package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mi.ten.R;

import bean.Critic_Item_Notes;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.MyCustomRequestT;

/**
 * Created by 暗示语 on 2016/11/15.
 */

public class ItemNotesFragment extends Fragment {

    @BindView(R.id.item_notes_title)
    TextView itemNotesTitle;
    @BindView(R.id.item_notes_author)
    TextView itemNotesAuthor;
    @BindView(R.id.item_notes_summary)
    TextView itemNotesSummary;
    @BindView(R.id.item_notes_text)
    TextView itemNotesText;
    @BindView(R.id.item_notes_author_big)
    TextView itemNotesAuthorBig;
    @BindView(R.id.item_notes_authorbrief)
    TextView itemNotesAuthorbrief;
    private RequestQueue queue;
    private String url;

    public ItemNotesFragment getInstance(String url) {
        ItemNotesFragment itemNotesFragment = new ItemNotesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        itemNotesFragment.setArguments(bundle);
        return itemNotesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_item_notes, container, false);
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
        MyCustomRequestT<Critic_Item_Notes> requestT=new MyCustomRequestT<Critic_Item_Notes>(url, new Response.Listener<Critic_Item_Notes>() {
            @Override
            public void onResponse(Critic_Item_Notes response) {
                itemNotesTitle.setText(response.getTitle());
                itemNotesAuthor.setText("作者:"+response.getAuthor()+" | 阅读量:"+response.getTimes());
                itemNotesSummary.setText(response.getSummary());
                itemNotesText.setText(response.getText());
                itemNotesAuthorBig.setText(response.getAuthor());
                itemNotesAuthorbrief.setText(response.getAuthorbrief());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },Critic_Item_Notes.class);
        queue.add(requestT);
    }
}
