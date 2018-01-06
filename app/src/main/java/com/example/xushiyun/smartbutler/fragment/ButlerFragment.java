package com.example.xushiyun.smartbutler.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.adapter.ChatListAdapter;
import com.example.xushiyun.smartbutler.entity.ChatListData;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.ShareUtils;
import com.example.xushiyun.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButlerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButlerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */

/**
 * 进行智能交流界面的开发工作
 * 这里因为是已经在相应的博客上已经写好了相关描述的原因,这里就不再这里详细讲述对应的内容了
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {
    //进行相关组件的初始化工作
    private ListView lv_chat;
//    private Button btn_left;
//    private Button btn_right;

    private List<ChatListData> chatListDataList;
    private ChatListAdapter chatListAdapter;

    private EditText et_send;
    private Button btn_send;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_butler, container, false);
        findView(view);

        return view;
    }

    private void findView(View view) {
        lv_chat = view.findViewById(R.id.lv_chat);
//        btn_left = view.findViewById(R.id.btn_left);
//        btn_right = view.findViewById(R.id.btn_right);

//        btn_left.setOnClickListener(this);
//        btn_right.setOnClickListener(this);

        et_send = view.findViewById(R.id.et_send);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        chatListDataList = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(getActivity(), chatListDataList);
        lv_chat.setAdapter(chatListAdapter);
        //最开始的初始化发送信息
        sendLeftMessage("Ciruy Sama,Maid is right here for you~");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_left:
//                sendLeftMessage("左边");
//                break;
//            case R.id.btn_right:
//                sendRightMessage("右边");
//                break;
            case R.id.btn_send:
                checkAndSendMessageToTuring();
                break;
        }
    }

    private void checkAndSendMessageToTuring() {
        //进行相应操作,除了第一句之外,用户和机器人的对答都是成对出现的
        //这里还是老规矩,需要首先检验输入框是否为空
        String message = et_send.getText().toString().trim();
        if(!TextUtils.isEmpty(message)) {
            sendRightMessage(message);
            //如果输入框不为空的情况下,进行相应数据的发送工作
            HttpParams params = new HttpParams();
            params.put("key", StaticClass.TURING_KEY);
            params.put("info", message);
            params.put("userid", BmobUser.getCurrentUser().getUsername());

            RxVolley.post(StaticClass.TURING_URL, params, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.i("请求到的数据:" + t);
                    try {
                        JSONObject jsonObject = new JSONObject(t);
                        String text = jsonObject.getString("text");
                        L.i(text);
                        sendLeftMessage(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            //然后再发送完请求之后进行发送栏的清空操作
            et_send.setText("");
        }
    }

    private void sendRightMessage(String str) {
        ChatListData chatListData = new ChatListData();
        chatListData.setType(true);
        chatListData.setData(str);
        chatListDataList.add(chatListData);
        chatListAdapter.notifyDataSetChanged();
        lv_chat.setSelection(lv_chat.getBottom());
    }

    private void sendLeftMessage(String str) {
        ChatListData chatListData = new ChatListData();
        chatListData.setType(false);
        chatListData.setData(str);
        chatListDataList.add(chatListData);
        chatListAdapter.notifyDataSetChanged();
        lv_chat.setSelection(lv_chat.getBottom());
        Boolean speakopen = ShareUtils.getBoolean(getActivity(), "speakopen", false);
        if(speakopen == true) {
            speak(str);
        }
    }

    private void speak(String str) {
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD );
        mTts.setParameter( SpeechConstant.ENGINE_MODE, SpeechConstant.MODE_MSC );

        //默认值为小燕,这里我感觉用默认值就行了,所以直接注释掉= =
//        mTts.setParameter( SpeechConstant.VOICE_NAME, voiceName );

        //这里完全没有必要使用final,参考原文档
//        final String strTextToSpeech = "科大讯飞，让世界聆听我们的声音";
        mTts.startSpeaking(str, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }
}
