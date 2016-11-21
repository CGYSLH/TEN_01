package com.mi.ten;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import bean.Critic_Item_Moive;
import bean.Critic_Item_Notes;
import bean.Critic_Item_Pictures;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;
import sqlite.MySqlitHelper;
import utils.CircleTransfrom;
import utils.MyCustomRequestT;
import utils.TENUrl;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.collect_show)
    ImageView collectShow;
    @BindView(R.id.collect_back)
    ImageButton collectBack;
    @BindView(R.id.collect_rec)
    RecyclerView collectRec;
    @BindView(R.id.ref)
    SwipeRefreshLayout ref;
    @BindView(R.id.collect_show_tv)
    TextView collectShowTv;
    private ArrayList<String> url_list = new ArrayList<>();
    private MyAdapter adapter;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        overridePendingTransition(android.R.anim.fade_in, 0);
        queue = Volley.newRequestQueue(this);

        collectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collectRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        JazzyRecyclerViewScrollListener jazzyRecyclerViewScrollListener = new JazzyRecyclerViewScrollListener();
        jazzyRecyclerViewScrollListener.setTransitionEffect(JazzyHelper.TWIRL);
        collectRec.setOnScrollListener(jazzyRecyclerViewScrollListener);
        initAdapter();
        initData();
        collectRec.setAdapter(adapter);
        ref.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ref.setRefreshing(true);
                boolean flag = url_list.containsAll(new MySqlitHelper(CollectActivity.this).getAllUrl());
                if (flag) {
                    Toast.makeText(CollectActivity.this, "没有更多的收藏了", Toast.LENGTH_LONG).show();
                } else {
                    url_list.clear();
                    url_list.addAll(new MySqlitHelper(CollectActivity.this).getAllUrl());
                }
                ref.setRefreshing(false);
            }
        });

    }

    private void initAdapter() {
        adapter = new MyAdapter();
    }

    private void initData() {
        MySqlitHelper helper = new MySqlitHelper(this);
        url_list.addAll(helper.getAllUrl());
        if (url_list.size() == 0) {
            collectShowTv.setVisibility(View.VISIBLE);
            collectShow.setVisibility(View.VISIBLE);
        } else {
            collectShowTv.setVisibility(View.GONE);
            collectShow.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemViewType(int position) {
            String url = url_list.get(position);
            if (url.contains("Critic")) {
                return 1;
            } else if (url.contains("Novel")) {
                return 2;
            } else {
                return 3;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {
                case 1:
                    holder = new MyHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.item_collect, null));
                    break;
                case 2:
                    holder = new MyNotesHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.item_collect_notes, null));
                    break;
                case 3:
                    holder = new MyHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.item_collect, null));
                    break;
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            Log.i("tmd", url_list.get(position));
            if (url_list.get(position).contains("Critic"))//电影
            {
                final MyHolder holder1 = (MyHolder) holder;
                holder1.iv.setImageResource(R.mipmap.ic_launcher);
                MyCustomRequestT<Critic_Item_Moive> request = new MyCustomRequestT<Critic_Item_Moive>(url_list.get(position), new Response.Listener<Critic_Item_Moive>() {
                    @Override
                    public void onResponse(Critic_Item_Moive response) {
                        holder1.title.setText(response.getTitle());
                        holder1.info.setText(response.getText1());
                        holder1.sort.setText("影评");
                        Picasso.with(CollectActivity.this).load(TENUrl.IMAGES_URL + response.getImage1())
                                .placeholder(R.drawable.pic_loading169)
                                .transform(new CircleTransfrom())
                                .resize(100, 100)
                                .error(R.drawable.pic_loading169).into(holder1.iv);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Critic_Item_Moive.class);
                queue.add(request);
            } else if (url_list.get(position).contains("Novel"))//美文
            {
                final MyNotesHolder holder1 = (MyNotesHolder) holder;

                MyCustomRequestT<Critic_Item_Notes> request = new MyCustomRequestT<Critic_Item_Notes>(url_list.get(position), new Response.Listener<Critic_Item_Notes>() {
                    @Override
                    public void onResponse(Critic_Item_Notes response) {
                        holder1.title.setText(response.getTitle());
                        holder1.info.setText(response.getText());
                        holder1.sort.setText("美文");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Critic_Item_Notes.class);
                queue.add(request);
            } else if (url_list.get(position).contains("Diagram"))//美图
            {
                final MyHolder holder1 = (MyHolder) holder;
                holder1.iv.setImageResource(R.mipmap.ic_launcher);
                MyCustomRequestT<Critic_Item_Pictures> request = new MyCustomRequestT<Critic_Item_Pictures>(url_list.get(position), new Response.Listener<Critic_Item_Pictures>() {
                    @Override
                    public void onResponse(Critic_Item_Pictures response) {
                        holder1.title.setText(response.getTitle());
                        holder1.info.setText(response.getText1());
                        holder1.sort.setText("美图");
                        Picasso.with(CollectActivity.this).load(TENUrl.IMAGES_URL + response.getImage1())
                                .placeholder(R.drawable.pic_loading169)
                                .transform(new CircleTransfrom())
                                .resize(100, 100)
                                .error(R.drawable.pic_loading169).into(holder1.iv);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Critic_Item_Pictures.class);
                queue.add(request);
            } else {
                Toast.makeText(CollectActivity.this, "发现不明的数据", Toast.LENGTH_SHORT).show();

            }

        }


        @Override
        public int getItemCount() {
            return url_list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv;
            TextView title;
            TextView info;
            TextView sort;

            public MyHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.collect_item_iv);
                title = (TextView) itemView.findViewById(R.id.collect_item_title);
                info = (TextView) itemView.findViewById(R.id.collect_item_info);
                sort = (TextView) itemView.findViewById(R.id.collect_item_sort);
            }
        }

        class MyNotesHolder extends RecyclerView.ViewHolder {
            TextView title;
            TextView info;
            TextView sort;

            public MyNotesHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.collect_item_title);
                info = (TextView) itemView.findViewById(R.id.collect_item_info);
                sort = (TextView) itemView.findViewById(R.id.collect_item_sort);
            }
        }
    }
}

