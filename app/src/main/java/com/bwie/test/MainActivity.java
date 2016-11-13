package com.bwie.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bwie.test.fragment.Fragment1;
import com.bwie.test.fragment.Fragment2;
import com.bwie.test.fragment.Fragment3;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager vp;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private ArrayList<Button> list;
    private ArrayList<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vp=(ViewPager)findViewById(R.id.vp);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        list=new ArrayList<Button>();
        list.add(btn1);
        list.add(btn2);
        list.add(btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        getFragMent();
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<list.size();i++){
                    if(i==position){
                        list.get(i).setBackgroundColor(Color.BLUE);
                    }else{
                        list.get(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                vp.setCurrentItem(0);
                break;
            case R.id.btn2:
                vp.setCurrentItem(1);
                break;
            case R.id.btn3:
                vp.setCurrentItem(2);
                break;
        }
    }

    private void getFragMent() {
        listFragment=new ArrayList<Fragment>();
        listFragment.add(new Fragment1());
        listFragment.add(new Fragment2());
        listFragment.add(new Fragment3());
    }
}
