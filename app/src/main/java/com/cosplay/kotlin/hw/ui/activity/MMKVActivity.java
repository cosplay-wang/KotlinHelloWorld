package com.cosplay.kotlin.hw.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.util.ClassAttriNullUtils;
import com.cosplay.kotlin.hw.util.OrdinaryDialogFive;
import com.cosplay.kotlin.hw.util.SPUtils;
import com.tencent.mmkv.MMKV;

public class MMKVActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvSave;
    private TextView tvGet;
    MMKV mmkv,mmkvSp;


    private void findViews() {
        tvSave = (TextView)findViewById( R.id.tv_save );
        tvGet = (TextView)findViewById( R.id.tv_get );
    }
    private void initEvents(){
        tvSave.setOnClickListener(this);
        tvGet.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv);
        findViews();
        initEvents();
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
        mmkv = MMKV.defaultMMKV();
        mmkv = MMKV.mmkvWithID("sp1");
        mmkv = MMKV.mmkvWithID("sp2",MMKV.MULTI_PROCESS_MODE);//支持跨进程;
        mmkvSp = MMKV.mmkvWithID("fromsp");
        SharedPreferences sp = SPUtils.getSP(this);
        SPUtils.setString(this,FingerPrintActivity.token,"ttttttttttt");
        mmkvSp.importFromSharedPreferences(sp);
        Log.e("dddda",mmkvSp.decodeString(FingerPrintActivity.token));
        ClassAttriNullUtils.isAllFieldNull(new Student());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_save:
                mmkv.encode("string",true);
                break;
            case R.id.tv_get:
                tvGet.setText(mmkv.getBoolean("string",false)+"---");
                break;
        }
    }
    class Student {//学生类
        private String name;// 姓名
        private Integer age;// 年龄
        private String xq;// 兴趣

        public Student() {
        }

        public Student(String name, Integer age, String xq) {
            this.name = name;
            this.age = age;
            this.xq = xq;
        }

    }
}
