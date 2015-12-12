package com.twocater.diamond.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015/12/12.
 */
public class ObjectUtil {

    public static String getFields(Object object) {
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            String sep = "";
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                Method method = object.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                Object value = method.invoke(object);
                sb.append(sep).append(name).append(":").append(String.valueOf(value));
                sep = ",";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
