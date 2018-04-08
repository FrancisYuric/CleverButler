package com.example.xushiyun.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.xushiyun.smartbutler.fragment.ButlerFragment;
import com.example.xushiyun.smartbutler.fragment.MarketFragment;
import com.example.xushiyun.smartbutler.fragment.UserFragment;
import com.example.xushiyun.smartbutler.fragment.WechatFragment;
import com.example.xushiyun.smartbutler.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //tablayout
    private TabLayout mTablayout;
    //viewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //创建浮动按钮
    private FloatingActionButton fbtn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

//        CrashReport.testJavaCrash();
    }


    //数据的初始化工作
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("话题精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new MarketFragment());
        mFragment.add(new UserFragment());
    }

    //界面的初始化工作
    private void initView() {
        fbtn_setting = (FloatingActionButton) findViewById(R.id.icon_setting);
        //设置fbtn按钮默认形态为不可见
        fbtn_setting.setVisibility(View.INVISIBLE);
        fbtn_setting.setOnClickListener(this);
        mTablayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.setOffscreenPageLimit(mFragment.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "position:" + position);
                if(position == 0)
                    fbtn_setting.setVisibility(View.GONE);
                else fbtn_setting.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
