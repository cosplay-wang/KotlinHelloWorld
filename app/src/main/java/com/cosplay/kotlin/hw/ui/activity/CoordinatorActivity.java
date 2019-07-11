package com.cosplay.kotlin.hw.ui.activity;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

public class CoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        findViews();
        setSupportActionBar(toolBar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getLayoutInflater().inflate(R.layout.customer_toolbar_layout, toolBar);//把自定义布局文件添加到toolbar中
        collapsingToolBar.setTitle("详情界面");
        collapsingToolBar.setExpandedTitleColor(Color.BLACK);//设置还没收缩时状态下字体颜色
        collapsingToolBar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.e("asdadad",i+"---");
            }
        });
    }
    private AppBarLayout appbarLayout;
    private CollapsingToolbarLayout collapsingToolBar;
    private TextView tvJjj;
    private Toolbar toolBar;
    private ConstraintLayout rlTutorTop;
    private TextView tvTutorName;
    private ImageView tutorTopRank;
    private ImageView tutorTopAchevement;
    CoordinatorLayout coordinatorLayout;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-03-07 14:18:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        appbarLayout = (AppBarLayout)findViewById( R.id.appbar_layout );
        collapsingToolBar = (CollapsingToolbarLayout)findViewById( R.id.collapsing_tool_bar );
        tvJjj = (TextView)findViewById( R.id.tv_jjj );
        toolBar = (Toolbar)findViewById( R.id.tool_bar );
        rlTutorTop = (ConstraintLayout)findViewById( R.id.rl_tutor_top );
        tvTutorName = (TextView)findViewById( R.id.tv_tutor_name );
        tutorTopRank = (ImageView)findViewById( R.id.tutor_top_rank );
        tutorTopAchevement = (ImageView)findViewById( R.id.tutor_top_achevement );
        coordinatorLayout = findViewById(R.id.coordinator);
    }

}
