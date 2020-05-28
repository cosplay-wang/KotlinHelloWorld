package com.cosplay.kotlin.hw.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author:wangzhiwei on 2020/5/7.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClickEvent {
        int values();
}
