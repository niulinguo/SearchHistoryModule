package com.niles.search_history.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Niles
 * Date 2018/10/24 17:34
 * Email niulinguo@163.com
 */
@Dao
public interface SearchHistoryDao {

    @Query("SELECT * FROM tb_search_history WHERE `key` = :key")
    SearchHistoryEntity findByKey(String key);

    @Query("SELECT * FROM tb_search_history Order By last_search_time DESC")
    LiveData<List<SearchHistoryEntity>> findAll();

    @Delete
    void delete(SearchHistoryEntity... entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(SearchHistoryEntity entity);
}
