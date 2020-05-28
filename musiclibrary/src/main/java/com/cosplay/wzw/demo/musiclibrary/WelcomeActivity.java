package com.cosplay.wzw.demo.musiclibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cosplay.wzw.demo.musiclibrary.entity.MusicEntity;

@Route(path = "/music/WelcomeActivity")
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity_welcome);
        // TODO: 2019/8/1 可能是需要在别的组件里 才能正常初始化
//        JsonParserService jsonParserService = ARouter.getInstance().navigation(JsonParserService.class);
//        jsonParserService.init(this);
//        MusicEntity aRouteEntity = jsonParserService.parseObject(getIntent().getStringExtra("key4"), MusicEntity.class);
    }
}
