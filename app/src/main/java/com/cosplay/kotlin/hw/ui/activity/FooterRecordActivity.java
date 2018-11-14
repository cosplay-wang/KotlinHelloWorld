package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.broadcast.FooterReceiver;

public class FooterRecordActivity extends AppCompatActivity {
    private FooterReceiver broadcastReceiver;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer_record);
        init();

    }

    //初始化广播
    private void init() {
        tv = (TextView) findViewById(R.id.tv_test);
        tv.setText("开始测试 : ");
        broadcastReceiver = new FooterReceiver(this);
        broadcastReceiver.startObserver(new FooterReceiver.ScreenStateListener() {
            // 解锁---------------
            @Override
            public void onUserPresent() {
                String str = tv.getText() + "+";
                tv.setText(str + "解锁");
            }

            // 锁屏---------------------
            @Override
            public void onScreenOn() {
                String str = tv.getText() + "+";
                tv.setText(str + "开屏");
            }

            // 开屏-------------------------
            @Override
            public void onScreenOff() {
                String str = tv.getText() + "+";
                tv.setText(str + "锁屏");
            }

            // home键-------------------------
            @Override
            public void onHomePressed() {
                String str = tv.getText() + "+";
                tv.setText(str + "home键");

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            broadcastReceiver.endObserver();
            Log.i("test", "注销");
        }
    }


}
