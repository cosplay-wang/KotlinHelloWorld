package com.cosplay.kotlin.hw.ui.Arounter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Author:wangzhiwei on 2019/8/1.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
@Interceptor(priority = 0, name = "测试拦截")
public class TestInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if (postcard.getUri() != null && !postcard.getUri().toString().isEmpty()) {
            if (postcard.getUri().toString().contains("activity3")) {
                callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程
            } else {
                callback.onContinue(postcard);  // 处理完成，交还控制权
            }
            // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程
            // 以上两种至少需要调用其中一种，否则不会继续路由
        }else{
            callback.onContinue(postcard);  // 处理完成，交还控制权
        }
    }

    @Override
    public void init(Context context) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}
