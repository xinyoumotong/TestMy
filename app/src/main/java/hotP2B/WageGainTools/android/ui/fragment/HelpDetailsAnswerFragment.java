package hotP2B.WageGainTools.android.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.kymjs.kjframe.ui.BindView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.HelpItem;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.HelpDetailsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDetailsAnswerFragment extends TitleBarFragment implements HelpDetailsAdapter.HelpDetailItemClickListener {

    @BindView(id = R.id.item_help_tv_answer_listview)
    private ListView item_help_tv_answer_listview;
    private List<HelpItem> helpItems;
    private HelpDetailsAdapter helpDetailsAdapter;
    private String detailTtitle;

    public HelpDetailsAnswerFragment() {
    }
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(this.outsideAty,R.layout.fragment_help_details_answer,null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        super.setActionBarRes(actionBarRes);
        if (detailTtitle != null) {
            actionBarRes.title = detailTtitle;
        } else {
            actionBarRes.titleId = R.string.my_item_help;
        }
        actionBarRes.backImageId = R.mipmap.titlebar_back;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        helpItems = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Bundle sba_data_key = (Bundle) extras.getBundle("sba_data_key");
                if (sba_data_key != null) {
                    int position = sba_data_key.getInt("position");
                    detailTtitle = sba_data_key.getString("title");
                   helpItems = HelpItem.getListByType(position);
                }
            }
        }

        helpDetailsAdapter = new HelpDetailsAdapter(outsideAty, helpItems);
        helpDetailsAdapter.setHelpDetailItemClickListener(this);
        item_help_tv_answer_listview.setAdapter(helpDetailsAdapter);
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        this.outsideAty.finish();
    }

    @Override
    public void helpDetailItemClick(View parent, View view, int position) {
        LinearLayout viewById = (LinearLayout) parent.findViewById(R.id.item_help_type_answer_ll);
        ImageView item_help_type_guid_iv = (ImageView) parent.findViewById(R.id.item_help_type_guid_iv);
                if (viewById.getVisibility() == View.VISIBLE) {
                    viewById.setVisibility(View.GONE);
                    item_help_type_guid_iv.setImageResource(R.mipmap.helpgo_2px);
                } else {
                    viewById.setVisibility(View.VISIBLE);
                    item_help_type_guid_iv.setImageResource(R.mipmap.helpback_2px);
                }
    }
}
