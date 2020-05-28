package com.cosplay.audiocompose;

import android.os.Message;
import android.util.Log;

import com.cosplay.audiocompose.audioBase.AudioEditUtil;
import com.cosplay.audiocompose.audioBase.AudioEncodeUtil;
import com.cosplay.audiocompose.bean.Audio;
import com.cosplay.audiocompose.bean.AudioFragment;
import com.cosplay.audiocompose.util.FileUtils;
import com.cosplay.audiocompose.util.IAudioCompose;
import com.cosplay.audiocompose.util.IAudioComposePrgress;
import com.cosplay.audiocompose.util.NoticeHandler;
import com.cosplay.audiocompose.util.ParameterRunable;
import com.cosplay.audiocompose.util.TheadPoolUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import static com.cosplay.audiocompose.audioBase.FileNameUtils.getWavNameByOther;
import static com.cosplay.audiocompose.util.AudioUtil.getAudioFromPath;

/**
 * Author:wangzhiwei on 2020/1/3.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class AudioMp3Utils {
    public static void replaceAudio(Audio srcAudio, AudioFragment coverAudio, String outAudio) {
        float srcStartTime = coverAudio.mPosition / 1000f;
        float srcEndTime = (coverAudio.mPosition + coverAudio.mDuration) / 1000f;
        String srcWavePath = srcAudio.getPath();
        String coverWavePath = coverAudio.mFile;
        int sampleRate = srcAudio.getSampleRate();
        int channels = srcAudio.getChannel();
        int bitNum = srcAudio.getBitNum();
        RandomAccessFile srcFis = null;
        RandomAccessFile coverFis = null;
        RandomAccessFile newFos = null;
        String tempOutPcmPath = srcWavePath + ".tempPcm";
        try {

            //创建输入流
            srcFis = new RandomAccessFile(srcWavePath, "rw");
            coverFis = new RandomAccessFile(coverWavePath, "rw");
            newFos = new RandomAccessFile(tempOutPcmPath, "rw");
            final int srcStartPos = AudioEditUtil.getPositionFromWave(srcStartTime, sampleRate, channels, bitNum);
            final int srcEndPos = AudioEditUtil.getPositionFromWave(srcEndTime, sampleRate, channels, bitNum);
            final int coverStartPos = 0;
            final int coverEndPos = (int) coverFis.length() - AudioEditUtil.WAVE_HEAD_SIZE;

            //复制源音频srcStartTime时间之前的数据
            //跳过头文件数据
            srcFis.seek(AudioEditUtil.WAVE_HEAD_SIZE);

            AudioEditUtil.copyData(srcFis, newFos, srcStartPos);

            //复制覆盖音频指定时间段的数据
            //跳过指定位置数据
            coverFis.seek(AudioEditUtil.WAVE_HEAD_SIZE + coverStartPos);

            int copyCoverSize = coverEndPos - coverStartPos;
            float volume = srcAudio.getVolume();
            AudioEditUtil.copyData(coverFis, newFos, copyCoverSize, volume);
            int remainSize = (int) (srcFis.length() - srcEndPos);
            if (remainSize > 0) {
                srcFis.seek(AudioEditUtil.WAVE_HEAD_SIZE + srcEndPos);
                AudioEditUtil.copyData(srcFis, newFos, remainSize, volume);
            }

        } catch (Exception e) {
            e.printStackTrace();

            return;

        } finally {
            //关闭输入流
            if (srcFis != null) {
                try {
                    srcFis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (coverFis != null) {
                try {
                    coverFis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (newFos != null) {
                try {
                    newFos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 删除源文件,
        //new File(srcWavePath).delete();
        // 转换临时文件为源文件
        String wavPath = srcAudio.getPath();
        // Log.e(REPLACETAG, "tempOutPcmPath:" + wavPath);
        Log.e("pathSave", "6 pcm tempOutPcmPath " + tempOutPcmPath); //2 wav record
        AudioEncodeUtil.convertPcm2Wav(tempOutPcmPath, srcAudio.getPath(), sampleRate, channels, bitNum);
        new File(tempOutPcmPath).delete();
        new File(coverWavePath).delete();
        Log.e("pathSave", "7 wav tempOutPcmPath " + srcAudio.getPath()); //2 wav record
        //  Log.e(REPLACETAG, "convertPcm2Wav wavPath:" + wavPath + "---" + sampleRate + "--" + channels + "--" + bitNum);
    }

    public static int finishNum = 0;
    public static NoticeHandler handler = new NoticeHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Log.e("allusetime", "finsihnum:" + finishNum);//1 wav文件
//            if (finishNum == handler.getRecords().size() + 1) {
//                Audio audio = getAudioFromPath(handler.getOriWavFilePath());
//                Log.e("allusetime", "replacestart:" + System.currentTimeMillis());//1 wav文件
//                for (int j = 0; j < handler.getRecords().size(); j++) {
//                    AudioFragment recordFragment = handler.getRecords().get(j);
//                    AudioMp3Utils.replaceAudio(audio, recordFragment, handler.getOutPath());
//                }
//                handler.getiAudioCompose().composeFinsih();
//                finishNum = 0;
//            }
        }
    };

    static class ComposeProgress implements IAudioComposePrgress {
        @Override
        public void composePorfress(int num) {
            if (finishNum == handler.getRecords().size() + 1) {
                Audio audio = getAudioFromPath(handler.getOriWavFilePath());
                Log.e("allusetime", "replacestart:" + System.currentTimeMillis());//1 wav文件
                for (int j = 0; j < handler.getRecords().size(); j++) {
                    AudioFragment recordFragment = handler.getRecords().get(j);
                    AudioMp3Utils.replaceAudio(audio, recordFragment, handler.getOutPath());
                }
                FileUtils.copyFile(audio.getPath(), handler.getOutPath());
                new File(audio.getPath()).delete();
                handler.getiAudioCompose().composeFinsih();
                finishNum = 0;
            }
        }
    }


    public static void replaceOriginByRecord(String ori, final List<AudioFragment> records, String out, IAudioCompose iAudioCompose) throws Exception {
        ComposeProgress composeProgress = new ComposeProgress();
        String oriWavFilePath = getWavNameByOther(ori);
        if (finishNum != 0) {
            finishNum = 0;
            TheadPoolUtils.getThreadPoolInstance().shutdownNow();
        }
        handler.setParameter(records, oriWavFilePath, out, iAudioCompose);
        TheadPoolUtils.getThreadPoolInstance().execute(new ParameterRunable(ori, oriWavFilePath, composeProgress));
        for (int i = 0; i < records.size(); i++) {
            TheadPoolUtils.getThreadPoolInstance().execute(new ParameterRunable(records.get(i), composeProgress, ori));
        }
    }
}
