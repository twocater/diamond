package com.twocater.diamond.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/13.
 */
public class ToStringUtil {

    private static Set<String> types = new HashSet<String>();

    static {
        types.add("int");
        types.add("boolean");
        types.add("class java.lang.String");
        types.add("byte");
    }

    public static String toString(Object object) {
        if (object == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            String sep = "";
            for (Field field : fields) {
                String genericType = field.getGenericType().toString();
                if (types.contains(genericType)) {
                    field.setAccessible(true);
                    String name = field.getName();
                    String prefix = "get";
                    if (genericType.equals("boolean")) {
                        prefix = "is";
                    }
                    try {
                        Method method = object.getClass().getMethod(prefix + name.substring(0, 1).toUpperCase() + name.substring(1));
                        Object value = method.invoke(object);
                        sb.append(sep).append(name).append(":").append(String.valueOf(value));
                        sep = ",";
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String toString(Map<?, ?> map) {
        if (map == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(sep).append(entry);
            sep = ",";
        }
        return sb.toString();
    }
}
