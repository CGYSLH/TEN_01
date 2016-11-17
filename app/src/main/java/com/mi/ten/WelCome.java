package com.mi.ten;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelCome extends AppCompatActivity {
    private ImageView imageView;
    private Animation an;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wel_come);
        imageView = (ImageView) findViewById(R.id.imgView);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    an = AnimationUtils.loadAnimation(WelCome.this, R.anim.set_animaition);
                    //开启动画
                    an.setFillAfter(true);
                    imageView.startAnimation(an);
                    Thread.sleep(4000);
                    Intent intent =new Intent();
                    intent.setClass(WelCome.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
