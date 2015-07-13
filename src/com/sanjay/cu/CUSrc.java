package com.sanjay.cu;

import com.sanjay.util.FileTools;
import com.sanjay.util.PropertiesTools;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zsj_09@hotmail.com on 2014/12/30.
 */
public class CUSrc {

    static String BASE_PATH = "C:\\Users\\Administrator\\Desktop\\work\\text\\ucdetector_reports\\";

    /**
     * 这边提供的的文件地址需要使用UCDector运行后获取
     *
     * @return
     */
    public static String clear0Reference(File f) {
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

            if (line.contains("Class") && line.contains("has 0 reference") && !line.contains("Method")) {
                int end = line.indexOf(".<init>");
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
        args[0] = "UCDetectorReport_001.txt";

        if (args.length < 1) {
            System.out.println("没有输入参数");
            return;
        }

        File f = new File(BASE_PATH + args[0]);
        String s = clear0Reference(f);
        if (s != null && !s.equals("")) {
            String out = f.getParent() + PropertiesTools.getFileSeparator() + "out.txt";
            FileTools.saveToFile(out, s);
            System.out.println("成功！ 文件路径:" + out);
        }
    }
}
