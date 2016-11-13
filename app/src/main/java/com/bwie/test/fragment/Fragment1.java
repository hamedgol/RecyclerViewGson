package com.bwie.test.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwie.test.R;
import com.bwie.test.adapter.MyRecyclerAdapter;
import com.bwie.test.bean.Bean;
import com.bwie.test.okhttp.OkHttp;
import com.bwie.test.utils.Tools;
import com.bwie.test.view.PullBaseView;
import com.bwie.test.view.PullRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Fragment1 extends Fragment implements PullBaseView.OnHeaderRefreshListener,PullBaseView.OnFooterRefreshListener{

    String mPath="http://m.yunifang.com/yunifang/mobile/goods/" +
            "getall?random=39986&encode=2092d7eb33e8ea0a7a2113f2d9886c90&category_id=17";
    private List<Bean.DataBean> data;
    private MyRecyclerAdapter recycleAdapter;
    private PullRecyclerView mRecyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(),R.layout.fragment1,null);
        initdata();
        mRecyclerview=(PullRecyclerView)view.findViewById(R.id.recyclerview);
        mRecyclerview.setOnHeaderRefreshListener(this);//设置下拉监听
        mRecyclerview.setOnFooterRefreshListener(this);//设置上拉监听
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        mRecyclerview.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        //设置分隔线
//        mRecyclerview .addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
//        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        return  view;
    }
    private void initdata() {
        OkHttp.getAsync(mPath, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }
            @Override
            public void requestSuccess(String result) throws Exception {
                Bean bean= Tools.parseJsonWithGson(result,Bean.class);
                data = bean.getData();
                recycleAdapter= new MyRecyclerAdapter((ArrayList<Bean.DataBean>) data,getActivity());
                mRecyclerview.setAdapter(recycleAdapter);
                recycleAdapter.setmOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "click " + data.get(position), Toast.LENGTH_SHORT).show();
                    }
                });

                recycleAdapter.setmOnItemLongClickListener(new MyRecyclerAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "Long click " + data.get(position), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mDatas.add("TEXT更多");
                recycleAdapter.notifyDataSetChanged();
                mRecyclerview.onFooterRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  mDatas.add(0, "TEXT新增");
                recycleAdapter.notifyDataSetChanged();
                mRecyclerview.onHeaderRefreshComplete();
            }
        }, 3000);
    }
}
