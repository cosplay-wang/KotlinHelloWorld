package com.cosplay.audiocompose.util;

import android.os.Handler;
import android.util.Log;

import com.cosplay.audiocompose.AudioMp3Utils;
import com.cosplay.audiocompose.audioBase.AudioDecode;
import com.cosplay.audiocompose.audioBase.AudioEncodeUtil;
import com.cosplay.audiocompose.audioBase.Constant;
import com.cosplay.audiocompose.bean.Audio;
import com.cosplay.audiocompose.bean.AudioFragment;

import java.io.File;

import static com.cosplay.audiocompose.audioBase.FileNameUtils.getWavNameByOther;
import static com.cosplay.audiocompose.util.AudioUtil.changeSam;

/**
 * Author:wangzhiwei on 2020/1/6.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ParameterRunable implements Runnable {
    private IAudioComposePrgress iAudioCompose;
    private AudioFragment recordFragment;

    private String ori;
    private String oriWav;
    boolean isOri;
    Audio oriAudio;
    Audio recordAudio;
    public ParameterRunable(String ori, String oriWav,IAudioComposePrgress iAudioCompose) {
        this.ori = ori;
        this.oriWav = oriWav;
        this.iAudioCompose = iAudioCompose;
        try {
            oriAudio = Audio.createAudioFromFile(new File(ori));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isOri = true;
    }

    public ParameterRunable(AudioFragment recordFragment,IAudioComposePrgress iAudioCompose,String oriPath) {
        this.recordFragment = recordFragment;
        this.iAudioCompose = iAudioCompose;
        this.ori = oriPath;
        try {
            oriAudio = Audio.createAudioFromFile(new File(ori));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isOri = false;
    }

    @Override
    public void run() {
        if (!isOri) {
            String recordWavFilePath ="";
            if(!recordFragment.mFile.endsWith(".mp3")){
                recordWavFilePath = getWavNameByOther(recordFragment.mFile + Constant.SUFFIX_MP3);
            }else{
                recordWavFilePath = getWavNameByOther(recordFragment.mFile);
            }
            long startTimeRecord = System.currentTimeMillis();
            AudioDecode.decodeAudio(recordFragment.mFile, recordWavFilePath);
            Log.e("usetime", "decodeRecorduse:" + (System.currentTimeMillis() - startTimeRecord) / 1000d + "--");
            Log.e("pathSave", "2 wav record" + recordWavFilePath); //2 wav record
            try {
                recordAudio = Audio.createAudioFromFile(new File(recordFragment.mFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String resamplePcmPath = changeSam(recordWavFilePath,recordAudio.getSampleRate(),oriAudio.getSampleRate(),recordAudio.getChannel(),oriAudio.getChannel());
            Log.e("pathSave", "3 sam record pcm " + resamplePcmPath); //3 pcm record
            new File(recordWavFilePath).delete();
            Log.e("pathSave", "4 wav record delete " + recordWavFilePath); //2 wav record
            AudioEncodeUtil.convertPcm2Wav(resamplePcmPath, recordWavFilePath, oriAudio.getSampleRate(), oriAudio.getChannel(), oriAudio.getBitNum());
            recordFragment.mFile = recordWavFilePath;
            new File(resamplePcmPath).delete();
            Log.e("pathSave", "5 wav recordWavFilePath " + recordWavFilePath); //2 wav record
            AudioMp3Utils.finishNum++;
            iAudioCompose.composePorfress(AudioMp3Utils.finishNum);
        } else {
            AudioDecode.decodeAudio(ori, oriWav);
            AudioMp3Utils.finishNum++;
            iAudioCompose.composePorfress(AudioMp3Utils.finishNum);
        }
    }
}
