package com.sanjay.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsj_09@hotmail.com on 2014/12/15.
 */
public class FileTools {

    public static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * Verify file' correctness before get FileInputStream
     */
    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }


    public static List<String> readLines(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOTools.readLines(in, encoding);
        } finally {
            IOTools.closeQuietly(in);
        }
    }

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, DEFAULT_ENCODE);
    }


    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("File '" + file + "' could not be created");
            }
        }
        return new FileOutputStream(file);
    }

    public static void writeStringToFileW(File file, String data, String encoding)
            throws IOException {
        OutputStream ot = null;
        BufferedWriter writer = null;
        try {
            ot = openOutputStream(file);
            if (encoding == null) {
                writer = new BufferedWriter(new OutputStreamWriter(ot, DEFAULT_ENCODE));
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(ot, encoding));
            }
            IOTools.write(data, writer);
        } finally {
            IOTools.closeQuietly(writer);
            IOTools.closeQuietly(ot);
        }
    }

    public static void writeStringToFileW(File file, String data) throws IOException {
        writeStringToFileW(file, data, null);
    }

    public static void saveToFile(String path, String data) {
        File f = new File(path);
        try {
            if (f.createNewFile()) {
                writeStringToFileW(f, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeStringToFileO(File file, String data, String encoding)
            throws IOException {
        OutputStream ot = null;
        try {
            ot = openOutputStream(file);
            IOTools.write(data, ot, encoding);
        } finally {
            IOTools.closeQuietly(ot);
        }
    }

    public static void writeStringToFileO(File file, String data) throws IOException {
        writeStringToFileO(file, data, DEFAULT_ENCODE);
    }

    public static List<File> getAllFiles(File root) throws FileNotFoundException {
        if (!root.exists()) {
            throw new FileNotFoundException(root.getAbsolutePath() + " does not exist");
        }

        List<File> filesList = new ArrayList<File>();

        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (files == null) {
                return filesList;
            }

            for (File file : files) {
                filesList.addAll(getAllFiles(file));
            }
        } else {
            filesList.add(root);
        }
        return filesList;
    }

}
