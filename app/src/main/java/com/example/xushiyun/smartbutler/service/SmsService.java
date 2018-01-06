package com.example.xushiyun.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.example.xushiyun.smartbutler.view.DispatchLinearLayout;

/**
 * 在这里进行短信服务的设置,具体希望实现的功能是监听手机收到的短信,然后进行显示
 * 这个开关的开启是在settingactivtiy里面进行设置
 */
public class SmsService extends Service implements View.OnClickListener {
    private SmsReceiver smsReceiver;
    private HomeWatchBroadcastReceiver homeWatchBroadcastReceiver;
    private WindowManager.LayoutParams layoutParams;
    private WindowManager wm;
    private DispatchLinearLayout mView;
    private TextView tv_sms_from;
    private TextView tv_sms_content;

    public SmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i("sms service start");
        init();
    }

    private void init() {
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        intent.addAction(StaticClass.SMS_ACTION);
        intent.setPriority(Integer.MAX_VALUE);
        registerReceiver(smsReceiver, intent);

        homeWatchBroadcastReceiver = new HomeWatchBroadcastReceiver();
        IntentFilter intent_home = new IntentFilter();
        intent.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeWatchBroadcastReceiver, intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("sms service stop");
        unregisterReceiver(smsReceiver);
        unregisterReceiver(homeWatchBroadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reply:
                sendSms(tv_sms_from.getText().toString().trim(), tv_sms_content.getText().toString().trim());
                wm.removeView(mView);
                break;
        }
    }


    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.i("来短信了");
            if(StaticClass.SMS_ACTION.equals(action)) {
//                L.i("来短信了");
                //获取短信内容返回的是一个Object数组
                Object[] objs = (Object[])intent.getExtras().get("pdus");
                String smsPhone = null, smsContent = null;
                //便利数组得到相关的数据
                for(Object obj:objs) {
                    //把数组元素转化成短信对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                    smsPhone = sms.getOriginatingAddress();
                    //内容
                    smsContent = sms.getMessageBody();
                    L.i("smsPhone:" + smsPhone + "," + "smsContent:" + smsContent);
                }
                showWindow(smsPhone, smsContent);
            }
        }
    }


    //理一下逻辑,首先是注册广播接收器用于接收短信信息,然后是跳出一个窗口用于显示三个组件,短信发送方,短信内容
    //回复按钮,点击回复按钮之后跳转到对应的短信回复界面,OK
    private void  showWindow(String smsPhone, String smsContent) {
        wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type  = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        //加载布局
        mView = (DispatchLinearLayout) View.inflate(getApplication(), R.layout.sms_item, null);

        tv_sms_from = mView.findViewById(R.id.tv_sms_from);
        tv_sms_content = mView.findViewById(R.id.tv_sms_content);
        tv_sms_from.setText("发件人:" + smsPhone);
        tv_sms_content.setText("内容:" + smsContent);
        Button btn_reply = mView.findViewById(R.id.btn_reply);
        btn_reply.setOnClickListener(SmsService.this);

        wm.addView(mView, layoutParams);

        mView.setMyDispatchEventListener(myDispatchEventListener);
    }

    DispatchLinearLayout.MyDispatchEventListener myDispatchEventListener = new DispatchLinearLayout.MyDispatchEventListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                //这里因为已经调用过了addView方法了,所以就应该mview已经在对应的屏幕里面了,所以这里的getParent就会返回对应的容器,而并不是空了
                if(mView.getParent() != null) {
                    wm.removeView(mView);
                }
                return true;
            }
            return false;
        }
    };

    private void sendSms(String smsPhone, String smsContent) {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body","");
        startActivity(intent);
    }

    public static final String SYSTEM_DIALOGS_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    public class HomeWatchBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOGS_REASON_KEY);
                if(SYSTEM_DIALOGS_HOME_KEY.equals(reason)) {
                    wm.removeView(mView);
                }
            }
        }
    }
}
