package com.cosplay.kotlin.hw.datacache.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.cosplay.kotlin.hw.datacache.bean.AnswerBean;
import com.cosplay.kotlin.hw.datacache.bean.CacheDataList;

/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AnswerViewModel extends BaseViewModel{
    public MutableLiveData<CacheDataList<AnswerBean>> obserAnswerList = new MutableLiveData<>();
}
