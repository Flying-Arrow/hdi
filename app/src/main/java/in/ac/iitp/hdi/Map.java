package in.ac.iitp.hdi;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Map extends FragmentActivity implements LoaderCallbacks<Cursor>, OnMapClickListener, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    GoogleMap googleMap;
    double x, y;
    int flag = 0, flag3 = 0;
    String[] mProjection =
            {
                    LocationsDB.FIELD_LAT,
                    LocationsDB.FIELD_LNG,
                    LocationsDB.FIELD_HDI,
            };

    String userUUID;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync(this);
        Intent intent = getIntent();
        int flag = Integer.parseInt(intent.getExtras().getString("FLAG"));
        if (flag == 1) {
            flag3 = 1;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String, HdiDataModel> td = new HashMap<String, HdiDataModel>();
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    HdiDataModel hdiDataModel = jobSnapshot.getValue(HdiDataModel.class);
                    td.put(jobSnapshot.getKey(), hdiDataModel);
                }

                ArrayList<HdiDataModel> values = new ArrayList<>(td.values());
                List<String> keys = new ArrayList<String>(td.keySet());

                for (int i = 0; i < Math.min(values.size(), keys.size()); i++) {
                    String timestamp = keys.get(i);
                    // double EI, double HI, double II, double latitude, double longitude, String userUID) {
                    HdiDataModel mHdiDataModel = values.get(i);
                    //Double lat = mHdiDataModel.getLatitude()
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userUUID = user != null ? user.getUid() : null;

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMapp) {
        googleMap = googleMapp;
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        getSupportLoaderManager().initLoader(0, null, this);

        initialise();
        googleMap.setOnMapClickListener(this);
        googleMapp.setOnMapLongClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
                break;
            }
        }
    }

    /*
     hdi >= 0.8          : blue
     0.8 > hdi >= 0.7    : green
     0.7 > hdi >= 0.55   : orange
     0.55 > hdi          : red
    */
    public void initialise() {
        LatLng iit = new LatLng(25.535721, 84.851003);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iit, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    private float getColor(double hdi) {
        if (hdi >= 0.8) {
            return BitmapDescriptorFactory.HUE_AZURE;
        } else if (hdi >= 0.7) {
            return BitmapDescriptorFactory.HUE_YELLOW;
        } else if (hdi >= 0.55) {
            return BitmapDescriptorFactory.HUE_ORANGE;
        } else {
            return BitmapDescriptorFactory.HUE_RED;
        }
    }

    private void drawMarker(LatLng point, String info) {
        MarkerOptions markerOptions = new MarkerOptions();
        Double hdi = Double.parseDouble(info);
        markerOptions.position(point).title(info.toString()).draggable(false).icon(BitmapDescriptorFactory.defaultMarker(getColor(hdi)));
        googleMap.addMarker(markerOptions);
    }

    private void drawMarker(Double lat, Double lng, Double hdi) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng)).title(hdi.toString()).draggable(false).icon(BitmapDescriptorFactory.defaultMarker(getColor(hdi)));
        googleMap.addMarker(markerOptions);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri uri = LocationsContentProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0,
                               Cursor arg1) {
        int locationCount = 0;
        double lat = 0;
        double lng = 0;
        String info = "";

        locationCount = arg1.getCount();
        if (locationCount > 0) {
            arg1.moveToFirst();
        }

        for (int i = 0; i < locationCount; i++) {
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LNG));
            info = arg1.getString(arg1.getColumnIndex(LocationsDB.FIELD_HDI));
            LatLng location = new LatLng(lat, lng);
            drawMarker(location, info);
            arg1.moveToNext();
        }

        if (locationCount > 0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onMapClick(LatLng arg0) {
        if (flag3 == 1) {
            // TODO Auto-generated method stub
            x = arg0.latitude;
            y = arg0.longitude;
            final AlertDialog alert2 = new AlertDialog.Builder(Map.this).create();
            alert2.setTitle("Add pinpoint");
            alert2.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                    Intent intent = getIntent();
                    String healthHDI = intent.getExtras().getString("HealthHDI");
                    String incomeHDI = intent.getExtras().getString("IncomeHDI");
                    String eduHDI = intent.getExtras().getString("EducationHDI");
                    double HDI = Math.cbrt(Double.parseDouble(healthHDI) * Double.parseDouble(incomeHDI) * Double.parseDouble(eduHDI));
                    if (userUUID != null) {
                        HdiDataModel mHdiDataModel = new HdiDataModel(Double.parseDouble(eduHDI), Double.parseDouble(healthHDI), Double.parseDouble(incomeHDI), x, y, userUUID);
                        mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(mHdiDataModel);
                    }

                    MarkerOptions op = new MarkerOptions();
                    op.position(new LatLng(x, y)).title(String.valueOf(HDI)).draggable(false).icon(BitmapDescriptorFactory.defaultMarker(getColor(HDI)));
                    googleMap.addMarker(op);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LocationsDB.FIELD_LAT, x);
                    contentValues.put(LocationsDB.FIELD_LNG, y);
                    contentValues.put(LocationsDB.FIELD_HDI, HDI);
                    LocationInsertTask insertTask = new LocationInsertTask();
                    insertTask.execute(contentValues);
                    flag3 = 0;

                }
            });
            alert2.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            alert2.show();
        } else {
            AlertDialog alert = new AlertDialog.Builder(Map.this).create();
            alert.setTitle("Choose your location!");
            alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Toggle View", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    if (flag == 1) {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        flag = 0;
                    } else {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        flag = 1;
                    }
                }
            });
            alert.setButton(AlertDialog.BUTTON_POSITIVE, "Get Average", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    LatLngBounds bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
                    int k = 0;
                    Cursor mCursor = getContentResolver().query(LocationsContentProvider.CONTENT_URI, mProjection, null, null, null);
                    if (mCursor.getCount() > 0) {
                        Double hdi;
                        Double lat, lng;
                        while (mCursor.moveToNext()) {
                            hdi = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_HDI)));
                            lat = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_LAT)));
                            lng = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_LNG)));
                            if (bounds.contains(new LatLng(lat, lng))) {
                                k++;
                            }
                        }
                        System.out.println(k + "------------------------------------------");
                        Toast.makeText(Map.this, "Total markers on screen : " + k, Toast.LENGTH_LONG).show();
                    }
                }
            });
            alert.show();
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {


        final CharSequence[] items = {"All region", "HDI > 0.8", "0.8 > HDI > 0.7", "0.7 > HDI > 0.55", "0.55 > HDI"};
        final ArrayList seletedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("HDI Range");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                googleMap.clear();
                Cursor mCursor = getContentResolver().query(LocationsContentProvider.CONTENT_URI, mProjection, null, null, null);
                if (mCursor.getCount() > 0) {
                    Double hdi;
                    Double lat, lng;
                    while (mCursor.moveToNext()) {
                        hdi = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_HDI)));
                        lat = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_LAT)));
                        lng = Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(LocationsDB.FIELD_LNG)));
                        for (int i = 0; i < seletedItems.size(); i++) {
                            if (seletedItems.get(i).toString() == "0") {
                                drawMarker(lat, lng, hdi);
                            } else if ((seletedItems.get(i).toString() == "1") && (hdi >= 0.8)) {
                                drawMarker(lat, lng, hdi);
                            } else if ((seletedItems.get(i).toString() == "2") && (hdi >= 0.7) && (hdi < 0.8)) {
                                drawMarker(lat, lng, hdi);
                            } else if ((seletedItems.get(i).toString() == "3") && (hdi >= 0.55) && (hdi < 0.7)) {
                                drawMarker(lat, lng, hdi);
                            } else if ((seletedItems.get(i).toString() == "4") && (hdi < 0.55)) {
                                drawMarker(lat, lng, hdi);
                            }
                        }
                    }
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert3 = builder.create();
        alert3.show();
    }

    public class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }
}