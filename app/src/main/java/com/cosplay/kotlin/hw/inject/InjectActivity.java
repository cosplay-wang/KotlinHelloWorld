package com.cosplay.kotlin.hw.inject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

public class InjectActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_click)
    TextView tvClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inject);
        ViewUtilsTest.inject(this);

    }
    @ClickEvent(values = R.id.tv_click)
    public void click(View view,int id,String name){
        Log.e("click","dddddddd"+view.getId()+"--"+id+"--"+name);
        tvClick.setText("点击生效了，开心不(●'◡'●)");
    }
}
