package com.cosplay.kotlin.hw.datacache.obser;

/**
 * Author:wangzhiwei on 2020/5/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public interface Observer {

    /**
     * 更新接口
     * @param subject 传入主题对象，方面获取相应的主题对象的状态
     */
    public void update(Observable subject);
}
