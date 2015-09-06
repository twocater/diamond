package com.twocater.diamond.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by chenzhiwei on 15-9-2.
 */
public class ExceptionUtil {
    public static String getExceptionInfo(Throwable e) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            e.printStackTrace(printStream);
            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        } catch (Exception ex) {
            return ExceptionUtil.class.getName() + ".getExceptionInfo exception," + ex.getMessage();
        }

    }

    public static void main(String[] args) {
        try {
            throw new Exception("sdfad");
        } catch (Exception e) {
            System.out.println(ExceptionUtil.getExceptionInfo(e));
        }

    }
}
