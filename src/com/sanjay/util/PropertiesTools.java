package com.sanjay.util;

import java.util.Properties;

/**
 * Created by zsj_09@hotmail.com on 2014/12/31.
 */
public class PropertiesTools {

    public static final Properties PROPERTIES = new Properties(System.getProperties());



    public static String getFileSeparator(){
        return PROPERTIES.getProperty("file.separator");
    }

    public static String getPathSeparator(){
        return PROPERTIES.getProperty("path.separator");
    }

    public static String getLineSeparator(){
        return PROPERTIES.getProperty("line.separator");
    }

    public static void main(String[] args) {

        Log.d(getFileSeparator());
        Log.d(getPathSeparator());
        Log.d(getLineSeparator());
    }

}
