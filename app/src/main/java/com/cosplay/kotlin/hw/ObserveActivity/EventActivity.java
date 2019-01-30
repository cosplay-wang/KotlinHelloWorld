package com.cosplay.kotlin.hw.ObserveActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        findViews();
    }

    private EditText etInput;
    private TextView tvSubscribe;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-12-21 15:05:23 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etInput = (EditText) findViewById(R.id.et_input);
        tvSubscribe = (TextView) findViewById(R.id.tv_subscribe);
        tvSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event.getEventInstance().publishMessage(etInput.getText().toString().equals("") ? "默认消息" : etInput.getText());
            }
        });
    }

}
