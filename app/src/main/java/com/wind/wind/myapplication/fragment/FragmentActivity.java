package com.wind.wind.myapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wyt.zdf.myapplication.R;

public class FragmentActivity extends AppCompatActivity {

    private static final int PAGE_HOME = 0;
    private static final int PAGE_WEIKE = 1;
    private static final int PAGE_INFO = 2;

    private int currentPage = 0;

    private Fragment homeFragment = new Fragment();
    private Fragment weikeFragment = new Fragment();
    private Fragment infoFragment = new Fragment();
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
                if (this.infoFragment == null) {
                    this.infoFragment = (Fragment) fragmentManager.findFragmentByTag(Fragment.class.getSimpleName());
                    if (this.infoFragment == null) {
                        this.infoFragment = new Fragment();
                    }
                }
                break;
            case PAGE_WEIKE:
                if (this.weikeFragment == null) {
                    this.weikeFragment = (Fragment) fragmentManager.findFragmentByTag(Fragment.class.getSimpleName());
                    if (this.weikeFragment == null) {
                        this.weikeFragment = new Fragment();
                    }
                }
                break;
            case PAGE_HOME:
                if (this.homeFragment == null) {
                    this.homeFragment = (Fragment) fragmentManager.findFragmentByTag(Fragment.class.getSimpleName());
                    if (this.homeFragment == null) {
                        this.homeFragment = new Fragment();
                    }
                }
                break;
        }

        //切换页面
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (pageType) {
            case PAGE_INFO:
                fragmentTransaction.replace(R.id.fragmentContainer, this.infoFragment);
                break;
            case PAGE_WEIKE:
                fragmentTransaction.replace(R.id.fragmentContainer, this.weikeFragment);
                break;
            case PAGE_HOME:
                fragmentTransaction.replace(R.id.fragmentContainer, this.homeFragment);
                break;
        }

//        changeButtonState( pageType );
        fragmentTransaction.commit();
    }

}
