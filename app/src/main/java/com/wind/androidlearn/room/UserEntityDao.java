package com.wind.androidlearn.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

}

