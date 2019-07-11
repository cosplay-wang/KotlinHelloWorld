package com.cosplay.kotlin.hw.ui.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.cosplay.kotlin.hw.R;

public class FloatingService extends Service {
    private static final String TAG = "FloatViewService";
      //定义浮动窗口布局 
          	private LinearLayout mFloatLayout;
  	private WindowManager.LayoutParams wmParams;
 	//创建浮动窗口设置布局参数的对象 
         	private WindowManager mWindowManager;
  	private ImageButton mFloatView;
  	private int width;
  	private int height;
  	public static OnserviceClickListener mlistener;
  	private boolean ison;


 	 @Override
  	public int onStartCommand(Intent intent, int flags, int startId) {
    flags = START_STICKY;
    return super.onStartCommand(intent, flags, startId);
  	}


 	 @Override
 	 public void onCreate() {
   	 super.onCreate();
   	 createFloatView();


 	 }
 	 public void setOnServiceListener(OnserviceClickListener listener){
  	  mlistener=listener;
 	 }


 	 private void createFloatView() {


     wmParams = new WindowManager.LayoutParams();
   	//通过getApplication获取的是WindowManagerImpl.CompatModeWrapper 
   	mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
   	 width = mWindowManager.getDefaultDisplay().getWidth();
    height = mWindowManager.getDefaultDisplay().getHeight();
    	//设置window type 
    wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
    //设置图片格式，效果为背景透明 
    wmParams.format = PixelFormat.RGBA_8888;
    //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作） 
    wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    //调整悬浮窗显示的停靠位置为左侧置顶 
   	wmParams.gravity = Gravity.LEFT | Gravity.TOP;
    // 以屏幕左上角为原点，设置x、y初始值，相对于gravity 
   	wmParams.x = 0;
    wmParams.y = 0;


    //设置悬浮窗口长宽数据  
    wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
    wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;


    LayoutInflater inflater = LayoutInflater.from(getApplication());
    //获取浮动窗口视图所在布局 
    mFloatLayout = (LinearLayout) inflater.inflate(R.layout.floating_view, null);
    //添加mFloatLayout 
    mWindowManager.addView(mFloatLayout, wmParams);
    //浮动窗口按钮 
    mFloatView = (ImageButton) mFloatLayout.findViewById(R.id.iv_flaoting);


    mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                        .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    //设置监听浮动窗口的触摸移动 
    mFloatView.setOnTouchListener(new View.OnTouchListener() {
      int lastx = 0;
      int lasty = 0;
      int movex = 0;
      int movey = 0;
      boolean isMove;


      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            lastx = (int) event.getRawX();
            lasty = (int) event.getRawY();
            isMove = false;
            return false;
          case MotionEvent.ACTION_MOVE:


            int curx = (int) event.getRawX();
            int cury = (int) event.getRawY();


            int x;
            int y;
            x = Math.abs(curx - lastx);
            y = Math.abs(cury - lasty);
            if (x < 5 || y < 5) {
              isMove = false;
              return false;
            } else {
              isMove = true;
            }


            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
            wmParams.x = curx - mFloatView.getMeasuredWidth() / 2;
            // 减25为状态栏的高度
            wmParams.y = cury - mFloatView.getMeasuredHeight() / 2;
            // 刷新
            mWindowManager.updateViewLayout(mFloatLayout, wmParams);
            return true;
          case MotionEvent.ACTION_UP:
            int finalX = (int) event.getRawX();
            int finalY = (int) event.getRawY();
            boolean isok = false;


            if (finalY < mFloatView.getMeasuredHeight()) {
              movey = 0;
              movex = finalX - mFloatView.getMeasuredWidth() / 2;
            }


            if (finalY > height - mFloatView.getMeasuredHeight()) {
              movey = height - mFloatView.getMeasuredHeight();
              movex = finalX - mFloatView.getMeasuredWidth() / 2;
            }


            if (finalY > mFloatView.getMeasuredHeight() && finalY < height - mFloatView.getMeasuredHeight()) {
              isok = true;
            }
            if (isok && finalX - mFloatView.getMeasuredWidth() / 2 < width / 2) {
              movex = 0;
              movey = finalY - mFloatView.getMeasuredHeight() / 2;
            } else if (isok && finalX - mFloatView.getMeasuredWidth() / 2 > width / 2) {
              movex = width - mFloatView.getMeasuredWidth();
              movey = finalY - mFloatView.getMeasuredHeight() / 2;
            }


            wmParams.x = movex;
            wmParams.y = movey;
            if(isMove){
              mWindowManager.updateViewLayout(mFloatLayout, wmParams);
            }
            return isMove;//false 为点击 true 为移动
          default:
            break;
        }
        return false;
      }
    });


    mFloatView.setOnClickListener(new View.OnClickListener() {


      @Override
      public void onClick(View v) {
        if(ison){
        ;
          AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext()).setMessage("您正在使用DingCredit的VPN服务赚取叮咚币，关闭服务将影响您的叮咚币到账。").setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             // mFloatView.setBackgroundResource(R.drawable.icon_dingcredit_pop_no);
              ison=false;
            }


          }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             dialogInterface.dismiss();
            }
          });
          AlertDialog alert = builder.create();
          alert.setCancelable(false);
          alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
          alert.show();


        }else {


          AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext()).setMessage("服务成功开启！").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
          });
          AlertDialog alert = builder.create();
          alert.setCancelable(false);
          alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
          alert.show();
         // mFloatView.setBackgroundResource(R.drawable.icon_dingcredit_pop);
          ison=true;


        }
        mlistener.onclick(getApplicationContext());
      }
    });
  }


 	 @Override
  	public void onDestroy() {
  	  super.onDestroy();
   	 	if (mFloatLayout != null) {
    	  //移除悬浮窗口 
    	  mWindowManager.removeView(mFloatLayout);
   	 }
  	}


  	@Override
  	public IBinder onBind(Intent intent) {
  	  return null;
 	 }


 	public interface OnserviceClickListener{
 void onclick (Context context);
	 }


}
