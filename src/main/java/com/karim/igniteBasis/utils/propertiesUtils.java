package com.karim.igniteBasis.utils;

/**
 * Created by sblim
 * Date : 2021-11-26
 * Time : 오전 11:05
 */
public class propertiesUtils {
    public static String[] getStringArray(String key) {
        String[] returnValue = key.split(",");
        return returnValue;
    }


    public static String urlParsing(String url){
        String normalUrl;
        String ipPort;

        normalUrl = url.split("://")[1];
        ipPort = normalUrl.split("/")[0];

        return ipPort;
    }
}
