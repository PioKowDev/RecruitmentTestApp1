package com.kovalik.rtestapp1.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import com.kovalik.rtestapp1.R;
import com.kovalik.rtestapp1.activities.WebPageActivity;
import com.kovalik.rtestapp1.databinding.WebPageFragmentBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WebPageFragment extends Fragment {

    private WebPageFragmentBinding mViewsBinding;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        mViewsBinding = DataBindingUtil.inflate(inflater, R.layout.web_page_fragment, null, false);
        mViewsBinding.webPage.setWebViewClient(new WebViewClient());
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

    public void loadPage(String providedUrl) {
        mViewsBinding.webPage.loadUrl(providedUrl);
    }

    @Override
    public void onPause() {
        WebPageActivity parentActivity = (WebPageActivity)getActivity();
        parentActivity.getLoadPageButtonObservable().unsubscribeOn(AndroidSchedulers.mainThread());

        super.onPause();
    }
}
