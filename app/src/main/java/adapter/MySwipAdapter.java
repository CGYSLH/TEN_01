package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mi.ten.CollectActivity;
import com.mi.ten.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;

import bean.Critic_Item_Moive;
import bean.Critic_Item_Notes;
import bean.Critic_Item_Pictures;
import it.sephiroth.android.library.picasso.Picasso;
import linterface.OnItemClickListener;
import utils.CircleTransfrom;
import utils.MyCustomRequestT;
import utils.TENUrl;

/**
 * Created by 暗示语 on 2016/11/21.
 */

public class MySwipAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder>{
    private ArrayList<String> url_list;
    private OnItemClickListener mOnItemClickListener;
    private RequestQueue queue;
    private Context context;
public MySwipAdapter(ArrayList<String> url_list, RequestQueue queue, Context context)
{
    this.url_list=url_list;
    this.queue=queue;
    this.context=context;
}

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

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
       View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, null);
                break;
            case 2:
                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_notes, null);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, null);
                break;
        }

       return view;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 1:
                holder = new MyHolder(realContentView);
                break;
            case 2:
                holder = new MyNotesHolder(realContentView);
                break;
            case 3:
                holder = new MyHolder(realContentView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
                    Picasso.with(context).load(TENUrl.IMAGES_URL + response.getImage1())
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
                    Picasso.with(context).load(TENUrl.IMAGES_URL + response.getImage1())
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
            Toast.makeText(context, "发现不明的数据", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public int getItemCount() {
        return url_list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener//适用于电影和美图
    {
        ImageView iv;
        TextView title;
        TextView info;
        TextView sort;
        OnItemClickListener onItemClickListener;
        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.collect_item_iv);
            title = (TextView) itemView.findViewById(R.id.collect_item_title);
            info = (TextView) itemView.findViewById(R.id.collect_item_info);
            sort = (TextView) itemView.findViewById(R.id.collect_item_sort);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
    class MyNotesHolder extends RecyclerView.ViewHolder implements View.OnClickListener//适用于美文
    {
        TextView title;
        TextView info;
        TextView sort;
        OnItemClickListener onItemClickListener;
        public MyNotesHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.collect_item_title);
            info = (TextView) itemView.findViewById(R.id.collect_item_info);
            sort = (TextView) itemView.findViewById(R.id.collect_item_sort);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
