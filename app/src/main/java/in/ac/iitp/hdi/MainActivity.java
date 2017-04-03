package in.ac.iitp.hdi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.ac.iitp.hdi.auth.AuthActivity;

/**
 * Created by anupam on 3/4/17.
 */

public class MainActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private SliderLayout mDemoSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
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
                    HdiDataModel mHdiDataModel = values.get(i);
                    String timestamp = keys.get(i);
                    Double lat = mHdiDataModel.getLatitude();
                    Double lng = mHdiDataModel.getLongitude();
                    String u_id = mHdiDataModel.getUserUID();
                    Double hea = mHdiDataModel.getHI();
                    Double edu = mHdiDataModel.getEI();
                    Double inc = mHdiDataModel.getII();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LocationsDB.FIELD_LAT, lat);
                    contentValues.put(LocationsDB.FIELD_LNG, lng);
                    contentValues.put(LocationsDB.FIELD_HDI, Math.cbrt(hea * edu * inc));
                    contentValues.put(LocationsDB.FIELD_INC, inc);
                    contentValues.put(LocationsDB.FIELD_HEA, hea);
                    contentValues.put(LocationsDB.FIELD_EDU, edu);
                    contentValues.put(LocationsDB.FIELD_TIM, timestamp);
                    contentValues.put(LocationsDB.FIELD_USER_ID, u_id);
                    LocationInsertTask insertTask = new LocationInsertTask();
                    insertTask.execute(contentValues);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("HDI1", R.drawable.img3);
        file_maps.put("HDI2", R.drawable.img11);
        file_maps.put("HDI3", R.drawable.img5);
        file_maps.put("HDI4", R.drawable.img9);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        final Activity mActivity = this;
        findViewById(R.id.startHDI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, HealHDI.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.startMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, Map.class);
                intent.putExtra("FLAG", "0");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_privacy:
                Intent homeIntent = new Intent(this, PrivacyActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.action_signout:
                signOut();
                break;
            default: //do nothing
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void signOut() {

        // Firebase sign out

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int SIGN_IN_MODE = sharedPref.getInt(getString(R.string.login_mode), -1);
        // Google sign out
        switch (SIGN_IN_MODE) {
            case -1:
                Intent intent = new Intent(this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;

            case 0:
                //nothing
                break;
            case 1:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {

                            }
                        });
                break;
            case 2:
                //nothing
                break;

            default:
                //
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.login_mode), -1);
        editor.apply();

        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, getString(R.string.connection_failed) + connectionResult);
        Toast.makeText(this, getString(R.string.google_play_error), Toast.LENGTH_SHORT).show();
    }

    public class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }
}
