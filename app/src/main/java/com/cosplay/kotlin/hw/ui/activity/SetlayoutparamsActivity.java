package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

public class SetlayoutparamsActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    TextView textView;
    int height = 200;
    FrameLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlayoutparams);
        textView = findViewById(R.id.tv_click);
        frameLayout = findViewById(R.id.menu_tab_fl);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = height + 50;
                layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                layoutParams.gravity = Gravity.BOTTOM;
                frameLayout.setLayoutParams(layoutParams);
            }
        });
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
    }
}


