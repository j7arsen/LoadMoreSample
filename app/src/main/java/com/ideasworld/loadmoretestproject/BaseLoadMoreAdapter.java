package com.ideasworld.loadmoretestproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arsen on 10.08.17.
 */

public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_LOADING = 1;

    protected Context mContext;

    protected boolean isLoading = false;
    protected boolean isMoreItems = true;
    protected int mPage = 1;

    protected int mItemsInPage = 20;

    protected LayoutInflater mLayoutInflater;

    protected List<T> mDataList;

    private ILoadMoreListener mLoadMoreListener;

    public BaseLoadMoreAdapter(Context context, ILoadMoreListener loadMoreListener){
        this.mContext = context;
        this.mLoadMoreListener = loadMoreListener;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mDataList = new ArrayList<>();
    }

    public void addData(List<? extends T> datas) {
        if (datas != null) {
            int start = mDataList.size();
            mDataList.addAll(datas);

            notifyItemRangeChanged(start, datas.size());
        }
    }

    public void addData(T data) {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void addData(T data, int position) {
        mDataList.add(position, data);
        notifyItemChanged(position);
    }

    public void setData(List<? extends T> datas){
        this.mDataList = (List<T>) datas;
        notifyDataSetChanged();
    }

    public void reset() {
        mDataList.clear();
    }

    public void updateData(List<T> items) {
        isLoading = false;
        mPage++;

        if (items == null || items.size() == 0) {
            notifyItemRemoved(getItemCount() - 1);
            isMoreItems = false;
            return;
        }

        if (items.size() < mItemsInPage) {
            isMoreItems = false;
        }
        addData(items);
    }

    public void refresh(){
        isLoading = false;
        isMoreItems = true;
        mPage = 1;
        reset();
        loadData();
    }

    public void errorLoading(){
        isLoading = false;
        isMoreItems = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOADING:
                return new LoadingHolder(mLayoutInflater.inflate(R.layout.item_loadmore_progress, parent, false));
            case TYPE_ITEM:
                return createItem(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0 && !isLoading && isMoreItems && getItemCount() - position <= 3) {
            loadData();
        }

        bindItem(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isMoreItems && mDataList.size() > 0) {
            return mDataList.size() + 1;
        } else {
            return mDataList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isMoreItems && position == getItemCount() - 1) {
            return TYPE_LOADING;
        } else {
            return TYPE_ITEM;
        }
    }

    protected void loadData(){
        isLoading = true;
        if(mLoadMoreListener != null){
            mLoadMoreListener.loadData();
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder{
        ProgressBar mPbLoad;

        public LoadingHolder(View itemView) {
            super(itemView);
            mPbLoad = (ProgressBar) itemView.findViewById(R.id.load_more_progressBar);
        }
    }

    protected abstract RecyclerView.ViewHolder createItem(ViewGroup parent);

    protected abstract void bindItem(RecyclerView.ViewHolder holder, int position);

}