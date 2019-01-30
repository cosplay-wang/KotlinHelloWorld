package com.cosplay.kotlin.hw.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.ObserveActivity.SubscribeOneActivity;
import com.cosplay.kotlin.hw.R;

public class ObserverableMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observerable_mode);
        findViews();
    }
    private TextView tvSubscribe;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-21 14:51:03 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvSubscribe = (TextView)findViewById( R.id.tv_subscribe );
        tvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ObserverableMode.this, SubscribeOneActivity.class));
            }
        });
    }

}
