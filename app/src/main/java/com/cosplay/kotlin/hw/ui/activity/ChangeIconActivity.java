package com.cosplay.kotlin.hw.ui.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.util.OrdinaryDialogFive;

public class ChangeIconActivity extends AppCompatActivity implements View.OnClickListener {
    PackageManager packageManager;
    TextView tv1, tv2;
    ComponentName test1Component;
    ComponentName test2Component;
    ComponentName defaultComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_icon);
        //拿到当前activity注册的组件名称
      //  ComponentName componentName = getComponentName();

        //拿到我们注册的MainActivity组件
         defaultComponent = new ComponentName(getBaseContext(), "com.cosplay.kotlin.hw.MainActivity");  //拿到默认的组件
        //拿到我注册的别名test组件
         test1Component = new ComponentName(getBaseContext(), "com.cosplay.kotlin.hw.icon_tag");
         test2Component = new ComponentName(getBaseContext(), "com.cosplay.kotlin.hw.icon_tag_1212");

        packageManager = getApplicationContext().getPackageManager();
        tv1 = findViewById(R.id.tv_change_1);
        tv2 = findViewById(R.id.tv_change_2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change_1:
                disableComponent(defaultComponent);
                disableComponent(test2Component);
                enableComponent(test1Component);
                break;
            case R.id.tv_change_2:
                disableComponent(defaultComponent);
                disableComponent(test1Component);
                enableComponent(test2Component);

                break;
        }
    }

    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
