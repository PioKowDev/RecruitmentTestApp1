package com.kovalik.rtestapp1.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jakewharton.rxbinding2.view.RxView;
import com.kovalik.rtestapp1.R;
import com.kovalik.rtestapp1.databinding.ActivityWebPageBinding;
import com.kovalik.rtestapp1.fragments.WebPageFragment;
import io.reactivex.Observable;

public class WebPageActivity extends AppCompatActivity {

    private static final String WEB_PAGE_FRAGMENT_TAG = "WEB_PAGE_FRAGMENT_TAG";

    private ActivityWebPageBinding mViewsBinding;
    private WebPageFragment mWebPageFragment;

    private Observable<String> mButtonObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_page);

        final FragmentManager fm = getSupportFragmentManager();

        mWebPageFragment = (WebPageFragment) fm.findFragmentByTag(WEB_PAGE_FRAGMENT_TAG);
        if (mWebPageFragment == null) {
            mWebPageFragment = new WebPageFragment();

            fm.beginTransaction().add(R.id.pageContentLayout, mWebPageFragment, WEB_PAGE_FRAGMENT_TAG).commit();
        }

        mButtonObservable = RxView.clicks(mViewsBinding.loadPageButton).
            flatMap(click -> Observable.just(mViewsBinding.pageAddressEditText.getText().toString()));
    }

    public Observable<String> getLoadPageButtonObservable() {
        return mButtonObservable;
    }

}
