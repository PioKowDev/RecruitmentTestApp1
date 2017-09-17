package com.kovalik.rtestapp1.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kovalik.rtestapp1.R;
import com.kovalik.rtestapp1.requests.ProgressStringRequest;

public class WebPageSourceProvider {

    private static final String REQUEST_TAG = "REQUEST_TAG";
    public static final String HTTP_PREFIX = "http://";
    public static final String HTTPS_PREFIX = "https://";
    public static final String WWW_PREFIX = "www.";
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

    public void getPageSource(@NonNull final Context context, @NonNull String url,
                              @NonNull final Response.Listener<String> successListener,
                              @NonNull final Response.ProgressListener progressListener,
                              @NonNull final Response.ErrorListener errorListener) {

        if (isInternetConnectionActive(context)) {
            if (!url.contains(HTTP_PREFIX) && !url.contains(HTTPS_PREFIX)) {
                url = HTTPS_PREFIX + url;
            }
            ProgressStringRequest request =
                new ProgressStringRequest(Request.Method.GET, url,
                    successListener, errorListener);
            request.setOnProgressListener(progressListener);
            request.setTag(REQUEST_TAG);
            mRequestQueue.add(request);
        } else if (FileManager.isExternalStorageReadable()) {
            String fileName = url.replaceAll(HTTP_PREFIX, "").
                replaceAll(HTTPS_PREFIX, "").
                replaceAll(WWW_PREFIX, "");;
            FileManager.openAndDisplayFile(context, fileName, successListener, progressListener);
        } else {
            successListener.onResponse(context.getResources().getString(R.string.error_no_connectivity_no_storage));
        }
    }

    public void cancelAllRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQUEST_TAG);
        }
    }

    private boolean isInternetConnectionActive(final Context context) {
        ConnectivityManager connectivityManager =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
            connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
