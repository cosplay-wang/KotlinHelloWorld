package com.cosplay.kotlin.hw.ui.activity;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cosplay.audiocompose.AudioMp3Utils;
import com.cosplay.audiocompose.VideoMp4Utils;
import com.cosplay.audiocompose.audioBase.AudioDecode;
import com.cosplay.audiocompose.audioBase.AudioEncodeUtil;
import com.cosplay.audiocompose.bean.Audio;
import com.cosplay.audiocompose.bean.AudioFragment;
import com.cosplay.audiocompose.ssrc.SSRC;
import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.mixAudio.AudioTaskCreator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.cosplay.audiocompose.audioBase.FileNameUtils.getWavNameByOther;
import static com.cosplay.audiocompose.util.AudioUtil.changeSam;
import static com.cosplay.audiocompose.util.AudioUtil.getAudioFromPath;

public class VideoAudioMixActivity extends AppCompatActivity {
    private static final String TAG = "PartM4";
    private TextView tvGet;
    private TextView tvMixmp3;
    private TextView tvMp4mix;
    private TextView tvMp3change;


    private MediaExtractor mediaExtractor;
    private MediaExtractor mediaExtractor2;
    List<AudioFragment> audioFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_audio_mix);
        findViews();
    }


    private void findViews() {
        tvGet = (TextView) findViewById(R.id.tv_get);
        tvMixmp3 = (TextView) findViewById(R.id.tv_mixmp3);
        tvMp4mix = (TextView) findViewById(R.id.tv_mp4mix);
        tvGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //   muxerdata();
                String oriMp4Path = "/storage/emulated/0/mp4/1.mp4";
                String resultMp3Path = "/storage/emulated/0/mp4/videoResult.mp3";
                String resultMp4Path = "/storage/emulated/0/mp4/videoResult.mp4";
                boolean isSuccess = false;
                VideoMp4Utils.separateMP4(oriMp4Path, resultMp3Path, resultMp4Path,isSuccess);

            }
        });
        tvMixmp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert.mp3", 6, 11));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                     //   String path = intent.getStringExtra(AudioTaskCreator.PATH_1);
                    //    List<AudioFragment> listRecord = (ArrayList<AudioFragment>) intent.getSerializableExtra("list");
                    //    Log.e(REPLACETAG, "handler" + path + listRecord.size());
                        //  String simPath = FileUtils.getAudioEditStorageDirectory() + File.separator + "outSim" + Constant.SUFFIX_WAV;
                        try {
                            replaceOriginByRecord( "/storage/emulated/0/demo2/demo2.mp3", audioFragmentList, "");
                        } catch (Exception e) {
                         //   Log.e(REPLACETAG, "handler" + e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }

                }).start();

                Log.e(TAG, "start insert");

                //    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert.mp3",20,25));
              //  AudioTaskCreator.createInsertAudioTask(VideoAudioMixActivity.this, "/storage/emulated/0/demo2/demo2.mp3", audioFragmentList);
                Log.e(TAG, "end insert");
            }
        });
        tvMp4mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //    muxermergedata();
                String resultMp4Path = "/storage/emulated/0/mp4/videoMerge.mp4";
                String oriMp3Path = "/storage/emulated/0/mp4/videoResult.mp3";
                String oriMp4Path = "/storage/emulated/0/mp4/videoResult.mp4";

                VideoMp4Utils.mergeMP4MP3(oriMp3Path, oriMp4Path, resultMp4Path);
            }
        });
        tvMp3change = findViewById(R.id.tv_mp3changeSam);
        tvMp3change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // changeSam();
                    List<AudioFragment> audioFragmentList = new ArrayList<>();
                    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/222.mp3", 6, 11));
                    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/222.mp3", 18, 23));
                    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/222.mp3", 30, 35));
                    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/222.mp3", 40, 45));
                    //    audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert.mp3",20,25));
               //     AudioTaskCreator.createReplaceAudioTask(VideoAudioMixActivity.this, "/storage/emulated/0/demo2/demo2.mp3", audioFragmentList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void replaceOriginByRecord(String ori, List<AudioFragment> records, String out) throws Exception {
        String oriWavFilePath = getWavNameByOther(ori);
        AudioDecode.decodeAudio(ori, oriWavFilePath);
        Audio audio = getAudioFromPath(oriWavFilePath);

        for (int i = 0; i < records.size(); i++) {
            AudioFragment recordFragment = records.get(i);
            String recordWavFilePath = getWavNameByOther(recordFragment.mFile);
            AudioDecode.decodeAudio(recordFragment.mFile, recordWavFilePath);
            // TODO: 2020/1/7  换一下
         //   String resamplePcmPath = changeSam(recordWavFilePath);
            String resamplePcmPath = recordWavFilePath;

            new File(recordWavFilePath).delete();

            AudioEncodeUtil.convertPcm2Wav(resamplePcmPath, recordWavFilePath, 44100, 2, 16);
            recordFragment.mFile = recordWavFilePath;
            AudioMp3Utils.replaceAudio(audio, recordFragment,out);
          //  replaceAudio(audio, recordFragment);

        }


    }


//    /**
//     * 重采样
//     */
//    private void changeSam() throws Exception {
//
//
//        //String orl = "/storage/emulated/0/demo2/demo2.mp3";
//        String orlwav = "/storage/emulated/0/demo2/demo2.wav";
//        String orlpcm = "/storage/emulated/0/demo2/demo2.pcm";
//        // String orl = "/storage/emulated/0/demo2/111.amr";
//        String orl = "/storage/emulated/0/demo2/222.mp3";
//        String otar = "/storage/emulated/0/demo2/demo16K.pcm";
//        String otarwav = "/storage/emulated/0/demo2/demo16K.wav";
//        // TODO: 2019/12/17  把文件例如mp3 转成pcm
//        int typeFile = detectFormatByTag(orl);
//        Log.e("decodeMusicFile", "start" + typeFile);
//        decodeAudio(orl, orlwav);
//        Log.e("decodeMusicFile", "wav");
//        // DecodeEngine.getInstance().decodeMusicFile(orlwav,orlpcm,0,20000000,null);
//        Log.e("decodeMusicFile", "success");
//
//
//        RandomAccessFile srcFis = new RandomAccessFile(orlwav, "rw");
//        //跳过头文件数据
//        int WAVE_HEAD_SIZE = 44;
//        srcFis.seek(WAVE_HEAD_SIZE);
//        long srcFileSize = new File(orlwav).length() - WAVE_HEAD_SIZE;
//        RandomAccessFile newFos = null;
//        newFos = new RandomAccessFile(orlpcm, "rw");
//        AudioEditUtil.copyData(srcFis, newFos, (int) srcFileSize);
//        Log.e("decodeMusicFile", "success Pcm");
//        // AudioEditUtil.writeWavHeader(srcFis);
//        MediaExtractor mediaExtractor = new MediaExtractor();
//        try {
//            mediaExtractor.setDataSource(orl);
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//        MediaFormat mediaFormat = mediaExtractor.getTrackFormat(0);
//        //采样率
//        int sampleRate = mediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ?
//                mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE) : 44100;
//        //通道数
//        int channelCount = mediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ?
//                mediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) : 1;
//        //音频长度
//        long duration = mediaFormat.containsKey(MediaFormat.KEY_DURATION) ? mediaFormat.getLong
//                (MediaFormat.KEY_DURATION)
//                : 0;
//        //mime
//        String mime = mediaFormat.containsKey(MediaFormat.KEY_MIME) ? mediaFormat.getString(MediaFormat
//                .KEY_MIME) : "";
//        String hh = "采样率:" + sampleRate + "通道数 :" + channelCount + "音频长度:" + (duration / 1000 / 1000) + "s  类型：" + mime;
//        tvGet.setText(hh);
//
//
//        Resampling(orlpcm, otar);
//        Log.e("decodeMusicFile", "Resampling");
//        AudioEncodeUtil.convertPcm2Wav(otar, otarwav, 44100, 2, 16);
//        MediaExtractor outmediaExtractor = new MediaExtractor();
//        try {
//            outmediaExtractor.setDataSource(otarwav);
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//        MediaFormat OutmediaFormat = outmediaExtractor.getTrackFormat(0);
//        //采样率
//        int outsampleRate = OutmediaFormat.containsKey(MediaFormat.KEY_SAMPLE_RATE) ?
//                OutmediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE) : 44100;
//        //通道数
//        int outchannelCount = OutmediaFormat.containsKey(MediaFormat.KEY_CHANNEL_COUNT) ?
//                OutmediaFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT) : 1;
//        //音频长度
//        long outduration = OutmediaFormat.containsKey(MediaFormat.KEY_DURATION) ? OutmediaFormat.getLong
//                (MediaFormat.KEY_DURATION)
//                : 0;
//        //mime
//        String outmime = OutmediaFormat.containsKey(MediaFormat.KEY_MIME) ? OutmediaFormat.getString(MediaFormat
//                .KEY_MIME) : "";
//        String hhout = "采样率:" + outsampleRate + "通道数 :" + outchannelCount + "音频长度:" + (outduration / 1000 / 1000) + "s  类型：" + outmime;
//        tvGet.setText(hhout);
//        Log.e("decodeMusicFile", "otarwav");
//    }


//    private void decodeAudio(String path, String destPath) {
//        final File file = new File(path);
//
//        if (FileUtils.checkFileExist(destPath)) {
//            FileUtils.deleteFile(new File(destPath));
//        }
//
//        FileUtils.confirmFolderExist(new File(destPath).getParent());
//
//        DecodeEngine.getInstance().convertMusicFileToWaveFile(path, destPath, new DecodeOperateInterface() {
//            @Override
//            public void updateDecodeProgress(int decodeProgress) {
//                String msg = String.format("解码文件：%s，进度：%d", file.getName(), decodeProgress) + "%";
//                EventBus.getDefault().post(new AudioMsg(AudioTaskCreator.ACTION_AUDIO_MIX, msg));
//            }
//
//            @Override
//            public void decodeSuccess() {
//
//            }
//
//            @Override
//            public void decodeFail() {
//
//            }
//        });
//    }

    public void Resampling(String before, String change) {
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

    /**
     * 分离MP4 成 消音Mp4和mp3
     */
    private void muxerdata() {
        Log.e(TAG, "start spra");
        String srcPath = Environment.getExternalStorageDirectory()
                .getPath() + "/video.mp4";

        String dirP = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2";
        String fPath1 = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2/demo1.mp4";
        String fPath2 = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2/demo2.mp3";
        File file = new File(dirP);
        if (!file.exists()) {
            file.mkdir();
        }

        File file1 = new File(fPath1);
        File file2 = new File(fPath2);
        try {
            if (file1.exists()) {
                file1.delete();

            }
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaMuxer mMediaMuxer;
        int mVideoTrackIndex = 0;
        int mAudioTrackIndex = 0;
        long frameRate;

        try {
            mediaExtractor = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor.setDataSource(srcPath);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat format = mediaExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                System.out.println("==========mime===================" + mime);
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
                    System.out.println("==============frameRate111==============" + frameRate + "");
                    mMediaMuxer = new MediaMuxer(fPath2, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mAudioTrackIndex = mMediaMuxer.addTrack(format);
                    mMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;

                    int sampleSize = 0;
                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += frameRate;
                        mMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }

                    mMediaMuxer.stop();
                    mMediaMuxer.release();

                }

                if (mime.startsWith("video")) {
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);
                    System.out.println("==============frameRate222==============" + 1000 * 1000 / frameRate + "");
                    mMediaMuxer = new MediaMuxer(fPath1, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                    mVideoTrackIndex = mMediaMuxer.addTrack(format);
                    mMediaMuxer.start();

                    MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                    info.presentationTimeUs = 0;
                    ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
                    int sampleSize = 0;
                    while ((sampleSize = mediaExtractor.readSampleData(buffer, 0)) > 0) {
                        info.offset = 0;
                        info.size = sampleSize;
                        info.flags = mediaExtractor.getSampleFlags();
                        info.presentationTimeUs += 1000 * 1000 / frameRate;
                        mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
                        mediaExtractor.advance();
                    }

                    mMediaMuxer.stop();
                    mMediaMuxer.release();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mediaExtractor.release();
            mediaExtractor = null;
        }
        Log.e(TAG, "finish spra");
    }

    /**
     * 合成Mp4 和  mP3 成 完整的MP4
     */
    private void muxermergedata() {
        Log.e(TAG, "start mix");
        String desPath = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2/videoMerge.mp4";

        String fPath1 = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2/videoResult.mp4";
        String fPath2 = Environment.getExternalStorageDirectory()
                .getPath() + "/demo2/videoResult.mp3";

        File filedes = new File(desPath);

        try {
            if (filedes.exists()) {
                filedes.delete();
            }
            filedes.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MediaMuxer mMediaMuxer = null;

        try {
            mMediaMuxer = new MediaMuxer(desPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
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
            mediaExtractor.setDataSource(fPath1);//媒体文件的位置
            System.out.println("==========getTrackCount()===================" + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                format1 = mediaExtractor.getTrackFormat(i);
                String mime = format1.getString(MediaFormat.KEY_MIME);

                if (mime.startsWith("video")) {
                    mediaExtractor.selectTrack(i);//选择此视频轨道
                    frameRate1 = format1.getInteger(MediaFormat.KEY_FRAME_RATE);
                    System.out.println("==============frameRate222==============" + 1000 * 1000 / frameRate1 + "");
                    mVideoTrackIndex = mMediaMuxer.addTrack(format1);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaExtractor2 = new MediaExtractor();//此类可分离视频文件的音轨和视频轨道
            mediaExtractor2.setDataSource(fPath2);//媒体文件的位置
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
                    mAudioTrackIndex = mMediaMuxer.addTrack(format2);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        mMediaMuxer.start();
        MediaCodec.BufferInfo info1 = new MediaCodec.BufferInfo();
        info1.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
        int sampleSize1 = 0;
        while ((sampleSize1 = mediaExtractor.readSampleData(buffer, 0)) > 0) {
            info1.offset = 0;
            info1.size = sampleSize1;
            info1.flags = mediaExtractor.getSampleFlags();
            info1.presentationTimeUs += 1000 * 1000 / frameRate1;
            mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info1);
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
            mMediaMuxer.writeSampleData(mAudioTrackIndex, buffer, info2);
            mediaExtractor2.advance();
        }

        try {
            mediaExtractor.release();
            mediaExtractor = null;
            mediaExtractor2.release();
            mediaExtractor2 = null;
        } catch (Exception e) {
        }
        Log.e(TAG, "finsih mix");
    }

    public static final int FORMAT_PCM = 0; // 非下面三者都认为是PCM格式了
    public static final int FORMAT_WAV = 1;
    public static final int FORMAT_MP3 = 2;
    public static final int FORMAT_FLV = 4;
    public static final int FORMAT_UNKNOWN = 5;

    private static int detectFormatByTag(String fileName) {
        if (fileName == null)
            return FORMAT_PCM;

        FileInputStream is;
        byte[] buff = new byte[4];
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return FORMAT_PCM;
        }

        try {
            is.read(buff, 0, 4);
            char c0 = (char) (buff[0] & 0xFF);
            char c1 = (char) (buff[1] & 0xFF);
            char c2 = (char) (buff[2] & 0xFF);
            char c3 = (char) (buff[3] & 0xFF);
            if (c0 == 'I' && c1 == 'D' && c2 == '3') {
                return FORMAT_MP3;
            } else if (c0 == 'F' && c1 == 'L' && c2 == 'V') {
                return FORMAT_FLV;
            } else if (c0 == 'R' && c1 == 'I' && c2 == 'F' && c3 == 'F') {
                return FORMAT_WAV;
            } else if (c0 == 255 && c1 >= 0xE0) {//mp3 frame start with 11111111 111xxxxx
                return FORMAT_MP3;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return FORMAT_PCM;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return FORMAT_UNKNOWN;
    }

}
