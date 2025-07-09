package com.noetix.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.noetix.libnoetix.Callback;
import com.noetix.libnoetix.FramePair;
import com.noetix.libnoetix.IRobotSDKManager;

import java.util.LinkedList;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DataReceiver mDataReceiver;
    int status =0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        IRobotSDKManager.getInstance().chatMode(false);
        mDataReceiver = new DataReceiver();


        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IRobotSDKManager.getInstance().registerResultListener(mDataReceiver);
                PcmStreamReader.readPcmStream(MainActivity.this, "tts.pcm", new PcmStreamReader.PcmStreamCallback() {
                    @Override
                    public void onDataRead(byte[] buffer, int size, int totalSize) {
                        Log.d(TAG,"onDataRead buffer size "+size);
                        IRobotSDKManager.getInstance().processAudioStream(buffer,status);
                        status=1;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });
    }

    //模拟 status
    private int getStatus( int size, int totalSize){
      int status =0;

      //TODO tts 一般会返回 status

      return status;
    }

    @Override
    protected void onStop() {
        super.onStop();
        IRobotSDKManager.getInstance().unRegisterResultListener(mDataReceiver);
        IRobotSDKManager.getInstance().unInit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}