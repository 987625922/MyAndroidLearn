package com.wyt.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/3/26 16:43
 */
public class Entity implements Parcelable {
    public Entity() {
    }

    public Entity(String name) {
        this.name = name;
    }

    private String name;
    public int id;

    protected Entity(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String privateName(){
        return "私有方法";
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        id = dest.readInt();
        name = dest.readString();
    }
}
