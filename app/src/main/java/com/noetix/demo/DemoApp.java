package com.noetix.demo;

import android.app.Application;
import android.util.Log;

import com.noetix.libnoetix.IRobotSDKManager;


public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DemoApp","onCreate");
        IRobotSDKManager.getInstance().enableLog(true);
//        IRobotSDKManager.getInstance().init("/dev/ttyS4","/dev/ttyS4",2,false);
        IRobotSDKManager.getInstance().init("0","0",2,false);
        IRobotSDKManager.getInstance().saveAudioData(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
