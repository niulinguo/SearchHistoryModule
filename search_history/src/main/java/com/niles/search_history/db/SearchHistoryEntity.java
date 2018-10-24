package com.niles.search_history.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Niles
 * Date 2018/10/24 17:30
 * Email niulinguo@163.com
 */
@Entity(tableName = "tb_search_history")
public class SearchHistoryEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "key")
    private String key;
    @ColumnInfo(name = "create_time")
    private Date createTime;
    @ColumnInfo(name = "last_search_time")
    private Date lastSearchTime;
    @ColumnInfo(name = "search_count")
    private int searchCount;

    public SearchHistoryEntity(@NonNull String key) {
        this.key = key;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastSearchTime() {
        return lastSearchTime;
    }

    public void setLastSearchTime(Date lastSearchTime) {
        this.lastSearchTime = lastSearchTime;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }
}
