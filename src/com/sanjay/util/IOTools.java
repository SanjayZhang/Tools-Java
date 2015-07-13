package com.sanjay.util;

import java.io.*;
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
        if (encoding != null)
            return readLines(new InputStreamReader(in, encoding));
        else
            return readLines(new InputStreamReader(in));
    }

    public static void write(String data, Writer writer) throws IOException {
        if (data != null)
            writer.write(data);
    }

    public static void write(String data, OutputStream out) throws IOException {
        if (data != null)
            out.write(data.getBytes());
    }

    public static void write(String data, OutputStream out, String encoding) throws IOException {
        if (data != null)
            if (encoding != null)
                write(data, out);
            else
                out.write(data.getBytes(encoding));
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
            }

    }
}
