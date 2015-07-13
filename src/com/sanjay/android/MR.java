package com.sanjay.android;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by zsj_09@hotmail.com on 2015/3/24.
 */
public class MR {

    private static Context mContext;

    private static Class<?> styleable;

    /**
     * 方便使用，调用其他函数需要前需要初始化
     *
     * @param c
     */
    public static void init(Context c) {
        mContext = c;
    }

    private static int getReId(Context context, String className, String name) {
        return context == null ? -1 : context.getResources().getIdentifier(name, className, context.getPackageName());
    }

    /**
     * 系统自带反射获取不到context.getResource().getIdnetifier()，自建反射获取
     *
     * @param context
     * @param type
     * @param name
     * @return
     */
    private static Object getResourceId(Context context, String type, String name) {
        if (context == null)
            return null;
        String className = context.getPackageName() + ".R";
        try {
            Class<?> cls = Class.forName(className);
            for (Class<?> childClass : cls.getClasses()) {
                String simple = childClass.getSimpleName();
                if (simple.equals(type)) {
                    for (Field field : childClass.getFields()) {
                        String fileName = field.getName();
                        if (fileName.equals(name))
                            try {
                                return field.get(null);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Field getField(Class<?> cls, String name) {
        try {
            return cls.getField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * styleable这边增加一个缓存，实际效果未测试
     *
     * @param context
     * @param name
     * @return
     */
    private static Object getStyleable(Context context, String name) {
        if (context == null)
            return null;

        if (styleable == null) {
            String className = context.getPackageName() + ".R$" + "styleable";
            styleable = getClass(className);
        }

        try {
            return styleable.getField(name).get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getReId(String className, String name) {
        return getReId(mContext, className, name);
    }

    public static int drawable(String name) {
        return getReId(mContext, "drawable", name);
    }

    public static int layout(String name) {
        return getReId(mContext, "layout", name);
    }

    public static int id(String name) {
        return getReId(mContext, "id", name);
    }

    public static int string(String name) {
        return getReId(mContext, "string", name);
    }

    public static String getString(String name) {
        return mContext.getResources().getString(string(name));
    }

    public static int raw(String name) {
        return getReId(mContext, "raw", name);
    }

    public static int attr(String name) {
        return getReId(mContext, "attr", name);
    }

    public static int xml(String name) {
        return getReId(mContext, "xml", name);
    }

    public static int style(String name) {
        return getReId(mContext, "style", name);
    }

    public static int styleable(String name) {
        return ((Integer) getStyleable(mContext, name)).intValue();
    }

    public static int[] styleableArray(String name) {
        return (int[]) getStyleable(mContext, name);
    }


    public static int color(String name) {
        return getReId(mContext, "color", name);
    }

    public static int anim(String name) {
        return getReId(mContext, "anim", name);
    }

    public static int dimen(String name) {
        return getReId(mContext, "dimen", name);
    }

    public static int array(String name) {
        return getReId(mContext, "array", name);
    }

    public static byte[] getAssetsBytes(String strAssetFile) {
        try {
            InputStream is = mContext.getAssets().open(strAssetFile);
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
