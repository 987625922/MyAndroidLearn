package com.learn.learn.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String name;
    private String age;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserEntity(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "name:" + name + ",age:" + age;
    }
}