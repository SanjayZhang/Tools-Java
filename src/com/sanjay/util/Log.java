package com.sanjay.util;


import java.util.HashMap;

/**
 * Created by zsj_09@hotmail.com on 2014/12/17.
 */
public class Log {

    public static final boolean debug = true;

    private static final HashMap<String, Boolean> mDebugAble;

    static {
        mDebugAble = new HashMap<String, Boolean>();
    }

    private static void println(String priority, String tag, String msg) {
        if (debug && show(tag)) {
            System.out.println("====" + tag + "====" + priority);
            System.out.println(msg);
        }
    }

    public static void d(String tag, String msg) {
        println("D", tag, msg);
    }

    public static void d(String msg) {
        d("", msg);
    }

    public static void d(String msg, Throwable t) {
        d("", msg);
        if (t != null) {
            t.printStackTrace();
        }
    }

    public static void e(String tag, String msg) {
        println("E", tag, msg);
    }

    public static void e(String msg) {
        e("", msg);
    }

    public static void e(String msg, Throwable t) {
        e("", msg);
        if (t != null) {
            t.printStackTrace();
        }
    }

    private static boolean show(String tag) {
        Boolean b = mDebugAble.get(tag);
        return b == null ? true : b;
    }

}
