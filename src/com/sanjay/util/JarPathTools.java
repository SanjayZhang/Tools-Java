package com.sanjay.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by zsj_09@hotmail.com on 2014/12/15.
 */
public class JarPathTools {

    public static String getPath() {
        String path = JarPathTools.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            //decode with utf-8
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (path.endsWith(".jar"))
            path = path.substring(0, path.lastIndexOf(File.separator) + 1);

        File f = new File(path);
        path = f.getAbsolutePath();

        return path;
    }
}
