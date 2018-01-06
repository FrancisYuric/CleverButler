package com.example.xushiyun.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.MyUser;
import com.example.xushiyun.smartbutler.utils.L;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    //definitions of all the objects
    private EditText et_username;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup rg_sex;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_email;
    private Button btn_register;

    private Boolean default_gender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_age = findViewById(R.id.et_age);
        et_desc = findViewById(R.id.et_desc);
        rg_sex = findViewById(R.id.rg_sex);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);
        et_email = findViewById(R.id.et_email);
        btn_register = findViewById(R.id.btn_register);

        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rb_male)
                    default_gender = true;
                else default_gender = false;
            }
        });
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                checkIfFitRegisterAndSendRequest();
                break;
        }
    }

    //创建一个方法,用来判定是否达到能注册的标准
    private void checkIfFitRegisterAndSendRequest() {
        if(TextUtils.isEmpty(et_username.getText()) || TextUtils.isEmpty(et_age.getText())
                ||TextUtils.isEmpty(et_password.getText())||TextUtils.isEmpty(et_repassword.getText())
                ||TextUtils.isEmpty(et_email.getText())) {
            //如果出了描述之外有任何一个位置为空,则判断缺乏必要信息
            Toast.makeText(this, "缺乏必要信息,请检查后完善~", Toast.LENGTH_SHORT).show();
        }
        else if(!et_password.getText().toString().equals(et_repassword.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不同,请检查~" + et_password.getText() + " " + et_repassword.getText(), Toast.LENGTH_SHORT).show();
        }
        else {
            //创建一个MyUser对象,如果介绍没有填的话,默认为"这个人很懒,没有写任何东西~"
            MyUser myUser = new MyUser();
            if(et_desc.getText().toString().trim().equals("")) {
                myUser.setDesc("这个人很懒,没有写任何东西~");
            }else {
                myUser.setDesc(et_desc.getText().toString().trim());
            }
            myUser.setAge(et_age.getText().toString().trim());
            myUser.setSex(default_gender);
            myUser.setEmail(et_email.getText().toString().trim());
            myUser.setPassword(et_password.getText().toString().trim());
            myUser.setUsername(et_username.getText().toString().trim());
            myUser.signUp(new SaveListener<MyUser>() {

                @Override
                public void done(MyUser myUser, BmobException e) {
                    if(e == null) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        RegisterActivity.this.finish();
                    }
                    else {
//                        Toast.makeText(RegisterActivity.this, "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        L.e(e.getMessage());
                    }
                }
            });
        }
    }

}
