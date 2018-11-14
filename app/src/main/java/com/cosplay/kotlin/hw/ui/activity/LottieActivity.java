package com.cosplay.kotlin.hw.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.view.PictureBookImage;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.io.InputStream;
import java.util.logging.Logger;

public class LottieActivity extends AppCompatActivity {
    PictureBookImage pictureBookImage;
    ImmersionBar immersionBar;
    boolean isFull;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        pictureBookImage = findViewById(R.id.iv_pic);
        applyImmersion(true);
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//// 设置json文件
//        animationView.setAnimation("helloworld.json");
//// 设置是否循环播放
//        animationView.loop(true);
//// 播放动画
//        animationView.playAnimation();
//// 暂停动画：貌似有点不同
//        animationView.cancelAnimation();
//// 停止动画：我感觉两个效果顺序是颠倒的，使用到时候请测试看看吧
//        animationView.pauseAnimation();
//// 跳转进度（0.0-1.1）
//        animationView.setProgress(0.5f);
//// 在监听中可以添加代码设置动画时长
//       // animator.setDuration(1000L);
         pictureBookImage.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.left),
                BitmapFactory.decodeResource(getResources(), R.drawable.left),
                BitmapFactory.decodeResource(getResources(), R.drawable.left));
        pictureBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFull){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    immersionBar.hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
                    isFull = false;
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    immersionBar.hideBar(BarHide.FLAG_SHOW_BAR).init();
                    isFull = true;
                }
             //   applyImmersion(isFull);
            }
        });
    }
    protected void applyImmersion(boolean isFull) {
        immersionBar = ImmersionBar.with(this);
        immersionBar.fitsSystemWindows(true);
//        if(isFull){
//            immersionBar.hideBar(BarHide.FLAG_HIDE_STATUS_BAR);
//        }

        setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 子类需要重载 关于软键盘弹出的模式
     *
     * @param softMode
     */
    protected void applyImmersion(int softMode) {
        immersionBar.reset();
        immersionBar = ImmersionBar.with(this);
        immersionBar.fitsSystemWindows(true);
        immersionBar.keyboardMode(softMode);
        setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 设置状态栏颜色 如果是接近白色会自动将文字转为黑色
     *
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
