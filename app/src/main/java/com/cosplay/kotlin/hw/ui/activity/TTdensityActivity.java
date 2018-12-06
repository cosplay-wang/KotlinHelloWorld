package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.util.SPUtils;

import org.w3c.dom.Text;

public class TTdensityActivity extends AppCompatActivity {
    /**
     * 今日头条的适配方案
     * 修改density的值  来处理dp和sp的转化值
     * https://mp.weixin.qq.com/s?__biz=MzI1MzYzMjE0MQ==&mid=2247484502&idx=2&sn=a60ea223de4171dd2022bc2c71e09351&scene=21#wechat_redirect
     * @param savedInstanceState
     * 正常的使用逻辑是
     * 在oncreate中  在setContentview之前  把density的值 修正   按照设计图 为360dp  density应该是3
     * 现在手机屏幕是380dp  density=3*380/360  把density 放大等倍数
     * 这样在把dp2px的时候
     * 调用 TypedValue#applyDimension(int unit, float value, DisplayMetrics metrics) 来进行转换:
     *  px  =  dp * metris.density
     *  px = sp * metris.scaleDensity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttdensity);
        changeDensity();
        findViewById(R.id.tv_now).setLayoutParams(new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen.btg_quick_signin_height)), (int) getResources().getDimension(R.dimen.btg_fab_action_size)));
        ((TextView) findViewById(R.id.tv_ori)).setText("原始的density" + getDensity());
        ((TextView) findViewById(R.id.tv_change)).setText("改变density");
//        findViewById(R.id.tv_ori).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((TextView)findViewById(R.id.tv_ori)).setText("原始的density"+getDensity());
//            }
//        });
        findViewById(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.tv_change)).setText("改变density：" + changeDensity());
                findViewById(R.id.tv_now).setLayoutParams(new LinearLayout.LayoutParams((int) (getResources().getDimension(R.dimen.btg_quick_signin_height)), (int) getResources().getDimension(R.dimen.btg_fab_action_size)));
            }
        });

    }

    private float getDensity() {
        DisplayMetrics hhDisplayMetrics = getResources().getDisplayMetrics();
        SPUtils.setFloat(this,"density",hhDisplayMetrics.density);
        return hhDisplayMetrics.density;
    }

    private float changeDensity() {
        DisplayMetrics hhDisplayMetrics = getResources().getDisplayMetrics();
        SPUtils.setFloat(this,"density",hhDisplayMetrics.density);
        hhDisplayMetrics.density = hhDisplayMetrics.widthPixels/360;
        return hhDisplayMetrics.density;
    }
    private float backDensity() {
        DisplayMetrics hhDisplayMetrics = getResources().getDisplayMetrics();
        hhDisplayMetrics.density = SPUtils.getFloat(this,"density");
        return hhDisplayMetrics.density;
    }

    @Override
    protected void onStop() {
        super.onStop();
        backDensity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backDensity();
    }
}
