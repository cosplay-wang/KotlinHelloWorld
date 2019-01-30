package com.cosplay.kotlin.hw.composite;

/**
 * Author:wangzhiwei on 2019/1/29.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class CompositeUtil {
    public static void main(String[] args) {
        ViewGroupElement rootGroup = new ViewGroupElement("root页面");
        ViewGroupElement music = new ViewGroupElement("音乐");
        ViewGroupElement video = new ViewGroupElement("视屏");
        PageElement ad = new ViewGroupElement("广告");
        rootGroup.addViewElement(music);
        rootGroup.addViewElement(video);
        rootGroup.addViewElement(ad);

        ViewGroupElement chineseMusic = new ViewGroupElement("国语");
        ViewGroupElement cantoneseMusic = new ViewGroupElement("粤语");
        music.addViewElement(chineseMusic);
        music.addViewElement(cantoneseMusic);

        chineseMusic.addViewElement(new ViewElement("十年.mp3"));
        cantoneseMusic.addViewElement(new ViewElement("明年今日.mp3"));

        video.addViewElement(new ViewElement("唐伯虎点秋香.avi"));

        rootGroup.print("");
    }
}
