package com.cosplay.kotlin.hw.util;

import android.util.Log;

import java.lang.reflect.Field;

/**
 * Author:wangzhiwei on 2019/3/12.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class ClassAttriNullUtils {
    //判断该对象是否
    public static boolean isAllFieldNull(Object obj){
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性

            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = null;// 得到此属性的值
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (val == null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
               // flag = false;
              //  break;
                Log.e("classAttri", f.getName());
            }
        }
        return flag;
    }
}
