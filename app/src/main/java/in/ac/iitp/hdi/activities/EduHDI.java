package in.ac.iitp.hdi.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
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

import in.ac.iitp.hdi.R;
import in.ac.iitp.hdi.data.HdiDataModel;

/**
 * Created by anupam(opticod) on 31/3/17.
 */
public class EduHDI extends ActionBarActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    boolean currentlyRecvEdu;
    boolean isCompletedMaster;
    int yearsPriSecSchool;
    int moreEdu;
    int guessChildEdu;
    List<RadioButton> iRadGroupCurrentlyEduList = new ArrayList<RadioButton>();
    List<RadioButton> iRadGroupCompletedMaster = new ArrayList<RadioButton>();
    private SliderLayout mDemoSlider;
    private int MYS;
    private int EYS;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.MYS = 0;
        this.EYS = 0;
        this.age = 0;
        this.currentlyRecvEdu = false;
        this.isCompletedMaster = true;
        this.yearsPriSecSchool = 0;
        this.moreEdu = 0;
        this.guessChildEdu = 0;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                System.out.println(dataSnapshot.getKey() + "Value" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String userUUID = user != null ? user.getUid() : null;

        setContentView(R.layout.activity_eduhdi);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Img1", R.mipmap.ic_launcher);
        file_maps.put("Img2", R.mipmap.ic_launcher);
        file_maps.put("Img3", R.mipmap.ic_launcher);
        file_maps.put("Img4", R.mipmap.ic_launcher);

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

        SeekBar seekBarAge = (SeekBar) findViewById(R.id.ageSlider);
        SeekBar seekBarPriSecSchool = (SeekBar) findViewById(R.id.secPriSchoolSlider);
        SeekBar seekBarMoreEdu = (SeekBar) findViewById(R.id.moreEduSlider);
        SeekBar seekBarGuessEdu = (SeekBar) findViewById(R.id.guessEduSlider);

        final TextView ageValue = (TextView) findViewById(R.id.ageValue);
        final TextView priSecValue = (TextView) findViewById(R.id.secPriSchoolValue);
        final TextView moreEduValue = (TextView) findViewById(R.id.moreEduValue);
        final TextView guessEduValue = (TextView) findViewById(R.id.guessEduValue);

        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                ageValue.setText(String.valueOf(progress));
                age = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarPriSecSchool.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                priSecValue.setText(String.valueOf(progress));
                yearsPriSecSchool = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarMoreEdu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                moreEduValue.setText(String.valueOf(progress));
                moreEdu = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBarGuessEdu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                guessEduValue.setText(String.valueOf(progress));
                guessChildEdu = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        RadioButton iRad0 = (RadioButton) findViewById(R.id.radioGroupCurrentlyEducTrue);
        RadioButton iRad1 = (RadioButton) findViewById(R.id.radioGroupCurrentlyEducFalse);

        iRadGroupCurrentlyEduList.add(iRad0);
        iRadGroupCurrentlyEduList.add(iRad1);

        RadioButton iRad2 = (RadioButton) findViewById(R.id.radioGroupBTechMtechTrue);
        RadioButton iRad3 = (RadioButton) findViewById(R.id.radioGroupBTechMtechFalse);

        iRadGroupCompletedMaster.add(iRad2);
        iRadGroupCompletedMaster.add(iRad3);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentlyRecvEdu = getSelectedAnswer(iRadGroupCurrentlyEduList) == 0;
                isCompletedMaster = getSelectedAnswer(iRadGroupCompletedMaster) == 0;
                if (currentlyRecvEdu) {
                    MYS = yearsPriSecSchool;
                }
                if (isCompletedMaster && age > 25) {
                    MYS = 15;
                }
                if (guessChildEdu != 0 && !currentlyRecvEdu) {
                    MYS = guessChildEdu;
                }
                MYS = Math.min(15, MYS);

                if (currentlyRecvEdu) {
                    EYS = yearsPriSecSchool;
                } else {
                    EYS = guessChildEdu;
                }
                if (isCompletedMaster && age > 25) {
                    EYS += 3;
                }
                if (moreEdu != 0 && age > 25) {
                    EYS += moreEdu;
                }
                EYS = Math.min(18, EYS);

                double EI = MYS / 15D + EYS / 18D;
                Toast.makeText(getApplicationContext(), "MYS: " + MYS + "; EYS: " + EYS + "; EI: " + EI, Toast.LENGTH_LONG).show();

                if (userUUID != null) {
                    HdiDataModel mHdiDataModel = new HdiDataModel(EI, 0, 0, 0, 0, userUUID);
                    mDatabase.child(String.valueOf(System.currentTimeMillis())).setValue(mHdiDataModel);
                }
            }
        });
    }

    public int getSelectedAnswer(List<RadioButton> iRadGroupList) {
        int selected = -1;
        for (int i = 0; i < iRadGroupList.size(); i++) {
            if (iRadGroupList.get(i).isChecked()) {
                return i;
            }
        }
        return selected;
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
}