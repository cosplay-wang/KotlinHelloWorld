package com.cosplay.audiocompose.util;

import android.os.Handler;
import android.os.Message;

import com.cosplay.audiocompose.bean.AudioFragment;

import java.util.List;

/**
 * Author:wangzhiwei on 2020/1/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class NoticeHandler extends Handler {
    private List<AudioFragment> records;
    private String oriWavFilePath;
    private String outPath;
    private IAudioCompose iAudioCompose;

    public void setParameter(List<AudioFragment> records,String oriWavFilePath,String outPath,IAudioCompose iAudioCompose) {
        this.records = records;
        this.oriWavFilePath = oriWavFilePath;
        this.outPath = outPath;
        this.iAudioCompose = iAudioCompose;
    }

    public IAudioCompose getiAudioCompose() {
        return iAudioCompose;
    }

    public List<AudioFragment> getRecords() {
        return records;
    }

    public String getOriWavFilePath() {
        return oriWavFilePath;
    }

    public String getOutPath() {
        return outPath;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
