package com.example.android.fono;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DetailsActivity extends AppCompatActivity {
    TextView detDeviceName;
    TextView detBrand;
    TextView detSize;
    TextView detColor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        initUI();

        Mobile mobile=(Mobile) getIntent().getExtras().getSerializable("mobile");
        detDeviceName.setText(mobile.getDeviceName());
        detBrand.setText(mobile.getBrand());
        detSize.setText(mobile.getSize());
        detColor.setText(mobile.getColors());
    }

    void initUI(){
        detDeviceName=(TextView)findViewById(R.id.detial_deviceName_id);
        detBrand=(TextView)findViewById(R.id.detial_brand_id);
        detSize=(TextView)findViewById(R.id.detial_size_id);
         detColor=(TextView)findViewById(R.id.detial_colors_id);
    }
}
