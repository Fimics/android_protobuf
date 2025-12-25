package com.grpc.pb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.grpc.nvidia.NvidiaActivity
import com.grpc.utils.KLog
import com.noetix.libnoetix.IRobotSDKManager
import com.noetix.libnoetix.RobotConfig
import com.noetix.libnoetix.SDKListener

class SplashActivity : AppCompatActivity() {
    private val mDataReceiver: DataReceiver? = null
    var status: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        initSDK()
        IRobotSDKManager.getInstance().chatMode(true)

        val btnChat = this.findViewById<AppCompatButton>(R.id.btn_chat)
        val btnTrack = this.findViewById<AppCompatButton>(R.id.btn_track)
        val btnGrpc = this.findViewById<AppCompatButton>(R.id.btn_grpc)
        val btnNvidia = this.findViewById<AppCompatButton>(R.id.btn_nvidia)

        btnChat.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
            }
        })

        btnTrack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val trackIntent = Intent(this@SplashActivity, LiveFaceActivity::class.java)
                startActivity(trackIntent)
            }
        })

        btnGrpc.setOnClickListener {
            val grpcIntent = Intent(this@SplashActivity, PBMainActivity::class.java)
            startActivity(grpcIntent)
        }

        btnNvidia.setOnClickListener {
            val nvidiaIntent = Intent(this@SplashActivity, NvidiaActivity::class.java)
            startActivity(nvidiaIntent)
        }
    }

    private fun initSDK(){
        val robotConfig = RobotConfig.Builder(this)
            .enableLog(true)
            .setSerialPort("")
            .setCan("")
            .setZeroDuration(5.0f)
            .setUAppKey("")
            .setUChannel("")
            .setNeckType(2)
            .setDelaySeconds(5)
            .setExt("")
            .build()
        IRobotSDKManager.getInstance().setSDKListener(object : SDKListener {
            override fun onSuccess() {
                KLog.d("init ","sdk 初始化成功...")
            }

            override fun onFail() {
                KLog.d("init ","sdk 初始化失败...")
            }
        })
        IRobotSDKManager.getInstance().init(robotConfig)
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}