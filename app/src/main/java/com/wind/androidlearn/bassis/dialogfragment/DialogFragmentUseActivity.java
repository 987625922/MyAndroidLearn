package com.wind.androidlearn.bassis.dialogfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wyt.zdf.myapplication.R;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/4 17:09
 */
public class DialogFragmentUseActivity extends AppCompatActivity implements NoticeDialogFragment.OnItemClickListener {

    private static Intent newIntent(Context context) {
        return new Intent(context, DialogFragmentUseActivity.class);
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    private NoticeDialogFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_dialog_fragment_notice);
        fragment = new NoticeDialogFragment();
        fragment.setOnItemClickListener(this::onItemClick);
        fragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onItemClick(View v) {
        fragment.dismiss();
        Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
    }
}
