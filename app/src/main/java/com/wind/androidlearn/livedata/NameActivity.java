package com.wind.androidlearn.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyt.zdf.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
//livedata的使用
public class NameActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn)
    Button btn;
    private NameViewModel mModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NameActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        ButterKnife.bind(this);
        // Other code to setup the activity...

        // Get the ViewModel.
        mModel = ViewModelProviders.of(this).get(NameViewModel.class);


        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                tvName.setText(newName);
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.getCurrentName().setValue(btn.getText().toString());
            }
        });

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getCurrentName().observe(this, nameObserver);
    }
}
