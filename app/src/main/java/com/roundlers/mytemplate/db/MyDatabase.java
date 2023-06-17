package com.roundlers.mytemplate.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.roundlers.mytemplate.constants.BuildConstants;
import com.roundlers.mytemplate.db.dao.UserDao;
import com.roundlers.mytemplate.models.User;

@Database(
        entities = {
                User.class,
        }, version = BuildConstants.DB_VERSION)

@TypeConverters({
        Converter.class
})

public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
