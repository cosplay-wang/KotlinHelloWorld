package com.cosplay.wzw.demo.musiclibrary;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Author:wangzhiwei on 2019/8/1.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
@Route(path = "/custom/json")
public class JsonParserService implements SerializationService {
    Gson gson;
    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return gson.fromJson(input,clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return gson.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return gson.fromJson(input,clazz);
    }

    @Override
    public void init(Context context) {
        gson = new Gson();
    }
}
