package com.cosplay.kotlin.hw.datacache;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.datacache.obser.Observable;
import com.cosplay.kotlin.hw.datacache.protocol.CacheObserver;
import com.cosplay.kotlin.hw.mixAudio.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class BaseNowAnswerActivity extends AppCompatActivity  implements CacheObserver, LifecycleOwner {
    AnswerController answerController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerController = new AnswerController();
        getLifecycle().addObserver(answerController);

    }
    private int cacheCount = 0;
    private final static int cacheSaveCount = 5;

    @Override
    public void notifyCacheData() {
        ToastUtil.showToast("更新==存储");
    }

    @Override
    public void notifyCacheCountData() {
        cacheCount++;
        if (cacheCount >= cacheSaveCount) {
            notifyCacheData();
            cacheCount = 0;
        } else {
            ToastUtil.showToast("更新==计数存储，计数累计：" + cacheCount);
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(answerController);
    }
}
