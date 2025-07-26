package com.noetix.demo;

import android.app.Application;

import com.noetix.libnoetix.IRobotSDKManager;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        IRobotSDKManager.getInstance().init("/dev/ttyS1", "/dev/ttyACM0");
        IRobotSDKManager.getInstance().init("", "");
        IRobotSDKManager.getInstance().enableLog(true);
        IRobotSDKManager.getInstance().saveAudioData(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
