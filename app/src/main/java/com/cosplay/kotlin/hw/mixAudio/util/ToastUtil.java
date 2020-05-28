package com.cosplay.kotlin.hw.mixAudio.util;

import android.widget.Toast;

import com.cosplay.kotlin.hw.APP;
import com.cosplay.kotlin.hw.mixAudio.MainHandler;


public class ToastUtil {

  /**
   * 系统Toast
   */
  public static void showToast(final String text) {
    MainHandler.getInstance().post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(APP.Companion.getContext(), text, Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * 系统Toast
   */
  public static void showToast(final int textId) {
    MainHandler.getInstance().post(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(APP.Companion.getContext(), APP.Companion.getContext().getString(textId), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }
}
