package com.cosplay.kotlin.hw.mixAudio;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;


import com.cosplay.kotlin.hw.mixAudio.bean.Audio;
import com.cosplay.kotlin.hw.mixAudio.bean.AudioFragment;
import com.cosplay.kotlin.hw.mixAudio.ssrc.SSRC;
import com.cosplay.kotlin.hw.mixAudio.util.AudioEditUtil;
import com.cosplay.kotlin.hw.mixAudio.util.AudioEncodeUtil;
import com.cosplay.kotlin.hw.mixAudio.util.DecodeEngine;
import com.cosplay.kotlin.hw.mixAudio.util.ToastUtil;
import com.cosplay.kotlin.hw.mixAudio.util.encode.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import static com.cosplay.kotlin.hw.mixAudio.util.DecodeEngine.Resample;

/**
 *
 */
public class AudioTaskHandler {


    private static final String REPLACETAG = "REPLACEAUDIO";

    public void handleIntent(final Intent intent) {

        if (intent == null) {
            return;
        }

        String action = intent.getAction();

        switch (action) {
            case AudioTaskCreator.ACTION_AUDIO_CUT: {
                //裁剪
                String path = intent.getStringExtra(AudioTaskCreator.PATH_1);
                float startTime = intent.getFloatExtra(AudioTaskCreator.START_TIME, 0);
                float endTime = intent.getFloatExtra(AudioTaskCreator.END_TIME, 0);
                cutAudio(path, startTime, endTime);
            }

            break;
            case AudioTaskCreator.ACTION_AUDIO_REPLACE:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = intent.getStringExtra(AudioTaskCreator.PATH_1);
                        List<AudioFragment> listRecord = (ArrayList<AudioFragment>) intent.getSerializableExtra("list");
                        Log.e(REPLACETAG, "handler" + path + listRecord.size());
                        //  String simPath = FileUtils.getAudioEditStorageDirectory() + File.separator + "outSim" + Constant.SUFFIX_WAV;
                        try {
                            replaceOriginByRecord(path, listRecord, "");
                        } catch (Exception e) {
                            Log.e(REPLACETAG, "handler" + e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }

                }).start();
                break;
            case AudioTaskCreator.ACTION_AUDIO_INSERT: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //插入
                        String path = intent.getStringExtra(AudioTaskCreator.PATH_1);
                        String fileName1 = new File(path).getName();
                        String pcmName = "";
                        fileName1 = fileName1.substring(0, fileName1.lastIndexOf('.')) + Constant.SUFFIX_WAV;
                        pcmName = fileName1.substring(0, fileName1.lastIndexOf('.')) + "--" + Constant.SUFFIX_PCM;
                        String desPath = FileUtils.getAudioEditStorageDirectory() + File.separator + fileName1;
                        String pcmPath = FileUtils.getAudioEditStorageDirectory() + File.separator + pcmName;

                        decodeAudio(path, desPath);
                        List<AudioFragment> listRecord = (ArrayList<AudioFragment>) intent.getSerializableExtra("list");
                        String simPath = FileUtils.getAudioEditStorageDirectory() + File.separator + "outSim" + Constant.SUFFIX_WAV;
                        for (int i = 0; i < listRecord.size(); i++) {
                            AudioFragment audioFragment = listRecord.get(0);
                            DecodeEngine.getInstance().decodeMusicFile(audioFragment.mFile, pcmPath, 0, 20000000, null);

                            Resampling(audioFragment.mFile, simPath);
                            audioFragment.mFile = pcmPath;
                            insertAudio(desPath, listRecord.get(i));
                        }
                    }

                }).start();
            }

            break;
            case AudioTaskCreator.ACTION_AUDIO_MIX: {
                //合成
                String path1 = intent.getStringExtra(AudioTaskCreator.PATH_1);
                String path2 = intent.getStringExtra(AudioTaskCreator.PATH_2);
                float progressAudio1 = intent.getFloatExtra(AudioTaskCreator.PROGRESS_AUDIO_1, 0);
                float progressAudio2 = intent.getFloatExtra(AudioTaskCreator.PROGRESS_AUDIO_2, 0);

                mixAudio(path1, path2, progressAudio1, progressAudio2);
            }

            break;
            default:
                break;
        }

    }

    public void Resampling16to41(String before, String change) {
        File BeforeSampleChangedFile = new File(before);
        File SampleChangedFile = new File(change);
        try {
            FileInputStream fis = new FileInputStream(BeforeSampleChangedFile);
            FileOutputStream fos = new FileOutputStream(SampleChangedFile);
            // AudioFormat audioFormat;
            // audioFormat.getSampleRate()
            //同样低采样率转高采样率也是可以的，改下面参数就行。
            new SSRC(fis, fos, 16000, 44100, 2, 2, 2, Integer.MAX_VALUE, 0, 0, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Resampling(String before, String change) {
        File BeforeSampleChangedFile = new File(before);
        File SampleChangedFile = new File(change);
        try {
            FileInputStream fis = new FileInputStream(BeforeSampleChangedFile);
            FileOutputStream fos = new FileOutputStream(SampleChangedFile);
            //同样低采样率转高采样率也是可以的，改下面参数就行。
            new SSRC(fis, fos, 44100, 16000, 2, 2, 1, Integer.MAX_VALUE, 0, 0, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪音频
     *
     * @param srcPath   源音频路径
     * @param startTime 裁剪开始时间
     * @param endTime   裁剪结束时间
     */
    private void cutAudio(String srcPath, float startTime, float endTime) {
        String fileName = new File(srcPath).getName();
        String nameNoSuffix = fileName.substring(0, fileName.lastIndexOf('.'));
        fileName = nameNoSuffix + Constant.SUFFIX_WAV;
        String outName = nameNoSuffix + "_cut.wav";

        //裁剪后音频的路径
        String destPath = FileUtils.getAudioEditStorageDirectory() + File.separator + outName;

        //解码源音频，得到解码后的文件
        decodeAudio(srcPath, destPath);

        if (!FileUtils.checkFileExist(destPath)) {
            ToastUtil.showToast("解码失败" + destPath);
            return;
        }

        Audio audio = getAudioFromPath(destPath);

        if (audio != null) {
            AudioEditUtil.cutAudio(audio, startTime, endTime);
        }

        String msg = "裁剪完成";
        //EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_CUT, destPath, msg));
    }

    private void replaceOriginByRecord(String ori, List<AudioFragment> records, String out) throws Exception {
        String oriWavFilePath = getWavNameByOther(ori);
        decodeAudio(ori, oriWavFilePath);
        Audio audio = getAudioFromPath(oriWavFilePath);
        Log.e(REPLACETAG, "decodeAudio:" + oriWavFilePath);
        for (int i = 0; i < records.size(); i++) {
            AudioFragment recordFragment = records.get(i);
            String recordWavFilePath = getWavNameByOther(recordFragment.mFile);
            decodeAudio(recordFragment.mFile, recordWavFilePath);
            Log.e(REPLACETAG, "decodeAudio:" + recordWavFilePath);
            String resamplePcmPath = changeSam(recordWavFilePath);
            Log.e(REPLACETAG, "resamplePcmPath:" + resamplePcmPath);
            new File(recordWavFilePath).delete();

            AudioEncodeUtil.convertPcm2Wav(resamplePcmPath, recordWavFilePath, 44100, 2, 16);
            recordFragment.mFile = recordWavFilePath;
            Log.e(REPLACETAG, "convertPcm2Wav:" + recordWavFilePath);
            replaceAudio(audio, recordFragment);
            Log.e(REPLACETAG, "replaceAudio:finsih");
        }


    }

    private void replaceAudio(Audio srcAudio, AudioFragment coverAudio) {
        float srcStartTime = coverAudio.mPosition;
        float srcEndTime = coverAudio.mDuration;
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
        Log.e(REPLACETAG, "tempOutPcmPath:" + wavPath);
        AudioEncodeUtil.convertPcm2Wav(tempOutPcmPath, srcAudio.getPath(), sampleRate, channels, bitNum);
        Log.e(REPLACETAG, "convertPcm2Wav wavPath:" + wavPath + "---" + sampleRate + "--" + channels + "--" + bitNum);
    }


    /**
     * 重采样
     * return pcm path
     */
    private String changeSam(String wavPath) throws Exception {
        RandomAccessFile srcFis = new RandomAccessFile(wavPath, "rw");
        //跳过头文件数据
        int WAVE_HEAD_SIZE = 44;
        srcFis.seek(WAVE_HEAD_SIZE);
        long srcFileSize = new File(wavPath).length() - WAVE_HEAD_SIZE;
        RandomAccessFile newFos = null;
        String pcmPath = getPcmNameByOther(wavPath);
        newFos = new RandomAccessFile(pcmPath, "rw");
        AudioEditUtil.copyData(srcFis, newFos, (int) srcFileSize);
        String resamplePcmPath = getResamplePcmNameByPcm(pcmPath);
        Resampling16to41(pcmPath, resamplePcmPath);
        // AudioEncodeUtil.convertPcm2Wav(otar, otarwav, 44100, 2, 16);
        return resamplePcmPath;

    }


    private String getWavNameByOther(String otherPath) {
        String oriFileName = new File(otherPath).getName();
        oriFileName = oriFileName.substring(0, oriFileName.lastIndexOf('.')) + Constant.SUFFIX_WAV;
        String oriWavFilePath = FileUtils.getAudioEditStorageDirectory() + File.separator + oriFileName;
        return oriWavFilePath;
    }

    private String getPcmNameByOther(String wavPath) {
        String oriFileName = new File(wavPath).getName();
        oriFileName = oriFileName.substring(0, oriFileName.lastIndexOf('.')) + Constant.SUFFIX_PCM;
        String oriPcmFilePath = FileUtils.getAudioEditStorageDirectory() + File.separator + oriFileName;
        return oriPcmFilePath;
    }

    private String getResamplePcmNameByPcm(String pcmPath) {
        String oriFileName = new File(pcmPath).getName();
        oriFileName = oriFileName.substring(0, oriFileName.lastIndexOf('.')) + Constant.SUFFIX_PCM;
        String oriPcmFilePath = FileUtils.getAudioEditStorageDirectory() + File.separator + "temp" + oriFileName;
        return oriPcmFilePath;
    }

    private void insertAudio(String destPath1, AudioFragment audioFragment) {
        String fileName2 = new File(audioFragment.mFile).getName();
        fileName2 = fileName2.substring(0, fileName2.lastIndexOf('.')) + Constant.SUFFIX_WAV;
        String destPath2 = FileUtils.getAudioEditStorageDirectory() + File.separator + fileName2;
        //Resample(16000, audioFragment.mFile);
        decodeAudio(audioFragment.mFile, destPath2);
        if (!FileUtils.checkFileExist(destPath2)) {
            ToastUtil.showToast("解码失败" + destPath2);
            return;
        }

        Audio audioSrc = getAudioFromPath(destPath1);
        Audio audioRecord = getAudioFromPath(destPath2);
        audioFragment.mFile = audioRecord.getPath();
        AudioEditUtil.insertOutAudio.setPath(new File(new File(destPath1).getParentFile(), "insert_out_1111.wav").getAbsolutePath());
        if (audioSrc != null) {
            AudioEditUtil.insertAudioWithSame(audioSrc, audioFragment);
        }

        String msg = "插入完成";
        //EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_INSERT, outAudio.getPath(), msg));

    }

    private void mixAudio(String path1, String path2, float progress1, float progress2) {
        String fileName1 = new File(path1).getName();
        fileName1 = fileName1.substring(0, fileName1.lastIndexOf('.')) + Constant.SUFFIX_WAV;
        String fileName2 = new File(path2).getName();
        fileName2 = fileName2.substring(0, fileName2.lastIndexOf('.')) + Constant.SUFFIX_WAV;

        String destPath1 = FileUtils.getAudioEditStorageDirectory() + File.separator + fileName1;
        String destPath2 = FileUtils.getAudioEditStorageDirectory() + File.separator + fileName2;

        decodeAudio(path1, destPath1);
        decodeAudio(path2, destPath2);

        if (!FileUtils.checkFileExist(destPath1)) {
            ToastUtil.showToast("解码失败" + destPath1);
            return;
        }
        if (!FileUtils.checkFileExist(destPath2)) {
            ToastUtil.showToast("解码失败" + destPath2);
            return;
        }

        Audio audio1 = getAudioFromPath(destPath1);
        Audio audio2 = getAudioFromPath(destPath2);
        Audio outAudio = new Audio();
        outAudio.setPath(new File(new File(destPath1).getParentFile(), "out.wav").getAbsolutePath());

        if (audio1 != null && audio2 != null) {
            AudioEditUtil.mixAudioWithSame(audio1, audio2, outAudio, 0, progress1, progress2);
        }

        String msg = "合成完成";
        //EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_MIX, outAudio.getPath(), msg));

    }

    private void decodeAudio(String path, String destPath) {
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

    /**
     * 获取根据解码后的文件得到audio数据
     *
     * @param path
     * @return
     */
    private Audio getAudioFromPath(String path) {
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

    private void simpleDownSample(String beforePath, String afterPath) {
        File BeforeDownSampleFile = new File(beforePath);
        File DownSampled = new File(afterPath);
        try {
            FileInputStream fileInputStream = new FileInputStream(BeforeDownSampleFile);
            FileOutputStream fileOutputStream = new FileOutputStream(DownSampled);
            new SSRC(fileInputStream, fileOutputStream, 44100, 16000,
                    2,
                    2,
                    1, Integer.MAX_VALUE, 0, 0, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
