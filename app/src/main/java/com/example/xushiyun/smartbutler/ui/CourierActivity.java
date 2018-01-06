package com.example.xushiyun.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.adapter.CourierAdapter;
import com.example.xushiyun.smartbutler.entity.CourierInfo;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends AppCompatActivity implements View.OnClickListener {

    //首先是获取这些对应的组件,分别是公司的名字,快递的单号,搜索按钮,快递信息列表
    private EditText et_company;
    private EditText et_courier_code;
    private Button btn_search;
    private ListView lv_courier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_company = findViewById(R.id.et_company);
        et_courier_code = findViewById(R.id.et_courier_code);
        btn_search = findViewById(R.id.btn_search);
        lv_courier = findViewById(R.id.lv_courier);

        //第一阶段,为搜索按钮设置点击触发事件,使用toast和log进行相应的反馈
        //第二阶段,将反馈回来的数据进行解析并显示在listview界面上
        /**
         * 接下来进行第二阶段的实现
         * 和以往一样,程序需要对用户的输入框进行获取,如果输入框内的内容为空的情况下,则程序有必要做出
         * 相应的提示,提示用户,输入框不能为空
         * 如果满足对应的发送条件,那么用户点击发送按钮就能正常发送对应的请求,如果请求返回值为成功,也就是返回的信息中
         * result的值为200(这里个人感觉需要做出一个判断,因为发现Onsuccess方法在请求失败的时候也会执行onsuccess方法)
         * 那么就开始信息的解析,并将解析传送到listview里面
         *
         * 1.首先,URL的拼接,这里我还是打算简单行事,其实感觉用post会更加直白和简单,但是这里我还是想从简单的做起
         * 这里get的参数总共有三个,appid,公司名,然后是快递单号,因为appid是固定的,所以这里实际上变化的只有两个参数
         */
        btn_search.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                //第一阶段先尝试默认的调试URL能够实现
                //获取两个参数信息
                String company_name = et_company.getText().toString().trim();
                String courier_code = et_courier_code.getText().toString().trim();
                //判断信息是否为空
                if(TextUtils.isEmpty(company_name)||TextUtils.isEmpty(courier_code)) {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    //当输入框满足条件时进行下一步操作
                    RxVolley.get(StaticClass.COURIER_URL_PART1 + company_name +
                                    StaticClass.COURIER_URL_PART2 + courier_code, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //当输入成功之后进行相应的操作,进行json数据的解析工作,同时解析操作需要实现一个返回值
                            //如果返回值为true,那么说明返回成功,result值为200
                            L.i(t);
                            decodeJson(t);
                        }
                    });
                }
               break;
        }
    }

    private Boolean decodeJson(String t) {
        try {
            //将返回的字符串转化成json数据
            JSONObject data = new JSONObject(t);
            String resultCode = data.getString("resultcode");
            if(!resultCode.equals("200")) {
                L.i("bbbbbb" + " " + resultCode);
                return false;
            }
            else {
                L.i("aaaaaa");
                JSONObject result = data.getJSONObject("result");
                //这个信息就是对应的每个路段的信息了
                JSONArray infos = result.getJSONArray("list");
                List<CourierInfo> courierInfos = new ArrayList<>();
                for(int i = 0; i < infos.length(); i++) {
                    CourierInfo courierInfo = new CourierInfo();
                    JSONObject jsonObject = (JSONObject) infos.get(i);
                    courierInfo.setDatetime(jsonObject.getString("datetime"));
                    courierInfo.setRemark(jsonObject.getString("remark"));
                    courierInfo.setZone(jsonObject.getString("zone"));
                    L.i(courierInfo.toString());
                    courierInfos.add(courierInfo);
                }
                Collections.reverse(courierInfos);
                lv_courier.setAdapter(new CourierAdapter(this, courierInfos));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
