package com.grpc.pb;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.grpc.pb.protos.ChatServiceGrpc;
import com.grpc.pb.protos.Request;
import com.grpc.pb.protos.Response;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

public class PBMainActivity extends AppCompatActivity {
    private static final String TAG = "PBMainActivity";

    private EditText etServerIp, etMessage, etUserId;
    private Button btnSend;
    private TextView tvResponse;

    private ManagedChannel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_pb);
        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etServerIp = findViewById(R.id.et_server_ip);
        etMessage = findViewById(R.id.et_message);
        etUserId = findViewById(R.id.et_user_id);
        btnSend = findViewById(R.id.btn_send);
        tvResponse = findViewById(R.id.tv_response);

        // 设置默认值
//        etServerIp.setText("192.168.101.139");
//        etUserId.setText("50051");

        etServerIp.setText("182.48.100.11");
        etUserId.setText("52000");
    }

    private void setupClickListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToServer();
            }
        });
    }

    private void sendMessageToServer() {
        String serverIp = etServerIp.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        String userIdStr = etUserId.getText().toString().trim();

        if (serverIp.isEmpty() || message.isEmpty() || userIdStr.isEmpty()) {
            tvResponse.setText("请填写所有字段");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            new GrpcTask().execute(serverIp, message, String.valueOf(userId));
        } catch (NumberFormatException e) {
            tvResponse.setText("用户ID必须是数字");
        }
    }

    private class GrpcTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String serverIp = params[0];
            String message = params[1];
            int userId = Integer.parseInt(params[2]);

            try {
                // 创建gRPC通道
                channel = ManagedChannelBuilder.forAddress(serverIp, 52000)
                        .usePlaintext() // 开发环境使用，生产环境应该使用TLS
                        .build();

                // 创建阻塞存根
                ChatServiceGrpc.ChatServiceBlockingStub stub = ChatServiceGrpc.newBlockingStub(channel);

                // 创建请求
                Request request = Request.newBuilder()
                        .setMessage(message)
                        .setUserId(userId)
                        .build();

                // 发送请求并获取响应
                Response response = stub.sendMessage(request);

                return "响应: " + response.getReply() +
                        "\n成功: " + response.getSuccess() +
                        "\n代码: " + response.getCode();

            } catch (Exception e) {
                Log.e(TAG, "gRPC调用失败", e);
                return "错误: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            tvResponse.setText(result);
            shutdownChannel();
        }
    }

    private void shutdownChannel() {
        if (channel != null) {
            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shutdownChannel();
    }
}