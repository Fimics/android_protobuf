package com.noetix.demo;

import android.app.Application;

import com.noetix.libnoetix.IRobotSDKManager;

public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CANShell.executeCanCommands();
        IRobotSDKManager.getInstance().enableLog(true);
        IRobotSDKManager.getInstance().init("","",2,false);
        IRobotSDKManager.getInstance().setNeckRadiosDuration(new float[]{0.0f, 0.0f, 0.0f}, 5.0f);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
