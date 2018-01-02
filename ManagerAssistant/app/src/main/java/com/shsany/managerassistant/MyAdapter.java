package com.shsany.managerassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 2017/10/20.
 */

public class MyAdapter<T> extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    List<T> mData = null;

    protected View.OnClickListener mOnClickListener;

    public MyAdapter(Context context,List<T> data){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_home,null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.message = (TextView) convertView.findViewById(R.id.tv_message);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.message.setText(mData.get(position).toString());
        return convertView;
    }
    class ViewHolder{
        TextView title,message,time;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    /**
     * 添加数据
     * @param aData
     */
    public void addData(List<T> aData){
        if (aData != null && aData.size() > 0){
            mData.addAll(aData);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     * @return
     */
    public List<T> getmData(){
        return mData;
    }

    /**
     * 设置数据
     * @param sData
     */
    public void setmData(List<T> sData){
        mData.clear();
        addData(sData);
    }
}
