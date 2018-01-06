package com.example.xushiyun.smartbutler.utils;

/**
 * Created by xushiyun on 2017/12/5.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.utils
 * File Name:    StaticClass
 * Descripetion: 数据和常量
 */

public class StaticClass {
    public static final String ISFIRSTOPEN = "isfirst";
    public static final int SPLASH = 1001;
    public static final String SPLASH_FONT = "fonts/FONT.TTF";
    public static final String BUGLY_APP_ID = "58b6c9cb5c";
    public static final String BMOB_APP_ID = "7801fcefd53f15f9c2e01615634767bc";

    //用户登录界面的记住密码状态,用户名,密码
    public static final String REM_PASS = "rem_pass";
    public static final String USERNAME = "user_name";
    public static final String PASSWORD = "password";

    //说真的,我真的不知道为什么作者之前一直很坚定地把常量存在staticclass里面,但是唯独UserFragment里面的静态变量他是直接在类中定义的,
    //但是为了统一,我这里还是打算将变量储存到专门储存常量的类里面
    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;

    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;

    //快递查询相应的静态变量
    public static final String COURIER_APPKEY = "b53662b81614aa9933da55bb8aee2999";
    public static final String COURIER_URL_TEST = "http://v.juhe.cn/exp/index?key="+COURIER_APPKEY+"&com=sf&no=575677355677";
    public static final String COURIER_URL_PART1 = "http://v.juhe.cn/exp/index?key=b53662b81614aa9933da55bb8aee2999&com=";
    public static final String COURIER_URL_PART2 = "&no=";

    //手机归属地查询,这里样例代码是先上传appid,但是因为对于get,顺序没有关系
    public static final String PHONE_LOCATION_APPKEY = "28cd8ffb03d22d293ba6cd788d25d91b";
    public static final String PHONE_LOCATION_TEXT = "http://apis.juhe.cn/mobile/get?phone=13429667914&key="+PHONE_LOCATION_APPKEY;
    public static final String PHONE_LOCATION_URL_PART1 = "http://apis.juhe.cn/mobile/get?key="+PHONE_LOCATION_APPKEY+"&phone=";

    //图灵机器人api接口,发送方式为post, key,info,userid来发送数据
    public static final String TURING_URL = "http://www.tuling123.com/openapi/api";
    public static final String TURING_KEY = "99cb5474b81746d798c07594faef200b";

    //使用极速数据,调用微信精选功能api来进行相应的
    public static final String WECHAT_KEY = "017d58b6145cf2bc90c91c166dc8376d";
    public static final String WECHAT_URL = "http://v.juhe.cn/weixin/query?key=017d58b6145cf2bc90c91c166dc8376d";

    //美女社区对应的模块对应参数,这里依然使用的是
    public static final String GIRL_URL_PART1 = "http://gank.io/api/data/";
    public static final String GIRL_URL_PART2 = "/50/1";

    //科大讯飞语音合成功能相关参数
    public static final String XFYUN_APP_ID = "5a2e569f";

    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    //手机端ip地址112.17.238.126
    public static final String CHECK_UPDATE_URL = "http://172.20.10.3:8080/ciruy/config.json";
    //下载进度常量
    public static final int HANDLER_LOADING = 10001;
    public static final int HANDLER_OK = 10002;
    public static final int HANDLER_ON = 10003;
}
