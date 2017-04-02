package in.ac.iitp.hdi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anupam on 3/4/17.
 */

public class MainActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
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
        file_maps.put("HDI2", R.drawable.img4);
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
            default: //do nothing
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
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

    public class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }
}
