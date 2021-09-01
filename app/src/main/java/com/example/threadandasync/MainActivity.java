package com.example.threadandasync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtNumber;
    private Button btnCount;
    private Handler handler;

    private static final int TOP_NUMBER = 100;
    private static final int NUMBER_DONE = 101;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        processHandler();
    }

    private void initView() {
        txtNumber = findViewById(R.id.txtNumber);
        btnCount = findViewById(R.id.btnCount);
        btnCount.setOnClickListener(this);
    }

    private void processHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case TOP_NUMBER:
                        isUpdate = true;
                        txtNumber.setText(String.valueOf(msg.arg1));
                        txtNumber.setTextSize(msg.arg1 + 10);
                        break;
                    case NUMBER_DONE:
                        txtNumber.setText("DONE!");
                        isUpdate = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCount:
                if(!isUpdate)
                    updateNum();
                break;
            default:
                break;
        }
    }

    private void updateNum() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TOP_NUMBER; i++) {
                    Message msg = new Message();
                    msg.what = TOP_NUMBER;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(NUMBER_DONE);
            }
        }).start();
    }
}