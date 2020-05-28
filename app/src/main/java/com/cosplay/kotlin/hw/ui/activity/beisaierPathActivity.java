package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.view.WordLine;

import java.util.ArrayList;
import java.util.List;

public class beisaierPathActivity extends AppCompatActivity {
    WordLine scroll;
    List<String> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beisaier_path);
        scroll = findViewById(R.id.word);
        getString();
        scroll.setDatalist(dataList);

    }
    private void getString(){
        for(int i=0;i<50;i++){
            dataList.add("hhhh"+i);
        }
    }
}
