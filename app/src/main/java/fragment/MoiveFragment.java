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
import com.mi.ten.DialogActivityShare;
import com.mi.ten.R;

import java.util.ArrayList;

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

public class MoiveFragment extends Fragment {
    @BindView(R.id.tb_fr_01)
    Toolbar tbFr01;
    @BindView(R.id.pager_fr_01)
    ViewPager pagerFr01;
    @BindView(R.id.item_01_week)//星期几
    ImageView item01Week;
    @BindView(R.id.item_01_data_e)//日期的后面的字符
    ImageView item01DataE;
    @BindView(R.id.item_01_data_f)//日期前面的字符
    ImageView item01DataF;
    @BindView(R.id.line_01)
    ImageView line01;
    @BindView(R.id.item_01_month)//月份
    ImageView item01Month;
    private ArrayList<ItemMoivefragment> itemMoivefragments = new ArrayList<>();
    private MyAdapter adapter;
    private RequestQueue quue;
    private ArrayList<CriticBean.ResultBean> list_cri = new ArrayList<>();
    private MyDialogCustom dialog;
    private Dialog mydialog;
    private MoiveCallBack moiveCallBack;
    public interface MoiveCallBack
    {
        void getMoiveUrl(String url);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        moiveCallBack= (MoiveCallBack) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_01, container, false);
        ButterKnife.bind(this, view);
        dialog = new MyDialogCustom();
        mydialog = dialog.getDialog(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tbFr01.setLogo(R.drawable.logo_critic);
        quue = Volley.newRequestQueue(getContext());
        initData();

        initAdapter();

        pagerFr01.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String url=TENUrl.MOIVE_CONTENT + "?id=" + list_cri.get(position).getId();

                if (moiveCallBack != null) {//将网址给Activity传递过去
                    moiveCallBack.getMoiveUrl(url);
                }
                Log.i("tmd","我是测试的"+url);
                TENDate tenDate=MyGetDate.getTenDate(System.currentTimeMillis() - 24 * 3600 * 1000 * position);
                item01Week.setImageResource(tenDate.getWeek());
                item01DataF.setImageResource(tenDate.getDate_F());
                item01DataE.setImageResource(tenDate.getDate_E());
                item01Month.setImageResource(tenDate.getMonth());
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

    private void initData() {
        mydialog.show();
        MyCustomRequestT<CriticBean> request = new MyCustomRequestT<CriticBean>(TENUrl.MOIVE_URL, new Response.Listener<CriticBean>() {
            @Override
            public void onResponse(CriticBean response) {
                mydialog.dismiss();
                list_cri.addAll(response.getResult());

                initFragment();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, CriticBean.class);
        quue.add(request);
    }

    private void initAdapter() {
        adapter = new MyAdapter(getChildFragmentManager());
        pagerFr01.setAdapter(adapter);
    }

    private void initFragment() {
        itemMoivefragments.clear();
        for (int i = 0; i < list_cri.size(); i++) {
            itemMoivefragments.add(new ItemMoivefragment().getInstance(TENUrl.MOIVE_CONTENT + "?id=" + list_cri.get(i).getId()));
            Log.i("tmd", TENUrl.MOIVE_CONTENT + "?id=" + list_cri.get(i).getId());
        }
        adapter.notifyDataSetChanged();
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return itemMoivefragments.get(position);
        }

        @Override
        public int getCount() {
            return itemMoivefragments.size();
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.UP, enter, 500);
    }
}
