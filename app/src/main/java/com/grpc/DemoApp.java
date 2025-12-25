package com.grpc;

import android.app.Application;
import android.util.Log;

import com.grpc.utils.CANShell;


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
