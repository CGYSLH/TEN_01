package com.mi.ten;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.ItemMoivefragment;
import sqlite.MySqlitHelper;

public class DialogActivityShare extends Activity implements View.OnClickListener{

    @BindView(R.id.but0)
    ImageButton but0;
    @BindView(R.id.but1)
    ImageButton but1;
    @BindView(R.id.but2)
    ImageButton but2;
    @BindView(R.id.but3)
    ImageButton but3;
    @BindView(R.id.but4)
    ImageButton but4;
    @BindView(R.id.but5)
    ImageButton but5;
    private String url="";
    private MySqlitHelper helper;
    private String FromUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_share);
        ButterKnife.bind(this);
        url=getIntent().getStringExtra("saveurl");
        helper = new MySqlitHelper(this);
        but0.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but0:
//                Toast.makeText(this,"得到网址"+url,Toast.LENGTH_LONG).show();
                helper.getReadableDatabase();
                 WhereFromUrl(url);
                if (helper.getAllUrl().contains(url)) {
                    Toast.makeText(this,"已经收藏过了",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    helper.addUrl(url);
                    Toast.makeText(this,"收藏成功"+FromUrl,Toast.LENGTH_LONG).show();
                    finish();
                }



//                new ItemMoivefragment.UrlCallBack() {
//                    @Override
//                    public void getUrl(String url) {
//                        Toast.makeText(DialogActivityShare.this,url,Toast.LENGTH_LONG).show();
//                    }
//                };
                break;
        }
    }
        private void WhereFromUrl(String url)
        {

           if (url.contains("Critic"))
           {
               //电影
               FromUrl="电影";


           }else if (url.contains("Novel"))
           {
               //美文
               FromUrl="美文";
           } else if (url.contains("Diagram")) {
               //图片
               FromUrl="图片";
           } else {
               //没有
               FromUrl="未知";
           }

        }

}
