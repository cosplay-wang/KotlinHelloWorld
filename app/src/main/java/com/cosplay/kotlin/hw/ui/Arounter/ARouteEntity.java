package com.cosplay.kotlin.hw.ui.Arounter;

import java.io.Serializable;

/**
 * Author:wangzhiwei on 2019/8/1.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ARouteEntity {
    int id;
    String name;
    long targetId;

    public ARouteEntity(int id, String name, long targetId) {
        this.id = id;
        this.name = name;
        this.targetId = targetId;
    }

    public ARouteEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
}
