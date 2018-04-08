package com.example.xushiyun.smartbutler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.adapter.GirlGridAdapter;
import com.example.xushiyun.smartbutler.entity.GirlInfo;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.PicassoUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.example.xushiyun.smartbutler.view.MyAlertDialog;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 来整理一下这里的构造吧,首先是为每一张图片设置点击触发事件,
 * 点击后弹出对话框,这里已经在之前进行封装了,然后是什么呢,将对话框其中的图片设置成与photoview进行关联
 * 最后改变style就行了
 */
public class MarketFragment extends Fragment {
    private GridView gv_girl;

    private List<GirlInfo> girlInfoList;
    private GirlGridAdapter girlGridAdapter;

    private MyAlertDialog detail_img;
    private PhotoViewAttacher photoViewAttacher;
    private PhotoView iv_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        gv_girl = view.findViewById(R.id.gv_girl);
        girlInfoList = new ArrayList<>();

        String welfare = null;
        try {
            welfare = URLEncoder.encode("福利","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        L.e(StaticClass.GIRL_URL_PART1+welfare+StaticClass.GIRL_URL_PART2);
        //进行数据的解析工作
        RxVolley.get(StaticClass.GIRL_URL_PART1 + welfare + StaticClass.GIRL_URL_PART2, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i(t);
                parsingJson(t);
            }
        });

        girlGridAdapter = new GirlGridAdapter(getActivity(), girlInfoList);
        gv_girl.setAdapter(girlGridAdapter);

        detail_img = new MyAlertDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.detail_img, R.style.girl_dialog, Gravity.CENTER, R.style.pop_up_anim);

        iv_photo = detail_img.findViewById(R.id.photo_view);

        gv_girl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PicassoUtils.loadImageView(getActivity(), girlInfoList.get(i).getUrl(), iv_photo);
                photoViewAttacher = new PhotoViewAttacher(iv_photo);
                photoViewAttacher.update();
                detail_img.show();
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i);
                GirlInfo girlInfo = new GirlInfo();
                girlInfo.set_id(item.getString("_id"));
                girlInfo.setDesc(item.getString("desc"));
                girlInfo.setType(item.getString("type"));
                girlInfo.setUrl(item.getString("url"));
                girlInfo.setWho(item.getString("who"));
                girlInfoList.add(girlInfo);
            }
//            girlGridAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
