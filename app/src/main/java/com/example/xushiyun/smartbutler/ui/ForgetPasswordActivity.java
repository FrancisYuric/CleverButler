package com.example.xushiyun.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.utils.L;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 感觉首先需要理清楚逻辑,首先,这里需要实现两个功能,第一个是用户修改当前密码,第二个是用户通过邮箱重置密码
 * 那么问题来了,如果我没有理解错的话,这里用户是通过登录界面还没有登录的情况下登录到这个忘记密码界面的,那么就很明显了
 * 这个时候不要求用户输入用户名很明显是不明智的,因为同一个app很可能不只有一个账号在使用
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    //definitions of all objects
    //因为作者的疏忽,所以这里的用户名基本处于可有可无的状态,不过为了界面不是那么生硬,我还是打算将这个留下
    private EditText et_username;
    private EditText et_ori_password;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_modify_password;

    private EditText et_email;
    private Button btn_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView() {
        et_username = findViewById(R.id.et_username);
        et_ori_password = findViewById(R.id.et_ori_password);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);

        btn_modify_password = findViewById(R.id.btn_modify_pass);
        et_email = findViewById(R.id.et_email);
        btn_reset_password = findViewById(R.id.btn_reset_password);

        //设置两个按钮的点击事件
        btn_modify_password.setOnClickListener(this);
        btn_reset_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_pass:
                //进行修改密码的相关操作,当然,这里需要通过用户名来实现,但是很遗憾的事情是,官方貌似并没有提供通过账号来修改密码的操作,但是作者这里YY了一个功能= =,这就比较尴尬了
                checkAndUpdateCurrentPassword();
                break;
            case R.id.btn_reset_password:
                //然后这里实现的功能就是通过邮箱进行密码的重置工作
                updatePasswordThroughEmail();
                break;
        }
    }

    private void updatePasswordThroughEmail() {
        String email = et_email.getText().toString().trim();
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null) {
                    Toast.makeText(ForgetPasswordActivity.this, "邮件发送成功,请登录邮箱进行密码修改", Toast.LENGTH_SHORT).show();
                    ForgetPasswordActivity.this.finish();
                }
                else {
                    Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败,请检查邮箱地址是否正确", Toast.LENGTH_SHORT).show();
                    L.e(e.getMessage());
                }
            }
        });
    }

    //所以无可奈何,这里我也只能按照作者的思路来,其实说真的,在没有登录之前进行密码的修改什么的,貌似真的不怎么实用
    private void checkAndUpdateCurrentPassword() {
        //这个方法需要同时达到验证输入是否规范和= =,提交修改的功能
        //1.检查所有的输入框是否为空
        if(TextUtils.isEmpty(et_ori_password.getText().toString().trim())
                || TextUtils.isEmpty(et_password.getText().toString().trim())
                || TextUtils.isEmpty(et_repassword.getText().toString().trim())) {
            Toast.makeText(this, "输入栏不能为空,请检查后再提交~",Toast.LENGTH_SHORT).show();
        }
        else {
            //检查两个输入的密码是否一致
            if(et_password.getText().toString().trim().equals(et_repassword.getText().toString().trim())) {
                //两者相等,说明,两次输入的密码一致,则向Bmob后端发送请求
                if(!BmobUser.getCurrentUser().getEmailVerified()) {
                    Toast.makeText(this, "该账户未通过邮箱验证,请先进行邮箱验证", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.updateCurrentUserPassword(et_ori_password.getText().toString().trim(), et_password.getText().toString().trim(),
                        new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "密码修改成功!", Toast.LENGTH_SHORT).show();
                                    ForgetPasswordActivity.this.finish();
                                }
                                else {
                                    Toast.makeText(ForgetPasswordActivity.this, "密码修改失败,请重试", Toast.LENGTH_SHORT).show();
                                    L.e(e.getMessage());
                                }
                            }
                        });
            }
            //两个密码输入不一致
            else {
                Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
