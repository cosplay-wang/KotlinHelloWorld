package com.cosplay.kotlin.hw.mixAudio.bean;

import java.io.Serializable;

/**
 * Created by shoulei on 2017/1/18.
 */

public class AudioFragment implements Serializable {
    private static final int ALL_FILE_DURATION = -1;
    public String mFile;
    public int mPosition;
    public int mDuration;

    public AudioFragment(String file, int position, int duration) {
        mFile = file;
        mPosition = position;
        mDuration = duration;
    }

    public AudioFragment(String file, int position) {
        mFile = file;
        mPosition = position;
        mDuration = ALL_FILE_DURATION;
    }
}
