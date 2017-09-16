package com.kovalik.rtestapp1.requests;

import com.android.volley.Response;
import com.android.volley.request.StringRequest;


public class ProgressStringRequest extends StringRequest implements Response.ProgressListener {

    private Response.ProgressListener mProgressListener;

    public ProgressStringRequest(final int method, final String url,
        final Response.Listener<String> listener, final Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public void setOnProgressListener(Response.ProgressListener listener){
        mProgressListener = listener;
    }

    @Override
    public void onProgress(final long transferredBytes, final long totalSize) {
        if(null != mProgressListener){
            mProgressListener.onProgress(transferredBytes, totalSize);
        }
    }
}
