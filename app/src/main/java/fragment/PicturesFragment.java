package fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.mi.ten.R;

import java.util.ArrayList;
import java.util.List;

import bean.CriticBean;
import bean.TENDate;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.MyCustomRequestT;
import utils.MyDialogCustom;
import utils.MyGetDate;
import utils.TENUrl;

/**
 * Created by 暗示语 on 2016/11/15.
 */

public class PicturesFragment extends Fragment {


    @BindView(R.id.tb_fr_03)
    Toolbar tbFr03;
    @BindView(R.id.pager_fr_03)
    ViewPager pagerFr03;
    @BindView(R.id.item_03_week)
    ImageView item03Week;
    @BindView(R.id.item_03_data_e)
    ImageView item03DataE;
    @BindView(R.id.item_03_data_f)
    ImageView item03DataF;
    @BindView(R.id.line_03)
    ImageView line03;
    @BindView(R.id.item_03_month)
    ImageView item03Month;
    private RequestQueue queue;
    private List<CriticBean.ResultBean> list_pic = new ArrayList<>();
    private List<ItemPicturesFragment> list = new ArrayList<>();
    private MyAdapter adapter;
    private MyDialogCustom dialog;
    private Dialog mydialog;
    private PicturesCallBack picturesCallBack;

    public interface PicturesCallBack
    {
        void getPicturesUrl(String url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        picturesCallBack= (PicturesCallBack) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_03, container, false);
        ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getContext());
        dialog = new MyDialogCustom();
        mydialog = dialog.getDialog(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tbFr03.setLogo(R.drawable.logo_diagram);
        initData();
        initAdapter();
        pagerFr03.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String url=TENUrl.PICTURES_CONTENT + "?id=" + list_pic.get(position).getId();
                if (picturesCallBack != null) {
                    picturesCallBack.getPicturesUrl(url);
                }
                Log.i("tmd","我是测试的"+url);
                TENDate tenDate= MyGetDate.getTenDate(System.currentTimeMillis() - 24 * 3600 * 1000 * position);
                item03Week.setImageResource(tenDate.getWeek());
                item03DataF.setImageResource(tenDate.getDate_F());
                item03DataE.setImageResource(tenDate.getDate_E());
                item03Month.setImageResource(tenDate.getMonth());



            }

            @Override
            public void onPageSelected(int position) {


                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "亲 已经是最新内容了", Toast.LENGTH_LONG).show();
                        break;
                    case 8:
                        Toast.makeText(getContext(), "亲 只有十篇内容可以查看", Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        adapter = new MyAdapter(getChildFragmentManager());
        pagerFr03.setAdapter(adapter);
    }

    private void initData() {
        mydialog.show();
        MyCustomRequestT<CriticBean> requestT = new MyCustomRequestT<CriticBean>(TENUrl.PICTURES_URL, new Response.Listener<CriticBean>() {
            @Override
            public void onResponse(CriticBean response) {
                mydialog.dismiss();
                list_pic.addAll(response.getResult());
                initFragment();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, CriticBean.class);
        queue.add(requestT);
    }

    private void initFragment() {
        for (int i = 0; i < list_pic.size(); i++) {
            list.add(new ItemPicturesFragment().getInstance(TENUrl.PICTURES_CONTENT + "?id=" + list_pic.get(i).getId()));
            Log.i("tmd", TENUrl.PICTURES_CONTENT + "?id=" + list_pic.get(i) + "网址");
        }
        adapter.notifyDataSetChanged();
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500);
    }
}
