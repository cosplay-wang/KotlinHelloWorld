package com.cosplay.kotlin.hw.inject;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author:wangzhiwei on 2020/5/7.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ViewUtilsTest {


    public static void inject(final Activity activity) {


        Class clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        /**
         * 通过字节码获取activity类中所有的字段，在获取Field的时候一定要使用
         * getDeclaredFields(),
         * 因为只有该方法才能获取到任何权限修饰的Filed，包括私有的。
         */
        //一个Activity中可能有多个Field，因此遍历。
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //设置为可访问，暴力反射，就算是私有的也能访问到
            field.setAccessible(true);

            //获取到字段上面的注解对象
            ViewInject annotation = (ViewInject)field.getAnnotation(ViewInject.class);
            //一定对annotation是否等于null进行判断，因为并不是所有Filed上都有我们想要的注解
            if (annotation == null)
            {
                continue;
            }
            //获取注解中的值
            int id = annotation.value();

            //获取控件
            View view = activity.findViewById(id);
            try
            {
                //将该控件设置给field对象
                field.set(activity, view);
                Log.e("injectUtil", field.getName()+":"+id);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        //获取所有的方法（私有方法也可以获取到）
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            ClickEvent clickEvent = (ClickEvent)method.getAnnotation(ClickEvent.class);
            if(clickEvent!=null){

               final int viewId =  clickEvent.values();
               final View view = activity.findViewById(viewId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(activity,view,viewId,"11");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Log.e("injectUtil",viewId+"");
            }
        }
    }
}
