package com.wind.androidlearn.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface UserEntityDao {

    @Query("select * FROM User")
    List<UserEntity> getUserList();

    @Query("select * FROM User WHERE name = :name")
    UserEntity getUserByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(UserEntity userEntity);

    @Delete()
    void deleteUser(UserEntity userEntity);

    @Query("select * FROM User")
    LiveData<List<UserEntity>> getLiveUserList();

}

