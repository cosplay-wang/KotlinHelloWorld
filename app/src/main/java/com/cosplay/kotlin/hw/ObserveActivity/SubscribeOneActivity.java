package com.cosplay.kotlin.hw.ObserveActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.activity.ObserverableMode;

public class SubscribeOneActivity extends BaseSubscribeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_one);
        findViews();
    }

    @Override
    public void getMessage(Object message) {
        super.getMessage(message);
        tvSubscribe.setText(message.toString());
    }

    private TextView tvSubscribe;


    private void findViews() {
        tvSubscribe = (TextView) findViewById(R.id.tv_subscribe);
        tvSubscribe.setText("跳转");
        tvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubscribeOneActivity.this, SubscribeTwoActivity.class));
            }
        });
    }
}
