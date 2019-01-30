package com.cosplay.kotlin.hw.mvvm2.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.cosplay.kotlin.hw.mvvm2.util.Contans;
import com.cosplay.kotlin.hw.mvvm2.base.BaseLoadListener;
import com.cosplay.kotlin.hw.mvvm2.entity.Book;
import com.cosplay.kotlin.hw.mvvm2.util.JsonUtil;
import com.cosplay.kotlin.hw.mvvm2.util.StringContans;
import com.cosplay.kotlin.hw.util.HttpUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Author:wangzhiwei on 2019/1/8.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class BookModelImpl implements BookModel {
    BaseLoadListener<Book> loadListener;
    Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StringContans.MainHandlerFailure:
                    loadListener.loadFailure(msg.obj.toString());
                    break;
                case StringContans.MainHandlerSuccess:
                    Book book = JsonUtil.GsonToBean(msg.obj.toString(), Book.class);
                    loadListener.loadSuccess(book);
                    break;
            }
        }
    };

    @Override
    public void loadBookData(int page, final BaseLoadListener<Book> loadListener) {
        this.loadListener = loadListener;
        try {
            HttpUtils.Companion.okHttpRequestCall(Contans.BOOK_LIST, null).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mainHandler.obtainMessage(StringContans.MainHandlerFailure, e.getMessage()).sendToTarget();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        String respnseJson = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(respnseJson);
                            if (jsonObject.get("data") != null && !jsonObject.get("data").equals("")) {
                                respnseJson = jsonObject.get("data").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("netData", "请求数据返回:" + respnseJson);
                        //  Book book = JsonUtil.GsonToBean(response.body().string(), Book.class);
                        mainHandler.obtainMessage(StringContans.MainHandlerSuccess, respnseJson).sendToTarget();
                        //  loadListener.loadSuccess(book);
                    } else {
                        mainHandler.obtainMessage(StringContans.MainHandlerFailure, response.message()).sendToTarget();
                        // loadListener.loadFailure(response.message());
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            mainHandler.obtainMessage(StringContans.MainHandlerFailure, e.getMessage()).sendToTarget();
            // loadListener.loadFailure(e.getLocalizedMessage());
        }
    }
}
