package com.cosplay.kotlin.hw.ui.Arounter;

import java.io.Serializable;

/**
 * Author:wangzhiwei on 2019/8/2.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ARouteEntitySer implements Serializable {
    String id;
    String name;

    public ARouteEntitySer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ARouteEntitySer(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
