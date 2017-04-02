package in.ac.iitp.hdi;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import in.ac.iitp.hdi.R;

/**
 * Created by satish on 4/1/2017.
 */

public class HealHDI extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,OnItemSelectedListener {

    private SliderLayout mDemoSlider;
    InfoDataBaseAdapter infoDataBaseAdapter;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    Spinner spinner;
    String gender,state;
    int birthYear=0;
    final int[] a = {0};
    double LEI=0;
    double LEB=0;
    final String state_1="";
    int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healhdi);
        spinner = (Spinner) findViewById(R.id.spinner);
        infoDataBaseAdapter=new InfoDataBaseAdapter(this);
        infoDataBaseAdapter=infoDataBaseAdapter.open();
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Male","2001","2005","63.4");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Female","2001","2005","67.9");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Male","2006","2010","65.4");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Female","2006","2010","69.4");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Male","2011","2015","66.9");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Female","2011","2015","70.9");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Male","2016","2020","68.4");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Female","2016","2020","72.1");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Male","2021","2025","69.4");
        infoDataBaseAdapter.insertEntry("Andhra Pradesh","Female","2021","2025","73.3");

        infoDataBaseAdapter.insertEntry("Assam","Male","2001","2005","59.6");
        infoDataBaseAdapter.insertEntry("Assam","Female","2001","2005","60.8");
        infoDataBaseAdapter.insertEntry("Assam","Male","2006","2010","61.6");
        infoDataBaseAdapter.insertEntry("Assam","Female","2006","2010","62.8");
        infoDataBaseAdapter.insertEntry("Assam","Male","2011","2015","63.6");
        infoDataBaseAdapter.insertEntry("Assam","Female","2011","2015","64.8");
        infoDataBaseAdapter.insertEntry("Assam","Male","2016","2020","75.6");
        infoDataBaseAdapter.insertEntry("Assam","Female","2016","2020","66.8");
        infoDataBaseAdapter.insertEntry("Assam","Male","2021","2025","67.1");
        infoDataBaseAdapter.insertEntry("Assam","Female","2021","2025","68.8");

        infoDataBaseAdapter.insertEntry("Bihar","Male","2001","2005","65.6");
        infoDataBaseAdapter.insertEntry("Bihar","Female","2001","2005","64.7");
        infoDataBaseAdapter.insertEntry("Bihar","Male","2006","2010","67.1");
        infoDataBaseAdapter.insertEntry("Bihar","Female","2006","2010","66.7");
        infoDataBaseAdapter.insertEntry("Bihar","Male","2011","2015","68.6");
        infoDataBaseAdapter.insertEntry("Bihar","Female","2011","2015","68.7");
        infoDataBaseAdapter.insertEntry("Bihar","Male","2016","2020","69.6");
        infoDataBaseAdapter.insertEntry("Bihar","Female","2016","2020","70.2");
        infoDataBaseAdapter.insertEntry("Bihar","Male","2021","2025","70.6");
        infoDataBaseAdapter.insertEntry("Bihar","Female","2021","2025","71.4");

        infoDataBaseAdapter.insertEntry("Gujarat","Male","2001","2005","64.9");
        infoDataBaseAdapter.insertEntry("Gujarat","Female","2001","2005","69.0");
        infoDataBaseAdapter.insertEntry("Gujarat","Male","2006","2010","67.2");
        infoDataBaseAdapter.insertEntry("Gujarat","Female","2006","2010","71.0");
        infoDataBaseAdapter.insertEntry("Gujarat","Male","2011","2015","69.2");
        infoDataBaseAdapter.insertEntry("Gujarat","Female","2011","2015","72.5");
        infoDataBaseAdapter.insertEntry("Gujarat","Male","2016","2020","70.7");
        infoDataBaseAdapter.insertEntry("Gujarat","Female","2016","2020","73.7");
        infoDataBaseAdapter.insertEntry("Gujarat","Male","2021","2025","71.9");
        infoDataBaseAdapter.insertEntry("Gujarat","Female","2021","2025","74.9");

        infoDataBaseAdapter.insertEntry("Haryana","Male","2001","2005","66.4");
        infoDataBaseAdapter.insertEntry("Haryana","Female","2001","2005","68.3");
        infoDataBaseAdapter.insertEntry("Haryana","Male","2006","2010","67.9");
        infoDataBaseAdapter.insertEntry("Haryana","Female","2006","2010","69.8");
        infoDataBaseAdapter.insertEntry("Haryana","Male","2011","2015","68.9");
        infoDataBaseAdapter.insertEntry("Haryana","Female","2011","2015","71.3");
        infoDataBaseAdapter.insertEntry("Haryana","Male","2016","2020","69.9");
        infoDataBaseAdapter.insertEntry("Haryana","Female","2016","2020","72.5");
        infoDataBaseAdapter.insertEntry("Haryana","Male","2021","2025","70.9");
        infoDataBaseAdapter.insertEntry("Haryana","Female","2021","2025","73.7");

        infoDataBaseAdapter.insertEntry("Karnataka","Male","2001","2005","64.5");
        infoDataBaseAdapter.insertEntry("Karnataka","Female","2001","2005","69.6");
        infoDataBaseAdapter.insertEntry("Karnataka","Male","2006","2010","66.5");
        infoDataBaseAdapter.insertEntry("Karnataka","Female","2006","2010","71.1");
        infoDataBaseAdapter.insertEntry("Karnataka","Male","2011","2015","68.0");
        infoDataBaseAdapter.insertEntry("Karnataka","Female","2011","2015","72.3");
        infoDataBaseAdapter.insertEntry("Karnataka","Male","2016","2020","69.0");
        infoDataBaseAdapter.insertEntry("Karnataka","Female","2016","2020","73.5");
        infoDataBaseAdapter.insertEntry("Karnataka","Male","2021","2025","70.0");
        infoDataBaseAdapter.insertEntry("Karnataka","Female","2021","2025","74.5");

        infoDataBaseAdapter.insertEntry("Kerala","Male","2001","2005","70.8");
        infoDataBaseAdapter.insertEntry("Kerala","Female","2001","2005","76.0");
        infoDataBaseAdapter.insertEntry("Kerala","Male","2006","2010","72.0");
        infoDataBaseAdapter.insertEntry("Kerala","Female","2006","2010","76.8");
        infoDataBaseAdapter.insertEntry("Kerala","Male","2011","2015","73.2");
        infoDataBaseAdapter.insertEntry("Kerala","Female","2011","2015","77.6");
        infoDataBaseAdapter.insertEntry("Kerala","Male","2016","2020","74.2");
        infoDataBaseAdapter.insertEntry("Kerala","Female","2016","2020","78.1");
        infoDataBaseAdapter.insertEntry("Kerala","Male","2021","2025","75.2");
        infoDataBaseAdapter.insertEntry("Kerala","Female","2021","2025","78.6");

        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Male","2001","2005","60.5");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Female","2001","2005","61.3");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Male","2006","2010","62.5");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Female","2006","2010","63.3");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Male","2011","2015","64.5");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Female","2011","2015","65.3");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Male","2016","2020","66.5");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Female","2016","2020","67.3");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Male","2021","2025","68.0");
        infoDataBaseAdapter.insertEntry("Madhya Pradesh","Female","2021","2025","69.3");

        infoDataBaseAdapter.insertEntry("Maharashtra","Male","2001","2005","66.4");
        infoDataBaseAdapter.insertEntry("Maharashtra","Female","2001","2005","69.8");
        infoDataBaseAdapter.insertEntry("Maharashtra","Male","2006","2010","67.9");
        infoDataBaseAdapter.insertEntry("Maharashtra","Female","2006","2010","71.3");
        infoDataBaseAdapter.insertEntry("Maharashtra","Male","2011","2015","68.9");
        infoDataBaseAdapter.insertEntry("Maharashtra","Female","2011","2015","72.5");
        infoDataBaseAdapter.insertEntry("Maharashtra","Male","2016","2020","69.9");
        infoDataBaseAdapter.insertEntry("Maharashtra","Female","2016","2020","73.7");
        infoDataBaseAdapter.insertEntry("Maharashtra","Male","2021","2025","70.9");
        infoDataBaseAdapter.insertEntry("Maharashtra","Female","2021","2025","74.7");

        infoDataBaseAdapter.insertEntry("Orissa","Male","2001","2005","60.3");
        infoDataBaseAdapter.insertEntry("Orissa","Female","2001","2005","62.3");
        infoDataBaseAdapter.insertEntry("Orissa","Male","2006","2010","62.3");
        infoDataBaseAdapter.insertEntry("Orissa","Female","2006","2010","64.8");
        infoDataBaseAdapter.insertEntry("Orissa","Male","2011","2015","64.3");
        infoDataBaseAdapter.insertEntry("Orissa","Female","2011","2015","67.3");
        infoDataBaseAdapter.insertEntry("Orissa","Male","2016","2020","66.3");
        infoDataBaseAdapter.insertEntry("Orissa","Female","2016","2020","69.6");
        infoDataBaseAdapter.insertEntry("Orissa","Male","2021","2025","67.8");
        infoDataBaseAdapter.insertEntry("Orissa","Female","2021","2025","71.6");

        infoDataBaseAdapter.insertEntry("Punjab","Male","2001","2005","67.7");
        infoDataBaseAdapter.insertEntry("Punjab","Female","2001","2005","70.4");
        infoDataBaseAdapter.insertEntry("Punjab","Male","2006","2010","68.7");
        infoDataBaseAdapter.insertEntry("Punjab","Female","2006","2010","71.6");
        infoDataBaseAdapter.insertEntry("Punjab","Male","2011","2015","69.7");
        infoDataBaseAdapter.insertEntry("Punjab","Female","2011","2015","72.8");
        infoDataBaseAdapter.insertEntry("Punjab","Male","2016","2020","70.7");
        infoDataBaseAdapter.insertEntry("Punjab","Female","2016","2020","73.8");
        infoDataBaseAdapter.insertEntry("Punjab","Male","2021","2025","71.5");
        infoDataBaseAdapter.insertEntry("Punjab","Female","2021","2025","74.8");

        infoDataBaseAdapter.insertEntry("Rajasthan","Male","2001","2005","64.1");
        infoDataBaseAdapter.insertEntry("Rajasthan","Female","2001","2005","67.2");
        infoDataBaseAdapter.insertEntry("Rajasthan","Male","2006","2010","66.1");
        infoDataBaseAdapter.insertEntry("Rajasthan","Female","2006","2010","69.2");
        infoDataBaseAdapter.insertEntry("Rajasthan","Male","2011","2015","67.6");
        infoDataBaseAdapter.insertEntry("Rajasthan","Female","2011","2015","70.7");
        infoDataBaseAdapter.insertEntry("Rajasthan","Male","2016","2020","68.6");
        infoDataBaseAdapter.insertEntry("Rajasthan","Female","2016","2020","71.9");
        infoDataBaseAdapter.insertEntry("Rajasthan","Male","2021","2025","69.6");
        infoDataBaseAdapter.insertEntry("Rajasthan","Female","2021","2025","73.1");

        infoDataBaseAdapter.insertEntry("Tamilnadu","Male","2001","2005","66.1");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Female","2001","2005","69.1");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Male","2006","2010","67.6");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Female","2006","2010","70.6");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Male","2011","2015","68.6");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Female","2011","2015","71.8");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Male","2016","2020","69.6");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Female","2016","2020","73.0");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Male","2021","2025","70.6");
        infoDataBaseAdapter.insertEntry("Tamilnadu","Female","2021","2025","74.0");

        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Male","2001","2005","62.0");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Female","2001","2005","61.9");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Male","2006","2010","64.0");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Female","2006","2010","64.4");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Male","2011","2015","66.0");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Female","2011","2015","66.9");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Male","2016","2020","67.5");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Female","2016","2020","69.2");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Male","2021","2025","68.7");
        infoDataBaseAdapter.insertEntry("Uttar Pradesh","Female","2021","2025","71.2");

        infoDataBaseAdapter.insertEntry("West Bengal","Male","2001","2005","66.7");
        infoDataBaseAdapter.insertEntry("West Bengal","Female","2001","2005","69.4");
        infoDataBaseAdapter.insertEntry("West Bengal","Male","2006","2010","68.2");
        infoDataBaseAdapter.insertEntry("West Bengal","Female","2006","2010","70.9");
        infoDataBaseAdapter.insertEntry("West Bengal","Male","2011","2015","69.2");
        infoDataBaseAdapter.insertEntry("West Bengal","Female","2011","2015","72.1");
        infoDataBaseAdapter.insertEntry("West Bengal","Male","2016","2020","70.2");
        infoDataBaseAdapter.insertEntry("West Bengal","Female","2016","2020","73.3");
        infoDataBaseAdapter.insertEntry("West Bengal","Male","2021","2025","71.0");
        infoDataBaseAdapter.insertEntry("West Bengal","Female","2021","2025","74.3");




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

        SeekBar seekBarAge = (SeekBar) findViewById(R.id.ageInp);
        final TextView ageValue = (TextView) findViewById(R.id.ageValue);


        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                progress=progress+1900;
                ageValue.setText(String.valueOf(progress));
                birthYear=progress;
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        radioSexGroup=(RadioGroup) findViewById(R.id.radioGroup1);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                if(selectedId!=-1){
                    radioSexButton=(RadioButton)findViewById(selectedId);
                    gender=radioSexButton.getText().toString();
                    System.out.println("STATE: "+state);
                    System.out.println("BirthYear: "+birthYear);
                    System.out.println("Gender: "+gender);
                    String temp;
                if(birthYear>2000) {
                    temp = infoDataBaseAdapter.getSingleEntry(state, gender, String.valueOf(birthYear));
                }
                else if(birthYear>=1995 && birthYear<=2000){
                    if(gender.equals("Male")){
                        temp="62.6";
                    }
                    else{
                        temp="61.8";
                    }
                }
                else if(birthYear>=1990 && birthYear<=1994){
                    if(gender.equals("Male")){
                        temp="59.0";
                    }
                    else{
                        temp="58.1";
                    }
                }
                else if(birthYear>=1985 && birthYear<=1989){
                    if(gender.equals("Male")){
                        temp="57.6";
                    }
                    else{
                        temp="56.7";
                    }
                }
                else if(birthYear>=1980 && birthYear<=1984){
                    if(gender.equals("Male")){
                        temp="55.8";
                    }
                    else{
                        temp="54.9";
                    }
                }
                else{
                    if(gender.equals("Male")){
                        temp="53.5";
                    }
                    else{
                        temp="52.5";
                    }
                }
                LEB=Double.valueOf(temp);
                LEI=(LEB-20)/65;
                System.out.println("Value of Life Expectancy Index :"+LEI);
                Intent intentIncHDI = new Intent(getApplicationContext(), IncHDI.class);
                intentIncHDI.putExtra("HEALTHHDI", Double.toString(LEI));
                startActivity(intentIncHDI);
                }
                else{
                    Toast.makeText(HealHDI.this, "Choose gender!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Andhra Pradesh");
        categories.add("Assam");
        categories.add("Bihar");
        categories.add("Gujarat");
        categories.add("Haryana");
        categories.add("Karnataka");
        categories.add("Kerala");
        categories.add("Madhya Pradesh");
        categories.add("Maharashtra");
        categories.add("Orissa");
        categories.add("Punjab");
        categories.add("Rajasthan");
        categories.add("Tamil Nadu");
        categories.add("Uttar Pradesh");
        categories.add("West Bengal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        state = spinner.getSelectedItem().toString();
        System.out.println("BirthYear:"+birthYear+"  State:"+state);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        state=item;
        if(flag>0){
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
        }
        flag++;
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
