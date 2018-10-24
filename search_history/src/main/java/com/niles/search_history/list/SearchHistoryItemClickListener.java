package com.niles.search_history.list;

import com.niles.search_history.db.SearchHistoryEntity;

/**
 * Created by Niles
 * Date 2018/10/24 18:49
 * Email niulinguo@163.com
 */
public interface SearchHistoryItemClickListener {

    void onItemClicked(SearchHistoryEntity entity);
}
