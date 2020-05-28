package com.cosplay.kotlin.hw.ui.Arounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cosplay.kotlin.hw.R;
@Route(path = "/test/ATargetOneActivity")
public class ATargetOneActivity extends AppCompatActivity {
   TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atarget_one);
        textView1 = findViewById(R.id.textView);
        textView1.setText("不带参数的activity");
    }
}
