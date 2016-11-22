package com.mi.ten;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.ItemMoivefragment;
import fragment.MoiveFragment;
import fragment.NotesFragment;
import fragment.PicturesFragment;
import fragment.UsersFragment;
import me.shaohui.bottomdialog.BottomDialog;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, ItemMoivefragment.UrlCallBack, MoiveFragment.MoiveCallBack,
        NotesFragment.NotesCallBack, PicturesFragment.PicturesCallBack{

    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.rg)
    RadioGroup rg;
    private float x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    private ArrayList<Fragment> list_fr = new ArrayList<>();
    private FragmentManager manager;
    private Fragment lastFragment;
    private GestureDetector detector;
    private String saveurl="";
    private List<String> list=new ArrayList<>();
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        detector = new GestureDetector(this, this);
        manager = getSupportFragmentManager();
        initFragement();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.frame, list_fr.get(0));
        ft.commit();
        lastFragment = list_fr.get(0);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) findViewById(checkedId);
                int tag = Integer.parseInt(rb.getTag().toString());
                if (tag == 3) {
                    more.setVisibility(View.GONE);
                } else {
                    more.setVisibility(View.VISIBLE);
                }
                if (!list_fr.get(tag).isAdded()) {
                    manager.beginTransaction().add(R.id.frame, list_fr.get(tag)).commit();//该碎片没有被添加就添加该碎片
                } else {
                    manager.beginTransaction().show(list_fr.get(tag)).commit();//如果已经被添加就直接显示
                }
                manager.beginTransaction().hide(lastFragment).commit();//并隐藏上一个fragment
                lastFragment = list_fr.get(tag);//记录当前的碎片
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }
    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(this,"走了",Toast.LENGTH_LONG).show();
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            x1=event.getX();
//            y1=event.getY();
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            x2=event.getX();
//            y2=event.getY();
//            if (y1 - y2 > 50) {
//                rg.setVisibility(View.INVISIBLE);
//                Toast.makeText(this,"上",Toast.LENGTH_LONG).show();
//            } else if (y2 - y1 > 50) {
//                rg.setVisibility(View.VISIBLE);
//                Toast.makeText(this,"下",Toast.LENGTH_LONG).show();
//            }
//        }
//
//        return true;
//    }

    private void initFragement() {
        list_fr.add(new MoiveFragment());
        list_fr.add(new NotesFragment());
        list_fr.add(new PicturesFragment());
        list_fr.add(new UsersFragment());
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (rg.getCheckedRadioButtonId() == rg.getChildAt(0).getId() || rg.getCheckedRadioButtonId() == rg.getChildAt(1).getId()) {
            if (distanceY >= 10) {
//            Toast.makeText(this, "向上", Toast.LENGTH_LONG).show();
                if (!(rg.getVisibility() == View.GONE)) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.1f);
                    alphaAnimation.setDuration(1000);
                    rg.startAnimation(alphaAnimation);
                    rg.setVisibility(View.GONE);
                    more.setVisibility(View.GONE);
                }


            } else {
//            Toast.makeText(this, "向下", Toast.LENGTH_LONG).show();
                if (!(rg.getVisibility() == View.VISIBLE)) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                    alphaAnimation.setDuration(1500);
                    rg.startAnimation(alphaAnimation);
                    rg.setVisibility(View.VISIBLE);
                    more.setVisibility(View.VISIBLE);
                }

            }
        }


        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    public void click(View view)
    {
        Intent intent=new Intent(this,DialogActivityShare.class);
        intent.putExtra("saveurl",saveurl);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_bottom,R.anim.slide_out_to_bottom);//设置下一个Activity的进入方式

    }

    //框架dialog有下方出来
   private void  getDialogDown()
    {
        BottomDialog dialog=  BottomDialog.create(getSupportFragmentManager())
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {

                    }
                })
                .setLayoutRes(R.layout.menudialog)
                .setDimAmount(0.1f)
                .setCancelOutside(true)
                .setDimAmount(0.5f);

        WindowManager manager= getWindowManager();
        Display display= manager.getDefaultDisplay();

        dialog.setHeight(display.getHeight());
        dialog .show();
    }
    //自定义没有框架没有什么效果
    private void getCustomDialog()
    {
        //       View view1= LayoutInflater.from(this).inflate(R.layout.menudialog,null);
//
//        final AlertDialog dialog=new AlertDialog.Builder(this,R.style.lol)
//                .setView(view1).show();
//
////       WindowManager.LayoutParams lp= dialog.getWindow().getAttributes();
////        lp.x=0;
////        lp.y= 200;
////        dialog.onWindowAttributesChanged(lp);
//      ImageButton igb= (ImageButton) view1.findViewById(R.id.but0);
//        igb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"添加不了",Toast.LENGTH_LONG).show();
//                dialog.dismiss();
//            }
//        });

    }

    @Override
    public void getUrl(String url) {//获取fragment中的fragment的数据
       // Log.i("tmd","得到了fragment传递过来的数据:"+url);
       // saveurl=url;
       // Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMoiveUrl(String url) {
        saveurl=url;
    }

    @Override
    public void getNotesUrl(String url) {
        saveurl=url;
    }

    @Override
    public void getPicturesUrl(String url) {
        saveurl=url;
    }

    @Override
    public void onBackPressed() {
        ExitApp();
    }
    public void ExitApp()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else
        {
            this.finish();
        }
    }
}
