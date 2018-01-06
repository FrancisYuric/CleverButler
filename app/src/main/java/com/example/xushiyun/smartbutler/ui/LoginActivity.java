package com.example.xushiyun.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.MainActivity;
import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.MyUser;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.ShareUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.example.xushiyun.smartbutler.view.MyAlertDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * at this part,I'd like to display those functions:
 * 1.two edittexts that allow users to inout their username and password
 * 2.single checkbox in order to let user decide if allowing application to remember password
 * 3.button allow users to login
 * 4.textview to solve the problem of forgetting the password
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //definitions of all objects
    private ImageView iv_icon;
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_remember;
    private Button bt_login;
    private Button bt_register;
    private TextView tv_forget;

    private MyAlertDialog loginDialog;

    //记住密码这个操作,大致上有两种做法,第一种是如果成功登录,并且记住密码复选框被选中,则利用ShareUtil记录对应的三个数据
    //另外一种则是放在Loginactivity的destroy方法中来实现,但是说真的,就个人而言,我还是比较喜欢使用前面一种思路,感觉用户体验会比较高
    private static Boolean isRemember = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //感觉每次都要重新输入密码未免也太过于麻烦了,所以这里我打算在闪屏页显示的时候进行是否已经有账户登录的判定,如果已经有账户登录,直接跳转到主界面
        BmobUser bmobUser = MyUser.getCurrentUser();
        if(bmobUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        initView();
    }

    private void initView() {
        iv_icon = findViewById(R.id.iv_icon);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_remember = findViewById(R.id.cb_remember);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        tv_forget = findViewById(R.id.tv_forget);

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);

        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == false) {
                    isRemember = false;
                }
                else isRemember = true;
            }
        });
        Boolean rem_status = ShareUtils.getBoolean(this, StaticClass.REM_PASS, false);
        if(rem_status == true) {
            cb_remember.setChecked(true);
            et_username.setText(ShareUtils.getString(this, StaticClass.USERNAME, ""));
            et_password.setText(ShareUtils.getString(this, StaticClass.PASSWORD, ""));
        }

        loginDialog = new MyAlertDialog(this, R.layout.dialog_login, R.style.login_dialog);
        loginDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.bt_login:
                //这个函数用于判定账号密码是否匹配,是否经过邮箱验证,如果均满足要求,则OK,登录
                checkAccountAndSignIn();
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    private void checkAccountAndSignIn() {
        loginDialog.show();
        //用户名密码为空的检查
        if(et_username.getText().toString().trim().equals("")||et_password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "用户名,密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final MyUser myUser = new MyUser();
        myUser.setUsername(et_username.getText().toString().trim());
        myUser.setPassword(et_password.getText().toString().trim());
        myUser.login(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                loginDialog.dismiss();
                if(e == null) {
                    if(!myUser.getEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "请前往邮箱验证~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                    saveStatus();
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "登录失败,请检查用户名密码是否正确", Toast.LENGTH_SHORT).show();
                    L.e(e.getMessage());
                }
            }
        });
    }

    private void saveStatus() {
        if(isRemember == true) {
            //这里还有一个细节,如果用户更改密码了,如果这里进行成功登陆了,应该重新赋值对应的密码
            if(!ShareUtils.getBoolean(this, StaticClass.REM_PASS, false)) {
                ShareUtils.putBoolean(this, StaticClass.REM_PASS, true);
            }
                ShareUtils.putString(this, StaticClass.USERNAME, et_username.getText().toString().trim());
                ShareUtils.putString(this, StaticClass.PASSWORD, et_password.getText().toString().trim());
        }
        else {
            if(ShareUtils.getBoolean(this, StaticClass.REM_PASS, false)) {
                ShareUtils.deleteShare(this, StaticClass.PASSWORD);
                ShareUtils.deleteShare(this, StaticClass.USERNAME);
                ShareUtils.deleteShare(this, StaticClass.REM_PASS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
