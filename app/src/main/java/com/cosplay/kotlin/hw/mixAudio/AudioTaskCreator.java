package com.cosplay.kotlin.hw.mixAudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cosplay.kotlin.hw.mixAudio.bean.AudioFragment;

import java.io.Serializable;
import java.util.List;

/**
 */
public class AudioTaskCreator {
  public static final String ACTION_AUDIO_MIX = "audio_action_mix";
  public static final String ACTION_AUDIO_CUT = "audio_action_cut";
  public static final String ACTION_AUDIO_INSERT = "audio_action_insert";
  public static final String ACTION_AUDIO_REPLACE = "audio_action_replace";
  public static final String ACTION_AUDIO_COVER = "audio_action_cover";

  public static final String PATH_LIST = "path_list";
  public static final String PATH_1 = "path_1";
  public static final String PATH_2 = "path_2";
  public static final String START_TIME = "start_time";
  public static final String END_TIME = "end_time";

  public static final String PROGRESS_AUDIO_1 = "progress_audio_1";
  public static final String PROGRESS_AUDIO_2 = "progress_audio_2";

  /**
   * 启动音频裁剪任务
   * @param context
   * @param path
   */
  public static void createCutAudioTask(Context context, String path, float startTime, float endTime){

    Intent intent = new Intent(context, AudioTaskService.class);
    intent.setAction(ACTION_AUDIO_CUT);
    intent.putExtra(PATH_1, path);
    intent.putExtra(START_TIME, startTime);
    intent.putExtra(END_TIME, endTime);

    context.startService(intent);
  }

  /**
   *
   * @param context
   * @param path1
   * @param path2
   */
  public static void createInsertAudioTask(Context context, String path1, List<AudioFragment>  listRecord){

    Intent intent = new Intent(context, AudioTaskService.class);
    intent.setAction(ACTION_AUDIO_INSERT);
    intent.putExtra(PATH_1, path1);
    intent.putExtra("list",(Serializable)listRecord);
    context.startService(intent);
  }
  /**
   *
   * @param context
   * @param path1
   * @param listRecord
   */
  public static void createReplaceAudioTask(Context context, String path1, List<AudioFragment>  listRecord){

    Intent intent = new Intent(context, AudioTaskService.class);
    intent.setAction(ACTION_AUDIO_REPLACE);
    intent.putExtra(PATH_1, path1);
    intent.putExtra("list",(Serializable)listRecord);
    context.startService(intent);
  }


  /**
   *
   * @param context
   * @param path1
   * @param path2
   * @param progress1
   * @param progress2
   */
  public static void createMixAudioTask(Context context, String path1, String path2, float progress1, float progress2){

    Intent intent = new Intent(context, AudioTaskService.class);
    intent.setAction(ACTION_AUDIO_MIX);
    intent.putExtra(PATH_1, path1);
    intent.putExtra(PATH_2, path2);
    intent.putExtra(PROGRESS_AUDIO_1, progress1);
    intent.putExtra(PROGRESS_AUDIO_2, progress2);

    context.startService(intent);
  }



}
