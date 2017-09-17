package com.kovalik.rtestapp1.managers;

import android.content.Context;
import android.os.Environment;
import com.android.volley.Response;
import com.kovalik.rtestapp1.tasks.FileReadAsyncTask;

import java.io.*;

public class FileManager {

    private static final String APP_CACHE = "/RTFilesCache/";

    public static void saveFile(final String fileName, final String fileContent) {

        File externalDir = Environment.getExternalStorageDirectory();
        File dir = new File (externalDir.getAbsolutePath() + APP_CACHE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File savedFile = new File(dir, fileName);
        try {
            if (!savedFile.exists() && savedFile.createNewFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
                PrintWriter pw = new PrintWriter(fileOutputStream);
                pw.println(fileContent);
                pw.flush();
                pw.close();
                fileOutputStream.close();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void openAndDisplayFile (final Context context, final String fileName,
        final Response.Listener<String> successListener, final Response.ProgressListener progressListener) {
        File readFile = new File(Environment.getExternalStorageDirectory().
            getAbsolutePath() + APP_CACHE + fileName);
        (new FileReadAsyncTask(context, successListener, progressListener)).execute(readFile);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


}
