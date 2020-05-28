package com.cosplay.kotlin.hw.datacache.bean;
/**
 * Author:wangzhiwei on 2020/4/26.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class NewAnswerBean extends BaseBean{
    int id;
    String duration;
    String answerJson;


    public NewAnswerBean(int id, String duration, String answerJson) {
        this.id = id;
        this.duration = duration;
        this.answerJson = answerJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
      //  cacheCountData();
    }

    public String getAnswerJson() {
        return answerJson;
    }

    public void setAnswerJson(String answerJson) {
        this.answerJson = answerJson;
       // cacheData();
    }

}
