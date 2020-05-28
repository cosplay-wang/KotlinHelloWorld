package com.cosplay.audiocompose.audioBase;

import java.io.File;

/**
 * Author:wangzhiwei on 2019/12/31.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class FileNameUtils {
    public static String getWavNameByOther(String otherPath) {
        String wavPath = otherPath.substring(0, otherPath.lastIndexOf('.')) + Constant.SUFFIX_WAV;
        return wavPath;
    }
    public static String getPcmNameByOther(String wavPath) {
        String pcmPath = wavPath.substring(0, wavPath.lastIndexOf('.')) + Constant.SUFFIX_PCM;
        return wavPath;
    }

    public static String getResamplePcmNameByPcm(String pcmPath) {
        File pcmFile = new File(pcmPath);
        String path =  pcmFile.getParent();
        String oriFileName = new File(pcmPath).getName();
        oriFileName = oriFileName.substring(0, oriFileName.lastIndexOf('.')) + Constant.SUFFIX_PCM;
        String oriPcmFilePath = path + File.separator + "temp" + oriFileName;
        return oriPcmFilePath;
    }


}
