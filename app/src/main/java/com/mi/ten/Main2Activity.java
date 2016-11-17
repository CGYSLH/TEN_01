package com.mi.ten;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.MyDialogCustom;
/**
 * 测试的
 * */
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.but) void test()
    {
        MyDialogCustom dialog=new MyDialogCustom();
        dialog.getDialog(this).show();
    }
}
