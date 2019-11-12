package com.joshua.androidparam.utils;

import java.lang.reflect.Method;

/**
 * Created by joshua on 2019-11-11
 * Describe:
 */
public class YSystemPropertiesUtil {
    public static String get(String property) {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
            return (String) declaredMethod.invoke(null, property);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getStringList(String property, String separator) {
        String value = get(property);
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return value.split(separator);
        }
    }

    public static String get(String property, String def) {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class,
                    String.class);
            return (String) declaredMethod.invoke(null, property, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
