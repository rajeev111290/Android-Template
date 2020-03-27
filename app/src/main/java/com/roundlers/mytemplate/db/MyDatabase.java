package com.roundlers.mytemplate.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

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
