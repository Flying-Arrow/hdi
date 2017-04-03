package in.ac.iitp.hdi;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        setContentView(R.layout.activity_eduhdi);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("EI1", R.drawable.img9);
        file_maps.put("EI2", R.drawable.img10);
        file_maps.put("EI3", R.drawable.img11);
        file_maps.put("EI4", R.drawable.img12);

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
                if ((getSelectedAnswer(iRadGroupCurrentlyEduList) != -1) && getSelectedAnswer(iRadGroupCompletedMaster) != -1) {
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

                    double EI = (MYS / 15D + EYS / 18D) / 2D;

                    Toast.makeText(EduHDI.this, "Tap to select your location!", Toast.LENGTH_LONG).show();

                    Intent in = new Intent(EduHDI.this, Map.class);

                    Intent intent = getIntent();
                    String healthHDI = intent.getExtras().getString("HealthHDI");
                    String incomeHDI = intent.getExtras().getString("IncomeHDI");
                    in.putExtra("FLAG", "1");
                    in.putExtra("HealthHDI", healthHDI);
                    in.putExtra("IncomeHDI", incomeHDI);
                    in.putExtra("EducationHDI", String.valueOf(EI));
                    startActivity(in);


                } else {
                    Toast.makeText(EduHDI.this, "Please select all fields!", Toast.LENGTH_LONG).show();
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