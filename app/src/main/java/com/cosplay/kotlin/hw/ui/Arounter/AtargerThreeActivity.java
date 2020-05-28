package com.cosplay.kotlin.hw.ui.Arounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cosplay.kotlin.hw.R;
@Route(path = "/test/activity3")
public class AtargerThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atarger_three);
    }
}
