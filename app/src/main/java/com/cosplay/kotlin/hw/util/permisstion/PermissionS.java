package com.cosplay.kotlin.hw.util.permisstion;

import com.yanzhenjie.permission.Permission;

/**
 * Author:wangzhiwei on 2018/9/18.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class PermissionS {
    public static final class Group {
        public static final String[] STUDENTSGROUP = new String[]{
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.RECORD_AUDIO,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_FINE_LOCATION};
    }

}
