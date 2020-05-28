package com.cosplay.kotlin.hw.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<String> dataList = new ArrayList<>();
    List<View> viewList = new ArrayList<>();
    View currentView;
    Button button;
    int checkPosition;
    TextView tv1;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = findViewById(R.id.vp_test);
        dealData();
        viewPager.setAdapter(new VPadapter());
        button= findViewById(R.id.bt_click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPosition = viewPager.getCurrentItem();
                currentView = viewList.get(checkPosition);
                tv1 = currentView.findViewById(R.id.tv_1);
                tv2 = currentView.findViewById(R.id.tv_2);
                tv1.setText(tv1.getText()+"---");
                tv2.setBackgroundResource(R.drawable.success_back);
            }
        });
    }

    private void dealData() {
        for (int i = 0; i < 20; i++) {
            dataList.add("position" + i);
            View view = LayoutInflater.from(this).inflate(R.layout.vp_item_layout,null);
         //   textView.setText("s,hsfsdf");
            viewList.add(view);
        }
    }


    class VPadapter extends PagerAdapter {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = viewList.get(position);
            tv1 = view.findViewById(R.id.tv_1);
            tv2 = view.findViewById(R.id.tv_2);
            tv1.setText(dataList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
           // super.destroyItem(container, position, object);
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }
}
