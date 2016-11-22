package fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.mi.ten.CollectActivity;
import com.mi.ten.CollectActivity_Swip;
import com.mi.ten.QQUser;
import com.mi.ten.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import it.sephiroth.android.library.picasso.Picasso;
import utils.CircleTransfrom;

/**
 * Created by 暗示语 on 2016/11/15.
 */

public class UsersFragment extends Fragment {
    @BindView(R.id.tb_fr_04)
    Toolbar tbFr04;
    @BindView(R.id.iv_but)
    ImageButton ivBut;
    @BindView(R.id.user_lv)
    ListView userLv;
    @BindView(R.id.user_name)
    TextView userName;

    private int[] images = {R.drawable.setting_favorite, R.drawable.setting_font,
            R.drawable.setting_aboutus, R.drawable.setting_feedback, R.drawable.setting_nightmodel};
    private String[] user_text = {"1", "2", "3", "4", "5"};//测试是不是数据的问题一直得不到数据下面

    //    private String[] user_text=getResources().getStringArray(R.array.user);//得不到数据
    private MyAdapter adapter;
    private String json = "";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setUser();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_04, container, false);
        ButterKnife.bind(this, view);
       SharedPreferences sharedPreferences= getContext().getSharedPreferences("info",Context.MODE_PRIVATE);
       String name= sharedPreferences.getString("name","");
        String icon= sharedPreferences.getString("icon","");
        if (!name.equals("") && !icon.equals("")) {

            userName.setText(name);
            Picasso.with(getContext()).load(icon)
                    .transform(new CircleTransfrom())
                    .resize(100,100)
                    .into(ivBut);

        }
        user_text = getContext().getResources().getStringArray(R.array.user);//通过此方法证明要在fragment中获取资源文件的数据需要这样获取
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        userLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //TastyToast.makeText(getContext(), "正在开发", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    startActivity(new Intent(getContext(), CollectActivity_Swip.class));

                } else {
                    TastyToast.makeText(getContext(), "SORRY不会呀", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                }
            }
        });

    }

    private void initAdapter() {
        adapter = new MyAdapter();
        userLv.setAdapter(adapter);
    }


    @OnClick(R.id.iv_but)
    void login() {

        ShareSDK.initSDK(getContext());
        // 第三方登录的方式一
        Platform weiboq = ShareSDK.getPlatform(QQ.NAME);
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weiboq.setPlatformActionListener(new PlatformActionListener() {


            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("tmd", platform.getDb().exportData());
                json = platform.getDb().exportData();

                handler.sendEmptyMessage(1);



            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        weiboq.authorize();


    }

    private void setUser() {

        Gson gson = new Gson();
        QQUser qquser = gson.fromJson(json, QQUser.class);
        Log.i("tmd", "网址" + qquser.getIcon() + "用户名" + qquser.getNickname());
      SharedPreferences sharedPreferences= getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("name",qquser.getNickname());
        editor.putString("icon",qquser.getIcon());
        editor.commit();
//        user_text[0]=qquser.getNickname(); 通过这两句话得到的是 已经改变了但是控件没有刷新
//        adapter.notifyDataSetChanged();

        userName.setText(qquser.getNickname());
        Picasso.with(getContext()).load(qquser.getIcon())
                .transform(new CircleTransfrom())
                .resize(100,100)
                .into(ivBut);


    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 500);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("tmd","onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("tmd","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("tmd","onResume");


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("tmd","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("tmd","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tmd","onDestroy");
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_lv_item, parent, false);
                holder = new MyViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.item_user_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.item_user_tv);
                convertView.setTag(holder);

            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            holder.iv.setImageResource(images[position]);
            holder.tv.setText(user_text[position]);
            return convertView;
        }

        class MyViewHolder {
            ImageView iv;
            TextView tv;
        }

    }
}
