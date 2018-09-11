package com.runblog.aterweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.runblog.aterweather.activity.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutView() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //缓存是否首次打开启动页

        isFirst = (boolean) sharedPreferencesUtil.get(IS_FIRST_STARTUP,true);
        if(isFirst){//若首次启动，则打开启动页
            startActivity(new Intent(this,GuideActivity.class));
        }else{ //否则进入首页
           // handler.sendEmptyMessageDelayed(0, 3000);
            startActivity(new Intent(this,MainActivity.class));
        }
        finish();
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(act, MainActivity.class);
            startActivity(intent);
            //执行一次后销毁本页面
            finish();
        }

    };
}
