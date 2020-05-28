package com.cosplay.audiocompose.util;

import android.util.Log;

import com.cosplay.audiocompose.audioBase.AudioEditUtil;
import com.cosplay.audiocompose.bean.Audio;
import com.cosplay.audiocompose.ssrc.SSRC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import static com.cosplay.audiocompose.audioBase.FileNameUtils.getPcmNameByOther;
import static com.cosplay.audiocompose.audioBase.FileNameUtils.getResamplePcmNameByPcm;

/**
 * Author:wangzhiwei on 2020/1/3.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AudioUtil {
    /**
     * 获取根据解码后的文件得到audio数据
     *
     * @param path
     * @return
     */
    public static Audio getAudioFromPath(String path) {
        if (!FileUtils.checkFileExist(path)) {
            return null;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Audio audio = Audio.createAudioFromFile(new File(path));
                return audio;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }



    /**
     * 重采样
     * return pcm path
     */
    public static String changeSam(String wavPath,int beforeRate ,int changeRate,int beforeChannel,int changeChannel)  {
        RandomAccessFile srcFis = null;
        try {
            srcFis = new RandomAccessFile(wavPath, "rw");

        //跳过头文件数据
        int WAVE_HEAD_SIZE = 44;
        srcFis.seek(WAVE_HEAD_SIZE);
        long srcFileSize = new File(wavPath).length() - WAVE_HEAD_SIZE;
        RandomAccessFile newFos = null;
        String pcmPath = getPcmNameByOther(wavPath);
        newFos = new RandomAccessFile(pcmPath, "rw");
        AudioEditUtil.copyData(srcFis, newFos, (int) srcFileSize);
        String resamplePcmPath = getResamplePcmNameByPcm(pcmPath);
        long startTime = System.currentTimeMillis();
        Resampling16to41(pcmPath, resamplePcmPath,beforeRate,changeRate,beforeChannel,changeChannel);
        Log.e("usetime", "changeSamuse:" + (System.currentTimeMillis() - startTime)/1000d + "--");
        // AudioEncodeUtil.convertPcm2Wav(otar, otarwav, 44100, 2, 16);
        return resamplePcmPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static void Resampling16to41(String before, String change,int beforeRate ,int changeRate,int beforeChannel,int changeChannel) {
        File BeforeSampleChangedFile = new File(before);
        File SampleChangedFile = new File(change);
        try {
            FileInputStream fis = new FileInputStream(BeforeSampleChangedFile);
            FileOutputStream fos = new FileOutputStream(SampleChangedFile);
            // AudioFormat audioFormat;
            // audioFormat.getSampleRate()
            //同样低采样率转高采样率也是可以的，改下面参数就行。
            new SSRC(fis, fos, beforeRate, changeRate, 2, 2, 2, Integer.MAX_VALUE, 0, 0, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
