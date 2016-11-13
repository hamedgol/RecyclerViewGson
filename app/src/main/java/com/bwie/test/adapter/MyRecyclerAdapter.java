package com.bwie.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.R;
import com.bwie.test.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */
public class MyRecyclerAdapter extends
        RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>  {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    private ArrayList<Bean.DataBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;



    public MyRecyclerAdapter(ArrayList<Bean.DataBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        inflater=LayoutInflater.from(mContext);
    }



    //创建新View，被LayoutManager所调用
    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item,parent,false);
        MyViewHolder holder= new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position).getGoods_name());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(mDatas.get(position));

        ImageLoader.getInstance().displayImage(mDatas.get(position).getGoods_img(),holder.img);

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.img);
            tv=(TextView) itemView.findViewById(R.id.concent);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
}
