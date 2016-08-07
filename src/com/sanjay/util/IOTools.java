package com.sanjay.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsj_09@hotmail.com on 2014/12/29.
 */
public class IOTools {


    public static List<String> readLines(Reader input) throws IOException {
        List list = new ArrayList();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new LineNumberReader(input);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } finally {
            IOTools.closeQuietly(bufferedReader);
        }
        return list;
    }

    public static List<String> readLines(InputStream in, String encoding) throws IOException {
        return readLines(new InputStreamReader(in, encoding));
    }

    public static void write(String data, Writer writer) throws IOException {
        if (data == null) {
            return;
        }

        writer.write(data);
    }

    public static void write(String data, OutputStream out, String encoding) throws IOException {
        if (data == null) {
            return;
        }

        out.write(data.getBytes(encoding));
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            //do nothing
        }
    }
}
