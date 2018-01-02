package com.shsany.managerassistant.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shsany.managerassistant.R;

import java.util.ArrayList;

/**
 * Created by PC on 2017/11/13.
 */

public class PositionFragment extends Fragment{

    private ListView mListView;
    private ArrayList mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_position,null);
        mListView = (ListView)view.findViewById(R.id.lv_position);
        mData = new ArrayList();
        setmData();
        mListView.setAdapter(new MyAdapter(getActivity()));
        return view;

    }
    public void setmData(){
        for (int i = 0; i <50; i++){
            mData.add(i,"this is title" + (i+1));
        }
    }

    class MyAdapter extends BaseAdapter{
        public LayoutInflater inflater;
        public Context context;
        public MyAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
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
                convertView = inflater.inflate(R.layout.item_home,null);
                holder.t1 = (TextView)convertView.findViewById(R.id.tv_title);
                holder.t2 = (TextView)convertView.findViewById(R.id.tv_message);
                holder.t3 = (TextView)convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.t1.setText(mData.get(position).toString());
            return convertView;
        }
    }

    class ThisMyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    class ViewHolder{
        TextView t1,t2,t3;
    }
}
