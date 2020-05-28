package com.cosplay.audiocompose;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Author:wangzhiwei on 2019/12/19.
 * Email :wangzhiwei@moyi365.com
 * Description: 分离MP4成 mp3和 无声mp4
 * 把无声的MP4和MP3合成 MP4
 */
public class VideoMp4Utils {
    /**
     * 分离mp4成 mp3和Mp4
     *
     * @param oriMp4Path
     * @param resultMP3Path
     * @param resultMP4Path
     */
    public static boolean separateMP4(String oriMp4Path, String resultMP3Path, String resultMP4Path, boolean isSuccess) {
        long startTime = System.currentTimeMillis();
        Log.e("separateMP4", "start：" + startTime);
        File resultMP4File = new File(resultMP4Path);
        File resultMP3File = new File(resultMP3Path);
        File oriMP4File = new File(oriMp4Path);
        MediaExtractor mediaExtractor = null;
        MediaMuxer sparateMediaMuxer = null;
        try {
            if (resultMP4File.exists()) {
                resultMP4File.delete();
            }
            resultMP4File.createNewFile();
            if (resultMP3File.exists()) {
                resultMP3File.delete();
            }
            resultMP3File.createNewFile();
            if (!oriMP4File.exists()) {
                isSuccess = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        }

        int mVideoTrackIndex = 0;
        int mAudioTrackIndex = 0;
        long frameRate;
        try {

            mediaExtractor = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor.setDataSource(oriMp4Path);//媒体文件的位置
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat format = mediaExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio")) {//获取音频轨道
                    ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
                    {
                        mediaExtractor.selectTrack(i);//选择此音频轨道
                        mediaExtractor.readSampleData(buffer, 0);
                        long first_sampletime = mediaExtractor.getSampleTime();
                        mediaExtractor.advance();
                        long second_sampletime = mediaExtractor.getSampleTime();
                        frameRate = Math.abs(second_sampletime - first_sampletime);//时间戳
                        mediaExtractor.unselectTrack(i);
                    }
                    mediaExtractor.selectTrack(i);
                    sparateMediaMuxer = new MediaMuxer(resultMP3Path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mAudioTrackIndex = sparateMediaMuxer.addTrack(format);
                    sparateMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;
                    int sampleSize = 0;
                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += frameRate;
                        sparateMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }
                    sparateMediaMuxer.stop();
                    sparateMediaMuxer.release();
                }

                if (mime.startsWith("video")) {
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                    sparateMediaMuxer = new MediaMuxer(resultMP4Path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mVideoTrackIndex = sparateMediaMuxer.addTrack(format);
                    sparateMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;
                    ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
                    int sampleSize = 0;
                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += 1000 * 1000 / frameRate;
                        sparateMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }
                    sparateMediaMuxer.stop();
                    sparateMediaMuxer.release();
                    isSuccess = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            mediaExtractor.release();
        }
        Log.e("separateMP4", "finsih：" + (System.currentTimeMillis() - startTime));
        return isSuccess;
    }

    /**
     * 合并mp3和MP4 成新的mp4
     *
     * @param oriMP3Path
     * @param oriMP4Path
     * @param resultMp4Path
     * @return
     */
    public static boolean mergeMP4MP3(String oriMP3Path, String oriMP4Path, String resultMp4Path) {
        long startTime = System.currentTimeMillis();
        Log.e("mergeMP4MP3", "start：" + startTime);
        File oriMP3File = new File(oriMP3Path);
        File oriMP4File = new File(oriMP4Path);
        File resultFile = new File(resultMp4Path);
        try {
            if (resultFile.exists()) {
                resultFile.delete();
            }
            resultFile.createNewFile();
            if (!oriMP3File.exists()) {
                return false;
            }
            if (!oriMP4File.exists()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaMuxer mergeMediaMuxer = null;
        MediaExtractor mediaExtractor = null;
        MediaExtractor mediaExtractor2 = null;
        try {
            mergeMediaMuxer = new MediaMuxer(resultMp4Path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int mVideoTrackIndex = 0;
        int mAudioTrackIndex = 0;
        long frameRate1 = 0;
        long frameRate2 = 0;

        MediaFormat format1;
        MediaFormat format2;
        try {
            mediaExtractor = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor.setDataSource(oriMP4Path);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                format1 = mediaExtractor.getTrackFormat(i);
                String mime = format1.getString(MediaFormat.KEY_MIME);

                if (mime.startsWith("video")) {
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    frameRate1 = format1.getInteger(MediaFormat.KEY_FRAME_RATE);
                    System.out.println("==============frameRate222==============" + 1000 * 1000 / frameRate1 + "");
                    mVideoTrackIndex = mergeMediaMuxer.addTrack(format1);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaExtractor2 = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor2.setDataSource(oriMP3Path);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor2.getTrackCount());
            for (int i = 0; i < mediaExtractor2.getTrackCount(); i++) {
                format2 = mediaExtractor2.getTrackFormat(i);
                String mime = format2.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio")) {//获取音频轨道
                    ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
                    {
                        mediaExtractor2.selectTrack(i);//选择此音频轨道
                        mediaExtractor2.readSampleData(buffer, 0);
                        long first_sampletime = mediaExtractor2.getSampleTime();
                        mediaExtractor2.advance();
                        long second_sampletime = mediaExtractor2.getSampleTime();
                        frameRate2 = Math.abs(second_sampletime - first_sampletime);//时间戳
                        mediaExtractor2.unselectTrack(i);
                    }
                    mediaExtractor2.selectTrack(i);
                    System.out.println("==============frameRate111==============" + frameRate2 + "");
                    mAudioTrackIndex = mergeMediaMuxer.addTrack(format2);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        mergeMediaMuxer.start();
        MediaCodec.BufferInfo info1 = new MediaCodec.BufferInfo();
        info1.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
        int sampleSize1 = 0;
        while ((sampleSize1 = mediaExtractor.readSampleData(buffer, 0)) > 0) {
            info1.offset = 0;
            info1.size = sampleSize1;
            info1.flags = mediaExtractor.getSampleFlags();
            info1.presentationTimeUs += 1000 * 1000 / frameRate1;
            mergeMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info1);
            mediaExtractor.advance();
        }


        MediaCodec.BufferInfo info2 = new MediaCodec.BufferInfo();
        info2.presentationTimeUs = 0;

        int sampleSize2 = 0;
        while ((sampleSize2 = mediaExtractor2.readSampleData(buffer, 0)) > 0) {
            info2.offset = 0;
            info2.size = sampleSize2;
            info2.flags = mediaExtractor2.getSampleFlags();
            info2.presentationTimeUs += frameRate2;
            mergeMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info2);
            mediaExtractor2.advance();
        }

        try {
            mediaExtractor.release();
            mediaExtractor2.release();
        } catch (Exception e) {
        }
        Log.e("mergeMP4MP3", "finsih：" + (System.currentTimeMillis() - startTime));
        return true;


    }



}
