package com.runblog.aterweather;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        //初始化和风天气的账户信息
        HeConfig.init(ApiConfig.WEATHER_AUTH_NAME,ApiConfig.WEATHER_AUTH_KEY);
        HeConfig.switchToFreeServerNode();
        System.out.println("name->"+HeConfig.getUserId());

        HeWeather.getWeatherNow(this, "CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable e) {
                        Log.i("Log", "onError: ", e);
                    }

                    @Override
                    public void onSuccess(List dataObject) {
                        Log.i("Log", "onSuccess: " + new Gson().toJson(dataObject));
                    }
                });
    }


}
