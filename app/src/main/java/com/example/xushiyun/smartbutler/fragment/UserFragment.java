package com.example.xushiyun.smartbutler.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.MyUser;
import com.example.xushiyun.smartbutler.ui.CourierActivity;
import com.example.xushiyun.smartbutler.ui.PhoneLocationActivity;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.ShareUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.example.xushiyun.smartbutler.utils.UtilsTools;
import com.example.xushiyun.smartbutler.view.MyAlertDialog;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 简单先想想相片操作这一块该怎么实现,从大体功能上着手吧,首先需要使用CircleImageView组件用于显示圆形的图片,
 * 然后第二,设置对应的圆形图片点击触发的事件,也就是显示AlertDialog,第三,如何设置这个AlertDialog用于显示三个内容,分别是使用相机拍一张,
 * 从相册库选一张,取消,第四,点击这三个事件触发的事件分析,如何使用Intent访问相册或图库获取照片,如何进行相应的裁剪,如何将裁剪完的图片使用到app中
 * 其中第四部分是最难的
 * 除此之外,背景图片需要尽量设置的小一点,不然会引起app的卡顿的现象
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;

    private Button btn_save;
    private Button btn_edit;
    private Button btn_logout;

    //然后接下来就是对应的circleImageView的获取以及对应的相关操作了
    private CircleImageView circleImageView;

    //这个组件用于显示点击图片之后的弹出框,唯一的区别是弹出框的界面之类的会产生不同
    private MyAlertDialog alertDialog;

    //保险起见,还是定义alertdialog中的3个button按钮吧= =,万一有用呢,三个按钮的分别作用为通过拍照来获取对应的图片,从相册中获取对应的图片,取消
    private Button btn_capture;
    private Button btn_photo;
    private Button btn_cancel;

    private TextView tv_courier;
    private TextView tv_location;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user, container, false);

        findView(view);
        return view;
    }

    private void findView(View view) {
        //实例化需要用到的组件,然后对其中的button的组件需要进行相应的设置相应的点击触发事件,另外,特别的一个组件是btn_save这个需要根据对应的状态设置对应的显示和隐藏
        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);

        circleImageView = view.findViewById(R.id.profile_image);
        //获取了imageView之后进行对其点击事件的设置
        circleImageView.setOnClickListener(this);

        //调用封装的组件从SP中拿出对应的图片
        Bitmap bitmap = UtilsTools.getImageFromShare(getContext());
        if(bitmap != null)
            circleImageView.setImageBitmap(UtilsTools.getImageFromShare(getContext()));

        //获取用户信息并且再页面上显示
        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        et_username.setText(myUser.getUsername());
        et_sex.setText(myUser.getSex() == true? "男":"女");
        et_age.setText(myUser.getAge());
        et_desc.setText(myUser.getDesc());


        //设置4个EditText的默认格式为不能编辑,只有当点击了编辑按钮的时候,这四个组件才进入可以编辑的状态
        setEditTextStatus(false);

        btn_save = view.findViewById(R.id.btn_save);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_edit = view.findViewById(R.id.btn_edit);

        btn_save.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_logout.setOnClickListener(this);


        //实例化alertDialog,并且设置其为不能取消,感觉这块太多了,直接再创建一个方法进行储存吧
        initAlertDialog();

        tv_courier = view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);

        tv_location = view.findViewById(R.id.tv_location);
        tv_location.setOnClickListener(this);
    }

    private void initAlertDialog() {
        alertDialog = new MyAlertDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                R.layout.image_pop_up, R.style.photo_dialog, Gravity.BOTTOM);
        alertDialog.setCancelable(false);

        //然后进行相应的组件的实例化
        btn_capture = alertDialog.findViewById(R.id.btn_capture);
        btn_photo = alertDialog.findViewById(R.id.btn_photo);
        btn_cancel = alertDialog.findViewById(R.id.btn_cancel);


        //为这三个按钮设置点击触发事件
        btn_capture.setOnClickListener(this);
        btn_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    private void setEditTextStatus(boolean status) {
        et_username.setEnabled(status);
        et_sex.setEnabled(status);
        et_age.setEnabled(status);
        et_desc.setEnabled(status);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                //当点击了btn_save时
                //获取4个组件中的字符串的信息,并且进行相应的判断,判定是否可以进行提交,并显示相应的提示信息
                //如果操作成功,那么应该将4个组件重新设置成不能编辑的状态,隐藏save按钮,如果失败,则无所为
                String username = et_username.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(sex)||TextUtils.isEmpty(age)||TextUtils.isEmpty(desc)) {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyUser myUser = new MyUser();
                    myUser.setUsername(username);
                    myUser.setSex(sex.equals("男")?true:false);
                    myUser.setAge(age);
                    myUser.setDesc(desc);
                    BmobUser bmobUser = MyUser.getCurrentUser();
                    myUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                                setEditTextStatus(false);
                                btn_save.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(getActivity(), "修改失败,请检查后再试", Toast.LENGTH_SHORT).show();
                                L.e(e.getMessage());
                            }
                        }
                    });
                }

                break;
            case R.id.btn_edit:
                //当点击了编辑按钮的时候,那么四个组件就会进入可以编辑状态,同时,save按钮也会显示
                setEditTextStatus(true);
                btn_save.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_logout:
                MyUser.logOut();
                BmobUser currentUser = MyUser.getCurrentUser();
                L.i("current user:" + (currentUser.toString().equals("")?"null":currentUser.toString()));
                //点击登出按钮,应该跳转到对应的LoginActivity
                break;
            case R.id.profile_image:
                //点击profile_image之后弹出alertDialog对话框
                alertDialog.show();
                //然后剩余的操作就交给alertDialog来实现
                break;
                //因为功能方面的原因,这里无论点击三个按钮中的哪个按钮最后dialog都会消失
            case R.id.btn_capture:
                //打开摄像机
                toCamera();
                alertDialog.dismiss();
                break;
            case R.id.btn_photo:
                //打开仓库
                toStash();
                alertDialog.dismiss();
                break;
            case R.id.btn_cancel:
                alertDialog.dismiss();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_location:
                startActivity(new Intent(getActivity(), PhoneLocationActivity.class));
                break;
        }
    }

    //跳转相册
    private void toStash() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, StaticClass.IMAGE_REQUEST_CODE);
    }

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存是否可用,可用的话就进行储存
        //说实话,这里应该分开来看
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
    }

    private File tempFile;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册的数据
                case StaticClass.IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机的数据
                case StaticClass.CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case StaticClass.RESULT_REQUEST_CODE:
                    //有可能在裁剪图片的时候点了了舍弃,这样以来返回的值就变成了null
                    if(data != null) {
                        setImageToView(data);
                        //既然已经设置了图片,我们原先的文件就应该删除
                        if(tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if(bundle != null) {
            Bitmap bitmap  = bundle.getParcelable("data");
            circleImageView.setImageBitmap(bitmap);
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if(uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", true);
        //裁剪宽高
        intent.putExtra("aspectX", 1 );
        intent.putExtra("aspectY", 1 );
        //设置裁剪图片的质量,就是他的分辨率
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);

        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, StaticClass.RESULT_REQUEST_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtilsTools.putImageToShare(getActivity(), circleImageView.getDrawable());
    }
}
