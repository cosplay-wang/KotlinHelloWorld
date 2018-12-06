package com.cosplay.kotlin.hw.ui.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cosplay.kotlin.hw.R;
import com.cosplay.kotlin.hw.util.OrdinaryDialogFive;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpannableStringActivity extends AppCompatActivity {
    TextView textView;
    //int a,b,c,d;
    SpannableStringBuilder style;
    List<HashMap<String,Integer>> pointWords = new ArrayList<>();
    String text = "关于本活动fnsVOSNV<u><b>extra</b></u>更多规则，请点我查看.关于本活sdfsfsfsddsfsdsdadfsdfsf动fnsVOSNV<u><b>extra</b></u>更多规则，请点我查看" +
            "关于本活动fnsVOSNV<u><b>extra</b></u>更多规则，请点我查看." +
            "关于本活动fnsVOSNV<u><b>extra</b></u>更多规则，请点我查看.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_spannable_string);
        textView = findViewById(R.id.tv_spna);
        style = new SpannableStringBuilder();
        ((TextView)textView).setHighlightColor(getResources().getColor(android.R.color.transparent));
        //设置文字
        Log.e("adagd","---"+text.length());
        int count = 0;
        int fromIndex = 0;
        while (true){
            int index = text.indexOf("<u><b>",fromIndex);
            if(-1 != index){
                count++;
                fromIndex = index +1;
            }else{
                break;
            }
        }
        for (int i=0;i<count;i++){
           getPointWordLocation();
        }
        style.append(text);
        for (int i=0;i<count;i++){
            setPointWord(i);
        }
        //配置给TextView
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(style);
    }

    private void setPointWord(int position) {
        int start = pointWords.get(position).get("start");
        int end = pointWords.get(position).get("end");
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.BLACK);
        style.setSpan(colorSpan1, start,  end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan styleSpan1 = new StyleSpan(Typeface.BOLD);//粗体
        style.setSpan(styleSpan1, start,  end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new NoLineClickSpan(""+position), start,  end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        UnderlineSpan underlineSpan1 = new UnderlineSpan();
        style.setSpan(underlineSpan1, start,  end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

    }

    private void getPointWordLocation() {
        int a = text.indexOf("<u>");
        int b = text.indexOf("</u>") - 10;
        if(a>0 && b>0){
            HashMap<String, Integer> pointPosition = new HashMap<String, Integer>();
            pointPosition.put("start",a);
            pointPosition.put("end",b);
            pointWords.add(pointPosition);
            text =  text.replaceFirst("<b>","");
            text = text.replaceFirst("</b>","");
            text = text.replaceFirst("<u>","");
            text = text.replaceFirst("</u>","");
        }

    }

    private void getLoacation(int position) {
        int start = pointWords.get(position).get("start");
        int end = pointWords.get(position).get("end");
        clickedViewLocation[0] = (int)(getPosiion(end,"right")+getPosiion(start,"left"))/2;
        clickedViewLocation[1] = (int)getPosiion(end,"bottom");

    }

    private int getPosiion(int position,String location){
        Layout layout = textView.getLayout();
        Rect bound = new Rect();
        int line = layout.getLineForOffset(position);
        layout.getLineBounds(line, bound);
        int yAxisTop = bound.top;//字符顶部y坐标
        int yAxisBottom = bound.bottom;//字符底部y坐标
        int xAxisLeft = (int)layout.getPrimaryHorizontal(position);//字符左边x坐标
        int xAxisRight = (int)layout.getSecondaryHorizontal(position);//字符右边x坐标
        switch (location){
            case "top":
                return yAxisTop;
            case "bottom":
                return yAxisBottom;
            case "left":
                return xAxisLeft;
            case "right":
                return xAxisRight;
        }
        return -1;
    }
    private  class  NoLineClickSpan extends ClickableSpan{
        String text;

        public NoLineClickSpan(String text) {
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //super.updateDrawState(ds);
            ds.setColor(Color.BLACK);
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View  flag) {
            processHyperLinkClick(text); //点击超链接时调用
        }
    }

    private void processHyperLinkClick(String flag) {
        getLoacation(Integer.parseInt(flag));
        View vii = new View(this);
        vii.setLayoutParams(new LinearLayout.LayoutParams(1,1));
        new BubbleDialog(this)
                .addContentView(LayoutInflater.from(this).inflate(R.layout.activity_finger_print, null))
                .setClickedView(vii,clickedViewLocation[0],clickedViewLocation[1])
                .calBar(true)
                .setOffsetY(10)
                .setPosition(BubbleDialog.Position.BOTTOM)
                .show();
        Toast.makeText(SpannableStringActivity.this, "触发点击事件!"+flag, Toast.LENGTH_SHORT).show();
    }
    private int[] clickedViewLocation = new int[2];
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickedViewLocation[0] = (int)event.getX();
        clickedViewLocation[1] = (int)event.getY();
        return super.onTouchEvent(event);
    }
}
