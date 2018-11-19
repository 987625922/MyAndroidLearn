package com.wind.androidlearn.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.wind.androidlearn.BaseActivity;
import com.wyt.zdf.myapplication.R;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.add)
    Button add;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RoomActivity.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_room;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {


        add.setOnClickListener(v -> {

            addRoom().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userEntities -> tvContent.setText(userEntities));
        });


    }

    private Observable<String> addRoom() {
        return new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                UserEntity userEntity = new UserEntity("NAME", "AGE");
                AppDatabase.getDatabase(getApplicationContext()).getUserEntityDao().addUser(userEntity);

                List<UserEntity> list = AppDatabase.getDatabase(getApplicationContext())
                        .getUserEntityDao().getUserList();
                String user = "";
                for (int i = 0; i < list.size(); i++) {
                    user += list.get(i).toString();
                }
                observer.onNext(user);
            }
        };
    }


}