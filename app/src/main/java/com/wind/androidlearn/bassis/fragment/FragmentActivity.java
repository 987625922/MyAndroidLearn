package com.wind.androidlearn.bassis.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wyt.zdf.myapplication.R;

public class FragmentActivity extends AppCompatActivity {

    private static final int PAGE_HOME = 1;
    private static final int PAGE_WEIKE = 2;
    private static final int PAGE_INFO = 3;

    private int currentPage = 0;

    private OneFragment homeOneFragment = new OneFragment();
    private OneFragment weikeOneFragment = new OneFragment();
    private OneFragment infoOneFragment = new OneFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        changePage(PAGE_HOME);
        findViewById(R.id.img_home_tap1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(PAGE_HOME);
            }
        });

        findViewById(R.id.img_home_tap2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(PAGE_WEIKE);
            }
        });

        findViewById(R.id.img_home_tap3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePage(PAGE_INFO);
            }
        });
    }


    /**
     * 切换页面
     *
     * @param pageType 页面类型
     */
    private void changePage(int pageType) {
        if (this.currentPage == pageType) {
            //不重复打开页面
            return;
        } else {
            this.currentPage = pageType;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        //检查Fragment是否存在
        switch (pageType) {
            case PAGE_INFO:
                if (this.infoOneFragment == null) {
                    this.infoOneFragment = (OneFragment) fragmentManager.findFragmentByTag(OneFragment.class.getSimpleName());
                    if (this.infoOneFragment == null) {
                        this.infoOneFragment = new OneFragment();
                    }
                }
                break;
            case PAGE_WEIKE:
                if (this.weikeOneFragment == null) {
                    this.weikeOneFragment = (OneFragment) fragmentManager.findFragmentByTag(OneFragment.class.getSimpleName());
                    if (this.weikeOneFragment == null) {
                        this.weikeOneFragment = new OneFragment();
                    }
                }
                break;
            case PAGE_HOME:
                if (this.homeOneFragment == null) {
                    this.homeOneFragment = (OneFragment) fragmentManager.findFragmentByTag(OneFragment.class.getSimpleName());
                    if (this.homeOneFragment == null) {
                        this.homeOneFragment = new OneFragment();
                    }
                }
                break;
        }

        //切换页面
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (pageType) {
            case PAGE_INFO:
                fragmentTransaction.replace(R.id.fragmentContainer, this.infoOneFragment);
                break;
            case PAGE_WEIKE:
                fragmentTransaction.replace(R.id.fragmentContainer, this.weikeOneFragment);
                break;
            case PAGE_HOME:
                fragmentTransaction.replace(R.id.fragmentContainer, this.homeOneFragment);
                break;
        }

//        changeButtonState( pageType );
        fragmentTransaction.commit();
    }

}
