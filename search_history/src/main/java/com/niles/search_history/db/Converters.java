package com.niles.search_history.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Niles
 * Date 2018/10/24 17:53
 * Email niulinguo@163.com
 */
public class Converters {

    @TypeConverter
    public static Date longToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }
}
