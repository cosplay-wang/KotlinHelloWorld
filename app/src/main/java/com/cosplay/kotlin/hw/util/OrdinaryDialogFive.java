package com.cosplay.kotlin.hw.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

/**
 * Created by mengxiangda on 2018/5/15.
 */

public class OrdinaryDialogFive extends Dialog {

    private Activity mContext;
    private View mView;

    TextView mTvContent;
    TextView mTvCancle;
    TextView mTvSure;

    public OrdinaryDialogFive(@NonNull Activity context) {
        super(context, R.style.VipDialog);
        init(context);
    }

    public OrdinaryDialogFive(@NonNull Activity context, OnClickListener onClickListener) {
        super(context, R.style.VipDialog);
        this.onClickListener = onClickListener;
        init(context);
    }

    private void init(Activity context) {
        mContext = context;
        mView = View.inflate(mContext, R.layout.layout_act, null);

      //  initView();

        Window window = getWindow();
        window.setWindowAnimations(R.style.Dialogstyle_3);
        WindowManager.LayoutParams wl = getWindow().getAttributes();
//        wl.width = DeviceInfoUtil.ScreenW - 80;
//        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        wl.gravity = Gravity.CENTER;
//        /** 设置dialog弹出在屏幕的位置 */
//        window.setAttributes(wl);
//        /** 透明度  0 ~ 1.0 之间 */
//        window.setDimAmount(0.5f);



        /** 点击背景和 手机的物理back键都不消失 */
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(mView);
    }

//    private void initView() {
//        this.mTvContent = (TextView) mView.findViewById(R.id.tv_content);
//        this.mTvCancle = (TextView) mView.findViewById(R.id.tv_cancel);
//        this.mTvSure = (TextView) mView.findViewById(R.id.tv_confirm);
//
//        mTvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickListener != null) {
//                    onClickListener.cancle();
//                }
//            }
//        });
//
//        mTvSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickListener != null) {
//                    onClickListener.sure();
//                }
//            }
//        });
//
//
//        BreathAnimationUtils.clickScaleAnim(mTvCancle);
//        BreathAnimationUtils.clickScaleAnim(mTvSure);
//    }

    /**
     * 设置弹框内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (!TextUtils.isEmpty(content))
            mTvContent.setText(content);
    }

    /**
     * 设置取消按钮文本内容
     * 默认 取消
     *
     * @param text
     */
    public void setCancleText(String text) {
        if (!TextUtils.isEmpty(text))
            mTvCancle.setText(text);
    }

    /**
     * 设置确定按钮文本内容
     * 默认 确定
     *
     * @param text
     */
    public void setSureText(String text) {
        if (!TextUtils.isEmpty(text))
            mTvSure.setText(text);
    }

    /**
     * 设置只显示确定一个按钮
     */
    public void setJustOneVisible() {
        mTvCancle.setVisibility(View.GONE);
    }

    public OnClickListener onClickListener;

    public interface OnClickListener {
        void cancle();

        void sure();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener != null)
            this.onClickListener = onClickListener;
    }

}
