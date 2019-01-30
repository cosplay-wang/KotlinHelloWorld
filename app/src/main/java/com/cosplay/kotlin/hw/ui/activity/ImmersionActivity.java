package com.cosplay.kotlin.hw.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.gyf.barlibrary.ImmersionBar;

public class ImmersionActivity extends AppCompatActivity {
    TextView tv;
    ImmersionBar immersionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersion);
        getSupportActionBar().hide();
        tv = findViewById(R.id.tv_ii);
         immersionBar = ImmersionBar.with(this);
        immersionBar.fitsSystemWindows(true);
        setStatusBarColor(getResources().getColor(R.color.btg_global_light_white));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                immersionBar.fitsSystemWindows(false);
                immersionBar.init();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        immersionBar.reset();
        immersionBar.fitsSystemWindows(false);
        immersionBar.init();
    }

    /**
     * 设置状态栏颜色 如果是接近白色会自动将文字转为黑色
     * @param color
     */
    protected void setStatusBarColor(int color) {
        if (immersionBar != null) {
            if (color > -700000) {
                immersionBar.statusBarDarkFont(true, 0.3f);
            }
            immersionBar.statusBarColorInt(color).init();
        }
    }
}
