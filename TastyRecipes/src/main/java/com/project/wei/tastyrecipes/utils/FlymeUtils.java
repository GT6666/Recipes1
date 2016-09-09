package com.project.wei.tastyrecipes.utils;

import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by acer on 2016/9/8.
 * 检验是否是魅族手机
 */
public class FlymeUtils {
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

}
