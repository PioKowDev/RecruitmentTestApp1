package com.kovalik.rtestapp1.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kovalik.rtestapp1.requests.ProgressStringRequest;

public class WebPageSourceProvider {

    private static final String REQUEST_TAG = "REQUEST_TAG";
    private static WebPageSourceProvider sInstance;
    private RequestQueue mRequestQueue;

    private WebPageSourceProvider(@NonNull Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized WebPageSourceProvider getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new WebPageSourceProvider(context);
        }
        return sInstance;
    }

    public void getPageSource(@NonNull String url, @NonNull Response.Listener<String> successListener,
                              @NonNull Response.ProgressListener progressListener,
                              @NonNull Response.ErrorListener errorListener) {

        ProgressStringRequest
            request = new ProgressStringRequest(Request.Method.GET, url, successListener, errorListener);
        request.setOnProgressListener(progressListener);
        request.setTag(REQUEST_TAG);
        mRequestQueue.add(request);
    }

    public void cancelAllRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQUEST_TAG);
        }
    }
}
