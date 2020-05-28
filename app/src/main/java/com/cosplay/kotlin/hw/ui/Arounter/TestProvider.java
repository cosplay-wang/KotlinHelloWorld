package com.cosplay.kotlin.hw.ui.Arounter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Author:wangzhiwei on 2019/8/1.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
@Route(path = "pp/test")
public class TestProvider implements IProvider {
    @Override
    public void init(Context context) {

    }
    public String test(){
        return ("Hello Provider!");
    }
}
