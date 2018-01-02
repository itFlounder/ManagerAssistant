package com.shsany.managerassistant.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2018/1/2.
 */

public abstract class BaseRecylerAdapter<T> extends RecyclerView.Adapter<MyRecylerViewHolder> implements View.OnClickListener,View.OnLongClickListener {
    protected List<T> mDatas = new ArrayList<>();
    private int mLayoutId;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecylerAdapter(Context context,List<T> mDatas,int mLayoutId){
        mLayoutInflater = LayoutInflater.from(context);
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
    }

    @Override
    public MyRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId,parent,false);
        if (mOnItemClickListener != null){
            view.setOnClickListener(this);
        }
        if (mOnItemLongClickListener != null){
            view.setOnLongClickListener(this);
        }
        MyRecylerViewHolder holder = new MyRecylerViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyRecylerViewHolder holder, int position) {
        holder.getHolderView().setTag(position);
        convert(holder,position);
    }
    public abstract void convert(MyRecylerViewHolder holder, int position);

    public T getItem(int position){
        return mDatas != null && mDatas.size()> position ? mDatas.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void addItem(T item){
        addItem(item);
    }

    public void addAllItem(List<T> items,boolean isNotify){
        mDatas.addAll(items);
        if (isNotify){
            notifyDataSetChanged();
        }
    }

    public void addAllItem(List<T> items){
        addAllItem(items,true);
    }

    public void clearItems(){
        mDatas.clear();
    }

    public void addAllAndClear(List<T> items){
        clearItems();
        addAllItem(items);
    }

    /**
     * 点击事件的处理
     */

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,position);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int position = (Integer)v.getTag();
        if (mOnItemLongClickListener != null){
            mOnItemLongClickListener.onItemLongClick(v,position);
            return true;
        }
        return false;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View v,int position);
    }

    /**
     * 该方法需要在setAdapter之前调用
     * @param mOnItemClickListener
     * @return
     */
    public BaseRecylerAdapter<T> setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
        return this;
    }

    /**
     * 该方法需要在setAdapter之前调用
     * @param mOnItemLongClickListener
     * @return
     */
    public BaseRecylerAdapter<T> setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;
        return this;
    }
}
