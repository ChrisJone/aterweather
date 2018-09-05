package com.runblog.aterweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.runblog.aterweather.R;
import com.runblog.aterweather.activity.base.BaseActivity;

import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
