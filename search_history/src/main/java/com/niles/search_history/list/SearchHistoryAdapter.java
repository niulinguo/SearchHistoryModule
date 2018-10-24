package com.niles.search_history.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niles.search_history.R;
import com.niles.search_history.db.SearchHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niles
 * Date 2018/10/24 17:59
 * Email niulinguo@163.com
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.Holder> {

    private final Context mContext;
    private final List<SearchHistoryEntity> mDataList = new ArrayList<>();
    private final SearchHistoryItemClickListener mItemClickListener;

    public SearchHistoryAdapter(Context context, SearchHistoryItemClickListener itemClickListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
    }

    public void setDataList(List<SearchHistoryEntity> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_search_history, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final SearchHistoryEntity entity = mDataList.get(i);
        holder.mTextView.setText(entity.getKey());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static final class Holder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        Holder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_text);
        }
    }
}
