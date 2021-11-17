package com.karim.igniteBasis.utils;

/**
 * Created by sblim
 * Date : 2021-11-17
 * Time : 오후 2:01
 */
public class LoggerUtils {
    public static String getStackTrace(Exception e) {
        StackTraceElement[] element = e.getStackTrace();
        StringBuffer str = new StringBuffer();

        for(int i = 0; i < element.length && i < element.length; ++i) {
            if (i == 0) {
                str.append(e.toString()).append("\n").append(element[i].getClassName()).append(" : ").append(e.getLocalizedMessage()).append("\n");
            }

            str.append("\tat ").append(element[i].getClassName()).append(".").append(element[i].getMethodName()).append("(").append(element[i].getFileName()).append(":").append(element[i].getLineNumber()).append(")").append("\n");
        }

        element = null;
        return str.toString();
    }
}
