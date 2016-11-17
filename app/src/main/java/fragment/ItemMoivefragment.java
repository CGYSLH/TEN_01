package fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mi.ten.R;

import bean.Critic_Item_Moive;
import bean.TENDate;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;
import utils.MyCustomRequestT;
import utils.MyGetDate;
import utils.TENUrl;

/**
 * Created by 暗示语 on 2016/11/15.
 */

public class ItemMoivefragment extends Fragment {

    @BindView(R.id.item_moive_iv)
    ImageView itemMoiveIv;
    @BindView(R.id.item_moive_title)
    TextView itemMoiveTitle;
    @BindView(R.id.item_moive_author)
    TextView itemMoiveAuthor;
    @BindView(R.id.item_moive_text1)
    TextView itemMoiveText1;
    @BindView(R.id.item_moive_image2)
    ImageView itemMoiveImage2;
    @BindView(R.id.item_moive_text2)
    TextView itemMoiveText2;
    @BindView(R.id.item_moive_realtitle)
    TextView itemMoiveRealtitle;
    @BindView(R.id.item_moive_image3)
    ImageView itemMoiveImage3;
    @BindView(R.id.item_moive_text3_4_5)
    TextView itemMoiveText345;
    @BindView(R.id.item_moive_image4)
    ImageView itemMoiveImage4;
    @BindView(R.id.item_moive_image5)
    ImageView itemMoiveImage5;
    @BindView(R.id.sw)
    ScrollView sw;
    private RequestQueue queue;
    private String url;
    private UrlCallBack callBack;

    public interface UrlCallBack
    {
        void getUrl(String url);
    }

    @Override
    public void onAttach(Activity context) {
        Log.i("tmd","我是item的onAttach的方法");
        super.onAttach(context);
        callBack= (UrlCallBack)context ;
    }

    public ItemMoivefragment getInstance(String url) {
        ItemMoivefragment itemMoivefragment = new ItemMoivefragment();

        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        itemMoivefragment.setArguments(bundle);

        return itemMoivefragment;
    }

    //设置通过网址对数据进行填充
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_item_moive, container, false);
        ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getContext());
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        url = getArguments().getString("url");
        if (callBack != null) {
            callBack.getUrl(url);
        }
        initData();


    }

    private void initData() {
        MyCustomRequestT<Critic_Item_Moive> request = new MyCustomRequestT<Critic_Item_Moive>(url, new Response.Listener<Critic_Item_Moive>() {
            @Override
            public void onResponse(Critic_Item_Moive response) {

                itemMoiveText1.setText(response.getText1());
                itemMoiveTitle.setText(response.getTitle());
                itemMoiveAuthor.setText("作者:" + response.getAuthor() + " | 阅读量:" + response.getTimes());
                itemMoiveText2.setText(response.getText2());
                itemMoiveRealtitle.setText(response.getRealtitle());
                itemMoiveText345.setText(response.getText3() + "/n" + response.getText4() + "/n" + response.getText5());
                Log.i("tmd",response.getImage1()+"  "+response.getImage2()+"  "+response.getImage3()+"  "+response.getImage4()+"  "+response.getImageforplay());
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImageforplay())
                .placeholder(R.drawable.pic_loading169)
                .error(R.drawable.pic_loading169).into(itemMoiveIv);
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImage1())
                        .placeholder(R.drawable.pic_loading169)
                        .error(R.drawable.pic_loading169).into(itemMoiveImage2);
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImage2())
                        .placeholder(R.drawable.pic_loading169)
                        .error(R.drawable.pic_loading169).into(itemMoiveImage3);
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImage3())
                        .placeholder(R.drawable.pic_loading169)
                        .error(R.drawable.pic_loading169).into(itemMoiveImage4);
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImage4())
                        .placeholder(R.drawable.pic_loading169)
                        .error(R.drawable.pic_loading169).into(itemMoiveImage5);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, Critic_Item_Moive.class);
        queue.add(request);
    }

}
