package com.wind.androidlearn.bassis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wind.androidlearn.BaseActivity;
import com.wyt.zdf.myapplication.R;

public class 获取联系人 extends BaseActivity {
    private ListView mContactsListView;
    private Button mGetContactsButton;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, 获取联系人.class);
        return intent;
    }

    public static void intentTo(Context context) {
        context.startActivity(newIntent(context));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_contact;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void findView() {
        mContactsListView = findViewById(R.id.lv_contacts);
        mGetContactsButton = findViewById(R.id.btn_contacts);

    }

    @Override
    protected void setListener() {
        mGetContactsButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(获取联系人.this);
                rxPermissions
                        .request(Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                //getContacts()方法获取联系人的姓名及电话号码
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(获取联系人.this, android.R.layout.simple_list_item_1, getContacts());
                                //将姓名及电话号码显示到ListView上
                                mContactsListView.setAdapter(adapter);
                            } else {
                                // Oups permission denied
                            }
                        });


            }
        });
    }

    @Override
    protected void initData() {

    }


    private String[] getContacts() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        String[] arr = new String[cursor.getCount()];
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                arr[i] = id + " , 姓名：" + name;

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = this.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String num = phonesCusor.getString(0);
                        arr[i] += " , 电话号码：" + num;
                    } while (phonesCusor.moveToNext());
                }
                i++;
            } while (cursor.moveToNext());
        }
        return arr;
    }

}
