package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mi.ten.R;

import bean.Critic_Item_Moive;
import bean.Critic_Item_Pictures;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;
import utils.MyCustomRequestT;
import utils.TENUrl;

/**
 * Created by 暗示语 on 2016/11/16.
 */

public class ItemPicturesFragment extends Fragment {
    @BindView(R.id.item_pictures_images)
    ImageView itemPicturesImages;
    @BindView(R.id.item_pictures_titles)
    TextView itemPicturesTitles;
    @BindView(R.id.item_pictures_authorbrief)
    TextView itemPicturesAuthorbrief;
    @BindView(R.id.item_pictures_text1)
    TextView itemPicturesText1;
    private String url;
    private RequestQueue queue;

    public ItemPicturesFragment getInstance(String url) {
        ItemPicturesFragment itemPicturesFragment = new ItemPicturesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        itemPicturesFragment.setArguments(bundle);
        return itemPicturesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_item_pictures, container, false);
        ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        url = this.getArguments().getString("url");
        initData();
    }

    private void initData() {
        MyCustomRequestT<Critic_Item_Pictures> request=new MyCustomRequestT<Critic_Item_Pictures>(url, new Response.Listener<Critic_Item_Pictures>() {
            @Override
            public void onResponse(Critic_Item_Pictures response) {
                itemPicturesTitles.setText(response.getTitle());
                itemPicturesAuthorbrief.setText(response.getAuthorbrief());
                itemPicturesText1.setText(response.getText1()+"  "+response.getText2());
                Picasso.with(getContext()).load(TENUrl.IMAGES_URL+response.getImage1())
                        .placeholder(R.drawable.pic_loading169)
                        .error(R.drawable.pic_loading169)
                        .into(itemPicturesImages);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },Critic_Item_Pictures.class);
        queue.add(request);
    }

}
