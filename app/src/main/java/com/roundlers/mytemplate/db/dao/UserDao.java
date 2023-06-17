package com.roundlers.mytemplate.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.roundlers.mytemplate.models.User;

import java.util.ArrayList;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insert(ArrayList<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertSingleRecord(User user);

    @Delete
    int delete(User user);

    @Update
    void updateRecord(User user);


    @Query("DELETE FROM User")
    public void nukeTable();

//    @Query("SELECT * FROM User WHERE userType=:followType AND daoUserId=:userId")
//    Single<List<User>> getUsers(String followType, String userId);
//
//
//    @Query("SELECT * FROM User WHERE userType=:followType AND daoUserId=:daoUserId AND userId=:userId")
//    Single<List<User>> fetchSingleUser(String followType, String userId, String daoUserId);
//
//    @Query("SELECT * FROM User WHERE userType=:userType AND daoUserId=:daoUserId AND userId=:userId")
//    User fetchUserById(String userType, String userId, String daoUserId);
//
//
//    @Query("SELECT User.userId FROM User WHERE userType=:followType AND daoUserId=:myId AND userId =:userId")
//    String getUserById(String followType, String myId, String userId);
//
//
//    @Query("DELETE FROM User WHERE userType=:followType AND daoUserId=:daoUserId")
//    void deleteUsersForUserType(String followType, String daoUserId);
//


}
