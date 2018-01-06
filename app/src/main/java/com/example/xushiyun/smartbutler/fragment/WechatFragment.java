package com.example.xushiyun.smartbutler.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.adapter.NewsListAdapter;
import com.example.xushiyun.smartbutler.entity.NewsData;
import com.example.xushiyun.smartbutler.ui.WebContentActivity;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WechatFragment extends Fragment {

    private ListView lv_wechat;
    private NewsListAdapter newsListAdapter;
    private List<NewsData> newsDataList;

    private List<String> titles;
    private List<String> urls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        lv_wechat = view.findViewById(R.id.lv_wechat);

        newsDataList = new ArrayList<>();
        titles = new ArrayList<>();
        urls = new ArrayList<>();

        getDatas();
        newsListAdapter = new NewsListAdapter(getActivity(), newsDataList);

        lv_wechat.setAdapter(newsListAdapter);

        lv_wechat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebContentActivity.class);
                intent.putExtra("url", urls.get(i));
                intent.putExtra("title", titles.get(i));
                getActivity().startActivity(intent);
            }
        });
    }

    private void getDatas() {
        RxVolley.get(StaticClass.WECHAT_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i(t);
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for(int i = 0; i < list.length(); i++) {
                String pic = ((JSONObject)list.get(i)).getString("firstImg");
                String title = ((JSONObject)list.get(i)).getString("title");
                String url = ((JSONObject)list.get(i)).getString("url");
                String name = ((JSONObject)list.get(i)).getString("source");
                NewsData newsData = new NewsData();
                newsData.setPic(pic);
                newsData.setTitle(title);
                newsData.setUrl(url);
                newsData.setFrom(name);
                newsDataList.add(newsData);

                urls.add(url);
                titles.add(title);
            }
//            newsListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
