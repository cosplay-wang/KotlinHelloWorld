package com.cosplay.wzw.demo.musiclibrary.entity;

/**
 * Author:wangzhiwei on 2019/8/2.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class MusicEntity {
    String musicID;
    String name;

    public MusicEntity(String musicID, String name) {
        this.musicID = musicID;
        this.name = name;
    }

    public MusicEntity(String musicID) {
        this.musicID = musicID;
    }

    public String getMusicID() {
        return musicID;
    }

    public void setMusicID(String musicID) {
        this.musicID = musicID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
