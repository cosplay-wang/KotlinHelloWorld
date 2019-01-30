package com.cosplay.kotlin.hw.Observerable;
import android.util.Log;

import com.cosplay.kotlin.hw.Observerable.Observer;
/**
 * Author:wangzhiwei on 2018/12/20.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class FirstObserver implements Observer {
    String name;

    public FirstObserver(String name) {
        this.name = name;
    }

    @Override
    public void getMessage(String message) {
        System.out.print(message + "---name="+name);
        //Log.e("updateObserver",message + "---name="+name);
    }
}
