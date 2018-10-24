package com.niles.search_history.db;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by Niles
 * Date 2018/10/24 17:49
 * Email niulinguo@163.com
 */
@Database(entities = {SearchHistoryEntity.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyRoomDatabase extends RoomDatabase {

    public static MyRoomDatabase createDatabase(Application app, String dbName) {
        return Room
                .databaseBuilder(app, MyRoomDatabase.class, dbName)
                .allowMainThreadQueries()
                .build();
    }

    public abstract SearchHistoryDao searchHistoryDao();
}
