package com.kovalik.rtestapp1.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.android.volley.Response;
import com.kovalik.rtestapp1.R;

import java.io.*;

public class FileReadAsyncTask extends AsyncTask<File, Long, String> {

    private Response.Listener<String> mListener;
    private Response.ProgressListener mProgressListener;
    private Context mContext;

    public FileReadAsyncTask(final Context context, final Response.Listener<String> listener, final
        Response.ProgressListener progressListener) {
        mContext = context;
        mListener = listener;
        mProgressListener = progressListener;
    }

    @Override
    protected String doInBackground(final File... files) {
        File readFile = files[0];
        if (readFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(readFile);
                int readSize = 0;
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                    readSize += line.length();
                    publishProgress((long)readSize, readFile.length());
                }
                reader.close();
                final String fileContent = sb.toString();
                fin.close();
                return fileContent;
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(final Long... values) {
        mProgressListener.onProgress(values[0], values[1]);
    }

    @Override
    protected void onPostExecute(final String fileContent) {
        if (!fileContent.equals("")) {
            mListener.onResponse(fileContent);
        } else {
            mListener.onResponse(mContext.getResources().getString(R.string.error_no_connectivity_no_file));
        }
    }
}
