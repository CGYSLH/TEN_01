package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.mi.ten.CollectActivity;
import com.mi.ten.R;
import com.sdsmdg.tastytoast.ErrorToastView;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private int[] images={R.drawable.setting_favorite,R.drawable.setting_font,
            R.drawable.setting_aboutus,R.drawable.setting_feedback,R.drawable.setting_nightmodel};
    private String[] user_text={"1","2","3","4","5"};//测试是不是数据的问题一直得不到数据下面

//    private String[] user_text=getResources().getStringArray(R.array.user);//得不到数据
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_04, container, false);
        ButterKnife.bind(this, view);
        user_text=getContext().getResources().getStringArray(R.array.user);//通过此方法证明要在fragment中获取资源文件的数据需要这样获取
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
                    startActivity(new Intent(getContext(), CollectActivity.class));
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
        Toast.makeText(getContext(), "还没有实现哦亲 请等待后续开发", Toast.LENGTH_LONG).show();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.DOWN, enter, 500);
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
                holder= (MyViewHolder) convertView.getTag();
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
