package com.learn.learn.room;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.learn.learn.R;
import com.wyt.common.base.BaseActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RoomActivity extends BaseActivity {

    TextView tvContent;
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
    protected void initView() {
        tvContent = findViewById(R.id.tv_content);
        add = findViewById(R.id.add);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void start() {
        add.setOnClickListener(v -> {

            addRoom().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(userEntities -> tvContent.setText(userEntities));
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String userEntities) throws Exception {
                            tvContent.setText(userEntities);
                        }
                    });

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