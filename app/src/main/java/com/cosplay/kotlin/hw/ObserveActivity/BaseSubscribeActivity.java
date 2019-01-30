package com.cosplay.kotlin.hw.ObserveActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cosplay.kotlin.hw.R;

public class BaseSubscribeActivity extends AppCompatActivity implements ISubscribe{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_subscribe);
        registerSubscribe();
    }

    @Override
    public void getMessage(Object message) {

    }
    public void registerSubscribe(){
        Event.getEventInstance().registerEvent(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Event.getEventInstance().removeEvent(this);
    }
}
