package com.sanjay.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by zsj_09@hotmail.com on 2014/12/15.
 */
public class JarPathTools {

    public static String getPath() {
        URL url = JarPathTools.class.getProtectionDomain().getCodeSource().getLocation();

        String path = null;
        try {
            //处理中文
            path = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (path.endsWith(".jar"))
            path = path.substring(0, path.lastIndexOf("/") + 1);

        File f = new File(path);
        path = f.getAbsolutePath();

        return path;
    }
}
