package com.wind.androidlearn.bassis.datasave;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wind.androidlearn.BaseActivity;

public class SQLiteActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        //创建StuDBHelper对象
        StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, 1);
        //得到一个可读的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getReadableDatabase();
    }

    //更新数据
    public void update() {
        StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, 1);
        //得到一个可写的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //生成ContentValues对象 //key:列名，value:想插入的值
        ContentValues cv = new ContentValues();
        //往ContentValues对象存放数据，键-值对模式
        cv.put("id", 1);
        cv.put("sname", "xiaoming");
        cv.put("sage", 21);
        cv.put("ssex", "male");
        //调用insert方法，将数据插入数据库
        db.insert("stu_table", null, cv);
        //关闭数据库
        db.close();

    }

    //查询数据
    public void query() {
        StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, 1);
        //得到一个可写的数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //参数1：表名
        //参数2：要想显示的列
        //参数3：where子句
        //参数4：where子句对应的条件值
        //参数5：分组方式
        //参数6：having条件
        //参数7：排序方式
        Cursor cursor = db.query("stu_table", new String[]{"id", "sname", "sage", "ssex"}, "id=?", new String[]{"1"}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("sname"));
            String age = cursor.getString(cursor.getColumnIndex("sage"));
            String sex = cursor.getString(cursor.getColumnIndex("ssex"));
            System.out.println("query------->" + "姓名：" + name + " " + "年龄：" + age + " " + "性别：" + sex);
        }
        //关闭数据库
        db.close();
    }

    //修改数据
    public void modify() {
        StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, 1);
        //得到一个可写的数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sage", "23");
        //where 子句 "?"是占位符号，对应后面的"1",
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(1)};
        //参数1 是要更新的表名
        //参数2 是一个ContentValeus对象
        //参数3 是where子句
        db.update("stu_table", cv, whereClause, whereArgs);
    }

    //删除数据
    public void delete() {

        StuDBHelper dbHelper = new StuDBHelper(SQLiteActivity.this, "stu_db", null, 1);
        //得到一个可写的数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String whereClauses = "id=?";
        String[] whereArgs = {String.valueOf(2)};
        //调用delete方法，删除数据
        db.delete("stu_table", whereClauses, whereArgs);

    }
}
