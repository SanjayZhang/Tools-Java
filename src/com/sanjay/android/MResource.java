package com.sanjay.android;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by zsj_09@hotmail.com on 2015/3/24.
 */
public class MResource {

    public static int getSysID(Context context, String className, String name) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getIdentifier(name, className, context.getPackageName());
    }

    public static int getDrawabelID(Context context, String name) {
        return getSysID(context, "drawable", name);
    }

    public static int getLayoutID(Context context, String name) {
        return getSysID(context, "layout", name);
    }

    public static int getViewID(Context context, String name) {
        return getSysID(context, "id", name);
    }

    public static int getStringID(Context context, String name) {
        return getSysID(context, "string", name);
    }

    public static String getStringByName(Context context, String name) {
        return context.getResources().getString(getStringID(context, name));
    }

    public static int getRawID(Context context, String name) {
        return getSysID(context, "raw", name);
    }

    public static int getStyleID(Context context, String name) {
        return getSysID(context, "style", name);
    }

    public static int getColorID(Context context, String name) {
        return getSysID(context, "color", name);
    }

    public static byte[] getAssetsBytes(Context context, String strAssetFile) {
        try {
            InputStream is = context.getAssets().open(strAssetFile);
            if (is != null) {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return buffer;
            }
        } catch (Exception e) {
            System.out.println(Log.getStackTraceString(e));
        }
        return null;
    }

}