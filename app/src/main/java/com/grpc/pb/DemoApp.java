package com.grpc.pb;

import android.app.Application;
import android.util.Log;


public class DemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DemoApp","onCreate");
        CANShell.executeCanCommands();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
