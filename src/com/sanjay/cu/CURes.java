package com.sanjay.cu;

import com.sanjay.util.FileTools;
import com.sanjay.util.PropertiesTools;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zsj_09@hotmail.com on 2014/12/30.
 */
public class CURes {

    static String BASE_PATH = "C:\\Users\\Administrator\\Desktop\\work\\text\\lint\\";

    /**
     * @param f
     * @return
     */
    public static String clearUnusedResources(File f) {
        List<String> list = null;
        try {
            list = FileTools.readLines(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        String line;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            line = (String) it.next();

            if (line.contains("UnusedResources") && !line.contains("res\\values") && !line.contains("appcompat")) {
                int end = line.indexOf(":");
                if (end != -1) {
                    sb.append(line.substring(0, end));
                    sb.append("\r\n");
                }
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {

        args = new String[1];
        args[0] = "lint.txt";

        if (args.length < 1) {
            System.out.println("没有输入参数");
            return;
        }

        File f = new File(BASE_PATH + args[0]);
        String s = clearUnusedResources(f);
        if (s != null && !s.equals("")) {
            String out = f.getParent() + PropertiesTools.getFileSeparator() + "out.txt";
            FileTools.saveToFile(out, s);
            System.out.println("成功！ 文件路径:" + out);
        }

    }
}
