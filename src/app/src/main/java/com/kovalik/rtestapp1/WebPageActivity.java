package com.kovalik.rtestapp1;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.kovalik.rtestapp1.databinding.ActivityWebPageBinding;

public class WebPageActivity extends AppCompatActivity {

    private ActivityWebPageBinding mViewsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_web_page);



    }
}
