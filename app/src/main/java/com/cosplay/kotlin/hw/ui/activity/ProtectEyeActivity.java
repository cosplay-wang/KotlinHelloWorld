package com.cosplay.kotlin.hw.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.service.FloatingService;

public class ProtectEyeActivity extends AppCompatActivity {
    WindowManager mWindowManager;
    Context mContext;
    WindowManager.LayoutParams mLayoutParams;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_protect_eye);
        view = new TextView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(200,200));
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 100);
        initWindow();
      //  startService(new Intent(this,FloatingService.class));
    }
    /**
     * 初始化Window,并设置相应的参数，如果保存了window参数，则加载该参数
     */
    private void initWindow() {
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        // 设置LayoutParams相关参数
        mLayoutParams = new WindowManager.LayoutParams();

        /**
         * 以下都是WindowManager.LayoutParams的相关属性 具体用途可参考SDK文档
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
           // mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }

       // mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 设置window type
        mLayoutParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP; // 设置Gravity为左上角
        // 设置x、y初始值,调整弹窗初始位置为屏幕中心
        mLayoutParams.x = 0;
      //  mLayoutParams.x = (Dip2PxUtil.getScreen(mContext)[0] - Dip2PxUtil.dip2px(mContext, 320)) / 2;
        mLayoutParams.y = 0;
      //  mLayoutParams.y = (Dip2PxUtil.getScreen(mContext)[1] - Dip2PxUtil.dip2px(mContext, 120)) / 2;

        // 设置悬浮窗口长宽数据
        mLayoutParams.width = 500;
        mLayoutParams.height = 500;
        mWindowManager.addView(view, mLayoutParams);
      //  mWindowManager.updateViewLayout(view, mLayoutParams);
    }
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // 获取相对屏幕的坐标，即以屏幕左上角为原点
//        x = event.getRawX();
//        y = event.getRawY() - 25; // 25是系统状态栏的高度
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mTouchStartX = event.getX();
//                mTouchStartY = event.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                updateViewPosition();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }
//
//    /**
//     * 根据拖拽更新视图在屏幕的显示位置
//     */
//    private void updateViewPosition() {
//        // 更新浮动窗口位置参数
//        mLayoutParams.x = (int) (x - mTouchStartX);
//        mLayoutParams.y = (int) (y - mTouchStartY);
//
//        // 这里判断是为了防止updateViewLayout 时，当前view已经dismiss了导致出错
//        if (mViewCount > 0) {
//            // 更新视图在屏幕上的位置
//            mWindowManager.updateViewLayout(mCallerDisplayView, mLayoutParams);
//        }
//    }
//}
}
