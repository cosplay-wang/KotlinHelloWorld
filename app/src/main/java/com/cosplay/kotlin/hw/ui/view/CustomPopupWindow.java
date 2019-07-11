package com.cosplay.kotlin.hw.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;


/**
 * Created by zhangjing on 2018/7/11.
 * popupWindow 工具类
 */

public class CustomPopupWindow {
    private static Context mContext;
    private PopupWindow popupWindow;
   private View contentView;

    public CustomPopupWindow(Builder builder) {
        contentView = builder.contentView;
        popupWindow = new PopupWindow(builder.contentView, builder.width, builder.height);
        popupWindow.setFocusable(builder.fouse);
        popupWindow.setOutsideTouchable(builder.outSideCancel);
        popupWindow.setAnimationStyle(builder.animStyle);
        popupWindow.setBackgroundDrawable(new ColorDrawable(builder.color));

    }

    /**
     * 获取子view
     * @param id  id
     * @return  this
     */
    public View getItemView(int id) {
        if (popupWindow != null) {
            return contentView.findViewById(id);
        }
        return null;
    }

    /**
     * 设置显示在父控件的位置
     * @param parentViewId  父布局id
     * @param gravity  方向
     * @param x  x偏移
     * @param y  y偏移
     * @return  this
     */
    public CustomPopupWindow showAtLocation(int parentViewId, int gravity, int x, int y) {
        if (popupWindow != null) {
            View parentView = LayoutInflater.from(mContext).inflate(parentViewId,null);
            popupWindow.showAtLocation(parentView, gravity, x, y);
        }
        return this;
    }

    /**
     * 设置显示在父view的位置
     * @param parentView  父view
     * @param gravity  位置
     * @param x  x偏移
     * @param y  y偏移
     * @return  this
     */
    public CustomPopupWindow showAtLoaction(View parentView, int gravity, int x, int y){
        if (popupWindow != null) {
            popupWindow.showAtLocation(parentView, gravity, x, y);
        }
        return this;
    }

    /**
     * 设置显示在目标view的下方
     * @param targetView   目标view
     * @return  this
     */
    public CustomPopupWindow showAsDropDown(View targetView) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(targetView);
        }
        return this;
    }

    /**
     * 设置显示在目标view的 x y偏移方向
     * @param targetView  目标view
     * @param x  x偏移
     * @param y   y偏移
     * @return  this
     */
    public CustomPopupWindow showAsDropDown(View targetView, int x, int y) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(targetView, x, y);
        }
        return this;
    }

    public void  dimiss(){
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing(){
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }


    public static class Builder {
        private View contentView;
        private int width;
        private int height;
        private boolean fouse;
        private boolean outSideCancel;
        private int animStyle;
        private int color;

        public Builder(Context context) {
            mContext = context;
        }


        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFouse(boolean fouse) {
            this.fouse = fouse;
            return this;
        }

        public Builder setOutSideCancel(boolean outSideCancel) {
            this.outSideCancel = outSideCancel;
            return this;
        }

        public Builder setAnimationStyle(int animstyle) {
            this.animStyle = animstyle;
            return this;

        }

        public Builder setBackgroundDrawable(int color) {
            this.color = color;
            return this;
        }

        public CustomPopupWindow builder() {
            return new CustomPopupWindow(this);
        }
    }
}
