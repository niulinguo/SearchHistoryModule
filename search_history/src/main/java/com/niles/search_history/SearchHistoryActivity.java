package com.niles.search_history;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.niles.search_history.db.MyRoomDatabase;
import com.niles.search_history.db.SearchHistoryDao;
import com.niles.search_history.db.SearchHistoryEntity;
import com.niles.search_history.list.SearchHistoryAdapter;
import com.niles.search_history.list.SearchHistoryItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity implements SearchHistoryItemClickListener {

    private final List<SearchHistoryEntity> mSearchDataList = new ArrayList<>();
    private SearchHistoryAdapter mAdapter;
    private MyRoomDatabase mDatabase;
    private String mSearchKey = "";
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        mEditText = findViewById(R.id.et_edit);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchKey = s.toString();
                notifyAdapterDataListChange();
            }
        });
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_UNSPECIFIED:
                    case EditorInfo.IME_ACTION_SEARCH: {
                        hideSoftInput();
                        String text = v.getText().toString();
                        if (!TextUtils.isEmpty(text)) {
                            onSearch(text);
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        mAdapter = new SearchHistoryAdapter(this, this);
        recyclerView.setAdapter(mAdapter);

        mDatabase = MyRoomDatabase.createDatabase(getApplication(), "search_history.db");
        LiveData<List<SearchHistoryEntity>> allLiveData = mDatabase.searchHistoryDao().findAll();
        allLiveData.observe(this, new Observer<List<SearchHistoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<SearchHistoryEntity> entities) {
                mSearchDataList.clear();
                if (entities != null) {
                    mSearchDataList.addAll(entities);
                }
                notifyAdapterDataListChange();
            }
        });
    }

    private void notifyAdapterDataListChange() {
        List<SearchHistoryEntity> list = new ArrayList<>();
        if (TextUtils.isEmpty(mSearchKey)) {
            list.addAll(mSearchDataList);
        } else {
            for (SearchHistoryEntity entity : mSearchDataList) {
                if (entity.getKey().contains(mSearchKey)) {
                    list.add(entity);
                }
            }
        }
        mAdapter.setDataList(list);
        mAdapter.notifyDataSetChanged();
    }

    private void onSearch(String text) {
        SearchHistoryDao historyDao = mDatabase.searchHistoryDao();
        SearchHistoryEntity entity = historyDao.findByKey(text);
        Date time = new Date();
        if (entity == null) {
            entity = new SearchHistoryEntity(text);
            entity.setCreateTime(time);
            entity.setLastSearchTime(time);
            entity.setSearchCount(1);
        } else {
            entity.setLastSearchTime(time);
            entity.setSearchCount(entity.getSearchCount() + 1);
        }
        historyDao.insertOrUpdate(entity);

        Toast.makeText(this, "查找" + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(SearchHistoryEntity entity) {
        mEditText.setText(entity.getKey());
    }

    public void onClearClicked(View view) {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("清楚历史记录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.searchHistoryDao().delete(mSearchDataList.toArray(new SearchHistoryEntity[0]));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }
}
