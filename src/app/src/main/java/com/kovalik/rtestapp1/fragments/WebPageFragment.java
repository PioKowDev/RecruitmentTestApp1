package com.kovalik.rtestapp1.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kovalik.rtestapp1.R;
import com.kovalik.rtestapp1.activities.WebPageActivity;
import com.kovalik.rtestapp1.databinding.WebPageFragmentBinding;
import com.kovalik.rtestapp1.enums.HTTPCode;
import com.kovalik.rtestapp1.managers.WebPageSourceProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WebPageFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener,
    Response.ProgressListener {

    private static final String BUNDLE_SOURCE_TEXT = "BUNDLE_SOURCE_TEXT";

    private WebPageFragmentBinding mViewsBinding;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        mViewsBinding = DataBindingUtil.
            inflate(inflater, R.layout.web_page_fragment, null, false);
        if (savedInstanceState != null && savedInstanceState.getString(BUNDLE_SOURCE_TEXT) != null) {
            mViewsBinding.webPageSource.setText(savedInstanceState.getString(BUNDLE_SOURCE_TEXT));
        }
        return mViewsBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        WebPageActivity parentActivity = (WebPageActivity)getActivity();
        if (parentActivity != null) {
            parentActivity.getLoadPageButtonObservable().subscribe(this::loadPage);
        }
    }

    @Override
    public void onPause() {
        WebPageActivity parentActivity = (WebPageActivity)getActivity();
        parentActivity.getLoadPageButtonObservable().unsubscribeOn(AndroidSchedulers.mainThread());

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        outState.putString(BUNDLE_SOURCE_TEXT, mViewsBinding.webPageSource.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private void loadPage(String providedUrl) {
        mViewsBinding.progressLoad.setProgress(0);
        WebPageSourceProvider.getInstance(getActivity().getApplicationContext()).
            getPageSource(providedUrl, this, this, this);
    }

    @Override
    public void onResponse(final String response) {
        mViewsBinding.webPageSource.setText(response);
        mViewsBinding.progressLoad.setProgress(mViewsBinding.progressLoad.getMax());
    }

    @Override
    public void onErrorResponse(final VolleyError error) {
        mViewsBinding.progressLoad.setProgress(mViewsBinding.progressLoad.getMax());
        HTTPCode errorCode = HTTPCode.fromValues(error.networkResponse.statusCode);
        mViewsBinding.webPageSource.setText(HTTPCode.mapToError(errorCode, getResources()));
    }

    @Override
    public void onProgress(final long transferredBytes, final long totalSize) {
        // Progress works correctly only if requested WebPage contains properly
        // set content-length header value in response. Otherwise the totalSize
        // is equal to -1 and we cannot determine the correct max value for progress
        // and show the real one.
        if (totalSize != -1) {
            if (mViewsBinding.progressLoad.getMax() != totalSize) {
                mViewsBinding.progressLoad.setMax((int) totalSize);
            }
            mViewsBinding.progressLoad.setProgress((int) transferredBytes);
        } else {
            if (mViewsBinding.progressLoad.getMax() != 100) {
                mViewsBinding.progressLoad.setMax(100);
            }
            if (mViewsBinding.progressLoad.getProgress() < 90) {
                mViewsBinding.progressLoad.setProgress(mViewsBinding.progressLoad.getProgress() + 1);
            }
        }
    }
}
