package com.mi.ten;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mob.commons.SHARESDK;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
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

        setContentView(R.layout.activity_dialog_share_01);
        ButterKnife.bind(this);
        url=getIntent().getStringExtra("saveurl");
        helper = new MySqlitHelper(this);
        but0.setOnClickListener(this);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
      Animation animation= AnimationUtils.loadAnimation(this,R.anim.in);
        animation.setStartOffset(300);
         but2.startAnimation(animation);
        but0.startAnimation(animation);
        but3.startAnimation(animation);
        but5.startAnimation(animation);
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

            case R.id.but2:
                shareQQWeibo();
                break;
            case R.id.but3:
                shareWeibo();
                break;
            case R.id.but4:
               shareQQ();
                break;
            case R.id.but5:
                shareQQzone();
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

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void finish() {
        super.finish();


        overridePendingTransition(R.anim.slide_in_from_top,R.anim.slide_out_to_top);

    }
    private void shareWechat()
    {
        ShareSDK.initSDK(this);
        Toast.makeText(this,"hhh",Toast.LENGTH_LONG).show();
        Wechat.ShareParams sp=new Wechat.ShareParams();
        sp.setImageUrl("http://www.baidu.com");
        Platform wechat= ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(DialogActivityShare.this,"分享成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(DialogActivityShare.this,"分享失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(DialogActivityShare.this,"取消分享",Toast.LENGTH_LONG).show();
            }
        });
        wechat.share(sp);
    }

    private void shareQQWeibo()
    {
        ShareSDK.initSDK(this);

        TencentWeibo.ShareParams sp = new TencentWeibo.ShareParams();
        sp.setText("测试分享的文本");


        Platform weibo = ShareSDK.getPlatform(TencentWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            //分享成功后回到的方法
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
              ;
            }

            //分享失败后回调的方法
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            //取消分享后回调的方法
            @Override
            public void onCancel(Platform platform, int i) {

            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);
    }
    private void shareQQ()
    {
        ShareSDK.initSDK(this);
        QQ.ShareParams sp=new QQ.ShareParams();
        sp.setText("测试分享的文本");
        Platform qq=ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qq.share(sp);

    }
    private void shareWeibo()
    {
        ShareSDK.initSDK(this);
        SinaWeibo.ShareParams sp=new SinaWeibo.ShareParams();
        sp.setText("测试分享的文本");
        Platform sina=ShareSDK.getPlatform(SinaWeibo.NAME);
        sina.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        sina.share(sp);
    }
    private void shareQQzone()
    {
        Toast.makeText(DialogActivityShare.this,"shiab",Toast.LENGTH_LONG).show();
        ShareSDK.initSDK(this);
        QZone.ShareParams sp=new QZone.ShareParams();
        sp.setText("测试分享的文本");//后续完善
        Platform qzone=ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qzone.share(sp);
    }
}
