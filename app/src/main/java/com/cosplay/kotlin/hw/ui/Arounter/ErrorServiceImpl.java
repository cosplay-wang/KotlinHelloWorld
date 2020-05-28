package com.cosplay.kotlin.hw.ui.Arounter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Author:wangzhiwei on 2019/8/2.
 * Email :wangzhiwei@moyi365.com
 * Description:错误跳转  的 异常捕捉 处理
 */
@Route(path = "/book/*")
public class ErrorServiceImpl implements DegradeService {
    @Override
    public void onLost(Context context, Postcard postcard) {
        ARouter.getInstance().build("/test/ArounterActivity").navigation();
    }

    @Override
    public void init(Context context) {

    }
}
