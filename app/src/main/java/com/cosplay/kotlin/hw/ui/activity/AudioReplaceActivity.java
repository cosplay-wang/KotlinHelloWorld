package com.cosplay.kotlin.hw.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cosplay.audiocompose.AudioMp3Utils;
import com.cosplay.audiocompose.VideoMp4Utils;
import com.cosplay.audiocompose.audioBase.AudioDecode;
import com.cosplay.audiocompose.audioBase.AudioEncodeUtil;
import com.cosplay.audiocompose.bean.Audio;
import com.cosplay.audiocompose.bean.AudioFragment;
import com.cosplay.audiocompose.util.IAudioCompose;
import com.cosplay.kotlin.hw.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.cosplay.audiocompose.audioBase.FileNameUtils.getWavNameByOther;
import static com.cosplay.audiocompose.util.AudioUtil.changeSam;
import static com.cosplay.audiocompose.util.AudioUtil.getAudioFromPath;

public class AudioReplaceActivity extends AppCompatActivity implements IAudioCompose {
    Button btCompose,btDesperate,btComposeVideo;
    String oriAudio = "/storage/emulated/0/demo2/sound/5832acf4e4a41.mp3";
    String outAudio = "/storage/emulated/0/demo2/demo2Out.mp3";
    String oriMp4 = "/storage/emulated/0/demo2/video.mp4";
    String deperateMp4 = "/storage/emulated/0/demo2/videoDeperate.mp4";
    String deperateMp3 = "/storage/emulated/0/demo2/videoDeperate.mp3";
    String composeMp4 = "/storage/emulated/0/demo2/videoCompose.mp4";
    String testMp3 = "/storage/emulated/0/demo2/bgm.mp3";
    List<AudioFragment> audioFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_replace);
        btCompose = findViewById(R.id.bt_compose);
        btCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/sound/1578387115609813095_bj", 1006, 2665));
                audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/sound/1578387121067394592_bj", 4917, 3066));
                audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/sound/1578387126602536370_bj", 9330, 2598));
               // audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/Android1.mp3", 18, 21));
              //  audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/Android3.mp3", 25, 28));
              //  audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert1.mp3", 18, 21));
               // audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert2.mp3", 30, 36));
               // audioFragmentList.add(new AudioFragment("/storage/emulated/0/demo2/insert3.mp3", 40, 46));
                try {
                    Log.e("allusetime", "start:" + System.currentTimeMillis() + "--");
                    AudioMp3Utils.replaceOriginByRecord(oriAudio, audioFragmentList, outAudio,AudioReplaceActivity.this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btDesperate = findViewById(R.id.bt_despetate);
        btDesperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                VideoMp4Utils.separateMP4(oriMp4,deperateMp3,deperateMp4,isSuccess);
            }
        });
        btComposeVideo = findViewById(R.id.bt_compsevideo);
        btComposeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // VideoMp4Utils.mergeMP4MP3(deperateMp3,deperateMp4,composeMp4);
                try {
                    Audio audio = Audio.createAudioFromFile(new File(testMp3));
                    Log.e("sfih",audio.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void composeFinsih() {
        Log.e("allusetime", "stopuse:" + (System.currentTimeMillis()) + "--");
    }
}
