package com.cosplay.audiocompose.audioBase;

import com.cosplay.audiocompose.DecodeOperateInterface;
import com.cosplay.audiocompose.util.FileUtils;

import java.io.File;

/**
 * Author:wangzhiwei on 2019/12/31.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AudioDecode {
    public static void decodeAudio(String path, String destPath) {
        final File file = new File(path);

        if (FileUtils.checkFileExist(destPath)) {
            FileUtils.deleteFile(new File(destPath));
        }

        FileUtils.confirmFolderExist(new File(destPath).getParent());

        DecodeEngine.getInstance().convertMusicFileToWaveFile(path, destPath, new DecodeOperateInterface() {
            @Override
            public void updateDecodeProgress(int decodeProgress) {
                String msg = String.format("解码文件：%s，进度：%d", file.getName(), decodeProgress) + "%";
                //EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_MIX, msg));
            }

            @Override
            public void decodeSuccess() {

            }

            @Override
            public void decodeFail() {

            }
        });
    }

}
