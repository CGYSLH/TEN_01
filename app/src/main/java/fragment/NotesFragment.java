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

public class NotesFragment extends Fragment {


    @BindView(R.id.tb_fr_02)
    Toolbar tbFr02;
    @BindView(R.id.pager_fr_02)
    ViewPager pagerFr02;
    @BindView(R.id.item_02_week)
    ImageView item02Week;
    @BindView(R.id.item_02_data_e)
    ImageView item02DataE;
    @BindView(R.id.item_02_data_f)
    ImageView item02DataF;
    @BindView(R.id.line_02)
    ImageView line02;
    @BindView(R.id.item_02_month)
    ImageView item02Month;
    private RequestQueue queue;
    private List<CriticBean.ResultBean> list_notes = new ArrayList<>();
    private List<ItemNotesFragment> list = new ArrayList<>();
    private MyAdapter adapter;
    private MyDialogCustom dialog;
    private Dialog mydialog;
    private NotesCallBack notesCallBack;
    public interface NotesCallBack
    {
        void getNotesUrl(String url);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        notesCallBack= (NotesCallBack) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_02, container, false);
        ButterKnife.bind(this, view);
        dialog = new MyDialogCustom();
        mydialog = dialog.getDialog(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tbFr02.setLogo(R.drawable.logo_novel);
        queue = Volley.newRequestQueue(getContext());
        initData();
        initAdapter();
    }

    private void initAdapter() {
        adapter = new MyAdapter(getChildFragmentManager());
        pagerFr02.setAdapter(adapter);
        pagerFr02.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String url=TENUrl.NOTRS_CONTENT + "?id=" + list_notes.get(position).getId();
                if (notesCallBack != null) {
                    notesCallBack.getNotesUrl(url);
                }
                Log.i("tmd","我是测试的"+url);
                TENDate tenDate= MyGetDate.getTenDate(System.currentTimeMillis() - 24 * 3600 * 1000 * position);
                item02Week.setImageResource(tenDate.getWeek());
                item02DataF.setImageResource(tenDate.getDate_F());
                item02DataE.setImageResource(tenDate.getDate_E());
                item02Month.setImageResource(tenDate.getMonth());
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
        MyCustomRequestT<CriticBean> requestT = new MyCustomRequestT<CriticBean>(TENUrl.NOTRS_URL, new Response.Listener<CriticBean>() {
            @Override
            public void onResponse(CriticBean response) {
                mydialog.dismiss();
                list_notes.addAll(response.getResult());


                initFragemnt();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, CriticBean.class);
        queue.add(requestT);
    }

    private void initFragemnt() {
        for (int i = 0; i < list_notes.size(); i++) {
            Log.i("tmd", list_notes.get(i).getId() + "");
            list.add(new ItemNotesFragment().getInstance(TENUrl.NOTRS_CONTENT + "?id=" + list_notes.get(i).getId()));
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
        return CubeAnimation.create(CubeAnimation.LEFT, enter, 500);
    }
}
