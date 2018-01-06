package com.example.xushiyun.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhoneLocationActivity extends BaseActivity implements View.OnClickListener{

    //这里因为有12个相似的按钮的原因,但是重复同样的操作感觉很伤眼,所以这里还是直接使用数组进行对应的数据的存取
    //然后用List对对应的组件进行操作
    private static final int[] keyboard_ids = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn_del,
    R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn0, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_sear};
    private List<Button> buttons;

    private static final int[] src_ids = {R.drawable.china_mobile, R.drawable.china_telecom, R.drawable.china_unicom};

    private EditText et_phone_number;
    private ImageView iv_logo;
    private TextView tv_desc;

    private Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_location);

        initView();
    }

    private void initView() {
        et_phone_number = findViewById(R.id.et_phone_num);
        iv_logo = findViewById(R.id.iv_logo);
        tv_desc = findViewById(R.id.tv_location);
        buttons = new ArrayList<>();
        for(int i = 0; i < keyboard_ids.length; i++) {
            Button btn = findViewById(keyboard_ids[i]);
            btn.setOnClickListener(this);
            buttons.add(btn);
        }
        buttons.get(3).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_phone_number.setText("");
                return true;
            }
        });

        flag = false;
    }

    @Override
    public void onClick(View view) {
        String num;
        switch (view.getId()) {
            case R.id.btn0: case R.id.btn1: case R.id.btn2:
            case R.id.btn3: case R.id.btn4: case R.id.btn5:
            case R.id.btn6: case R.id.btn7: case R.id.btn8:
            case R.id.btn9:
                num = et_phone_number.getText().toString().trim();
                if(flag || !TextUtils.isDigitsOnly(num)) {
                    et_phone_number.setText("");
                    flag = false;
                }
                num = et_phone_number.getText().toString().trim();
                et_phone_number.setText(num + ((Button)view).getText().toString().trim());
                et_phone_number.setSelection(num.length() + 1);
                break;
            case R.id.btn_del:
                flag = false;
                num = et_phone_number.getText().toString().trim();
                //del中进行的操作有两个,一个是短按,另外一个是长按操作
                if(!TextUtils.isEmpty(num) && num.length() > 0) {
                    et_phone_number.setText(num.substring(0, num.length() - 1));
                    et_phone_number.setSelection(num.length() - 1);
                }
                break;
            case R.id.btn_sear:
                getPhoneLocation();
                break;
        }
    }

    private void getPhoneLocation() {
        String phone_num = et_phone_number.getText().toString().trim();
        if(TextUtils.isEmpty(phone_num)) {
            Toast.makeText(this, "输入栏不能为空", Toast.LENGTH_SHORT).show();
        }
        else {
            RxVolley.get(StaticClass.PHONE_LOCATION_URL_PART1 + phone_num, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    try {
                        JSONObject data = new JSONObject(t);
                        String resultcode = data.getString("resultcode");
                        if(resultcode.equals("200")) {
                            L.i(t);
                            flag = true;
                            JSONObject result = data.getJSONObject("result");
                            String province = result.getString("province");
                            String city = result.getString("city");
                            String areacode = result.getString("areacode");
                            String zip = result.getString("zip");
                            String company = result.getString("company");
                            String card = result.getString("card");
                            L.i(city);
                            tv_desc.setText("province: " + province + "\ncity: " + city + "\nareacode: " + areacode
                            + "\nzip: " + zip + "\ncompany: " + company + "\ncard:" + card + "\n" );
                            switch (company) {
                                case "移动":
                                    iv_logo.setImageResource(R.drawable.china_mobile);
                                    break;
                                case "联通":
                                    iv_logo.setImageResource(R.drawable.china_unicom);
                                    break;
                                case "电信":
                                    iv_logo.setImageResource(R.drawable.china_telecom);
                                    break;
                            }
                        }
                        else {
                            //返回值不为200说明错误,然后给出对应toast
                            Toast.makeText(PhoneLocationActivity.this, "接收错误,请重新尝试", Toast.LENGTH_SHORT);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
