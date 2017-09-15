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
import com.kovalik.rtestapp1.databinding.WebPageFragmentBinding;


public class WebPageFragment extends Fragment {

    private WebPageFragmentBinding dataBinding;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.web_page_fragment, null, false);
        dataBinding.webPage.setWebViewClient(new WebViewClient());
        return dataBinding.getRoot();
    }
}
