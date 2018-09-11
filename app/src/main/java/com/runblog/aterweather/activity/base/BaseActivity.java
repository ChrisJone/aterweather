package com.runblog.aterweather.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.runblog.aterweather.util.SharedPreferencesUtil;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    public boolean isFirst;
    public static final String IS_FIRST_STARTUP="is_first_startup";
    public Activity act;
    public SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutView() != 0){
            setContentView(getLayoutView());
        }
        act = this;
        initView();
        if(null == sharedPreferencesUtil){
            sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);
        }
    }

    public abstract int getLayoutView();

    public abstract void initData();

    public abstract void initView();
}
