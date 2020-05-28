package com.cosplay.kotlin.hw.ui.Arounter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cosplay.kotlin.hw.R;

@Route(path = "/test/ArounterActivity")
public class ArounterActivity extends AppCompatActivity {
    TextView textView1,textView2,textView3,textView4;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getLocalClassName();
        setContentView(R.layout.activity_arounter);
        textView1 = findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 普通跳转
              //  ARouter.getInstance().build("/test/ATargetOneActivity").navigation();
                ARouter.getInstance().build("/book/ATargetOneActivity").navigation();
            }
        });
        textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/test/ATargetTwoParamrterActivity")
                        .withLong("key1", 666L)
                        .withString("key3", "888")
                        .withObject("key4", new ARouteEntity("Jack"))
                        .withSerializable("key5",new ARouteEntitySer("serjack"))
                        .navigation();
            }
        });
        textView3 = findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri testUri = Uri.parse("arouter://m.aliyun.com/test/activity3");
                ARouter.getInstance().build(testUri)
                        .withLong("key1", 666L)
                        .withString("key3", "888")
                       // .greenChannel() //绿色通道，跳过所有拦截器
                        .withObject("key4", new ARouteEntity("Jack"))
                        .navigation(ArounterActivity.this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.d(TAG,postcard.toString());
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                super.onInterrupt(postcard);
                                Log.d(TAG, "被拦截了原因：：：："+postcard.getTag());
                            }
                        });
            }
        });
        textView4 = findViewById(R.id.textView4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/music/WelcomeActivity")
                        .withLong("key1", 666L)
                        .withString("key3", "888")
                     //   .withObject("key4", new MusicEntity("JackMusicID"))
                        .navigation(ArounterActivity.this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.d(TAG,postcard.toString());
                            }
                        });

         //   Log.d(TAG,ARouter.getInstance().navigation(TestProvider.class).test());//可能是需要在别的组件里 才能正常初始化
            }
        });
    }
}
