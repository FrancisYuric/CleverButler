package com.example.xushiyun.smartbutler.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xushiyun.smartbutler.MainActivity;
import com.example.xushiyun.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    //滑动页面
    private ViewPager mViewpager;
    //容器
    private List<View> views;
    private View view1, view2, view3;
    private ImageView dot1, dot2, dot3;
    private ImageView skip;
    private Button goto_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        views = new ArrayList<>();
        //四个视图的初始化工作
        view1 = View.inflate(this, R.layout.pager_view_one, null);
        view2 = View.inflate(this, R.layout.pager_view_two, null);
        view3 = View.inflate(this, R.layout.pager_view_three, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

        mViewpager = (ViewPager) findViewById(R.id.mViewPager);
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        refreshState(true, false, false);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        refreshState(false, true, false);
                        skip.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        refreshState(false, false, true);
                        skip.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //指引点的操作
        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        dot3 = (ImageView) findViewById(R.id.dot3);
        refreshState(true, false, false);

        skip = (ImageView) findViewById(R.id.skip);
        skip.setOnClickListener(this);

        goto_main = (Button) view3.findViewById(R.id.btn_goto);
        goto_main.setOnClickListener(this);
    }
    private void refreshState(Boolean dot1_s, Boolean dot2_s, Boolean dot3_s) {
        if(dot1_s == true) {
            dot1.setImageResource(R.drawable.point_on);
        }else {
            dot1.setImageResource(R.drawable.point_off);
        }
        if(dot2_s == true) {
            dot2.setImageResource(R.drawable.point_on);
        }else {
            dot2.setImageResource(R.drawable.point_off);
        }
        if(dot3_s == true) {
            dot3.setImageResource(R.drawable.point_on);
        }else {
            dot3.setImageResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:case R.id.btn_goto:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
