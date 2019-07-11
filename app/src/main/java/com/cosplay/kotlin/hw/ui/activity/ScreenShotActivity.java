package com.cosplay.kotlin.hw.ui.activity;

import android.Manifest;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.ui.view.CustomPopupWindow;
import com.cosplay.kotlin.hw.util.permisstion.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ScreenShotActivity extends AppCompatActivity {
    //截屏监听需要的判断依据
    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap"
    };

    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };

    /**
     * 内部存储器内容观察者
     */
    private ContentObserver mInternalObserver;

    /**
     * 外部存储器内容观察者
     */
    private ContentObserver mExternalObserver;
    private HandlerThread mHandlerThread;
    private Handler sHandler;
    private String TAG = "-------";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);//Manifest.permission.WRITE_EXTERNAL_STORAGE,
        mContext = this;
        mHandlerThread = new HandlerThread("Screenshot_Observer");
        mHandlerThread.start();
        sHandler = new Handler(mHandlerThread.getLooper());

        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, sHandler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, sHandler);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加监听
//        this.getContentResolver().registerContentObserver(
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//                false,
//                mInternalObserver
//        );
        this.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false,
                mExternalObserver
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  this.getContentResolver().unregisterContentObserver(mInternalObserver);
        this.getContentResolver().unregisterContentObserver(mExternalObserver);
    }

    /**
     * 媒体内容观察者(观察媒体数据库的改变)
     */
    private class MediaContentObserver extends ContentObserver {

        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            mContentUri = contentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, mContentUri.toString());
//            UserInfoManager userManager = new UserInfoManager(mContext);
//            UserInfoEntity infoEntity = userManager.readUserInfoFromCache();
//            if (infoEntity != null) {
//                avatar = infoEntity.getPortraitUrl();
//            }
            handleMediaContentChange(mContentUri);
        }
    }
    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {
            // 数据改变时查询数据库中最后加入的一条数据
            cursor = this.getContentResolver().query(
                    contentUri,
                    MEDIA_PROJECTIONS,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1"
            );

            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }

            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);

            // 获取行数据
            String data = cursor.getString(dataIndex);
            long dateTaken = cursor.getLong(dateTakenIndex);

            // 处理获取到的第一行数据
            handleMediaRowData(data, dateTaken);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
    /**
     * 处理监听到的资源
     */
    private void handleMediaRowData(final String data, long dateTaken) {
        if (checkScreenShot(data, dateTaken)) {
            Log.e(TAG, data + "--------> " + dateTaken);
            boolean b = false;
            final View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_feedback, null);
            ImageView iv_sheet = view.findViewById(R.id.iv_sheet);
            FileInputStream f = null;
            try {
                f = new FileInputStream(data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;//图片的长宽都是原来的1/8
            BufferedInputStream bis = new BufferedInputStream(f);

            bm = BitmapFactory.decodeStream(bis, null, options);
            iv_sheet.setImageBitmap(bm);
            final CustomPopupWindow popupWindow = new CustomPopupWindow.Builder(mContext)
                    .setContentView(view)
                    .setWidth(360)
                    .setHeight(640)
                    .setFouse(false)
                    .setOutSideCancel(false)
                    .setBackgroundDrawable(Color.TRANSPARENT)
                  //  .setAnimationStyle(R.style.animFeedback)
                    .builder()
                    .showAtLoaction(getWindow().getDecorView(), Gravity.RIGHT, 0, 0);


            popupWindow.getItemView(R.id.tv_bs_feedback).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  FeedbackAPI.openFeedbackActivity();
                }
            });

            popupWindow.getItemView(R.id.tv_bs_cs).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  startChat();
                }
            });

            final ImageView ivSheet = (ImageView) popupWindow.getItemView(R.id.iv_sheet);
            if (!TextUtils.isEmpty(data)) {
                try {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(data, opts);
                    opts.inSampleSize = 4;
                    opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    opts.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeFile(data, opts);
                    b = bitmap == null;
                    ivSheet.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (b) {
                    new Handler().postDelayed(new Runnable() {//截图写入手机数据库会有延迟
                        @Override
                        public void run() {
                            try {
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                opts.inJustDecodeBounds = true;
                                BitmapFactory.decodeFile(data, opts);
                                opts.inSampleSize = 4;
                                opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
                                opts.inJustDecodeBounds = false;
                                Bitmap bitmap = BitmapFactory.decodeFile(data, opts);
                                ivSheet.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 2000);
                }
            }
            view.setOnTouchListener(new View.OnTouchListener() {
                float y;
                float yLast;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            y = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            yLast = event.getRawY();
                            if (yLast < y) {
                                popupWindow.dimiss();
                            }
                            break;
                    }
                    return true;
                }
            });

            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (!isFinishing() && popupWindow.isShowing()) {//activity没有销毁时
                        popupWindow.dimiss();
                    }
                }
            }.start();
        }
    }

    /**
     * 判断是否是截屏
     */
    private boolean checkScreenShot(String data, long dateTaken) {
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork) && System.currentTimeMillis() - dateTaken < 8000) {
                return true;
            }
        }
        return false;
    }
    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(ScreenShotActivity.this,"获取权限成功",Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toast.makeText(ScreenShotActivity.this,R.string.failure,Toast.LENGTH_SHORT).show();
                        if (AndPermission.hasAlwaysDeniedPermission(ScreenShotActivity.this, permissions)) {
                            //showSettingDialog(ScreenShotActivity.this, permissions);
                        }
                    }
                })
                .start();
    }
}
