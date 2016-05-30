package hotP2B.WageGainTools.android.ui.fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.HelpItem;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.adapter.HelpAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends TitleBarFragment {

    private ListView listView;
    private HelpAdapter helpAdapter;
    private List<String> helpTypes;

    public HelpFragment() {
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(this.outsideAty, R.layout.fragment_help, null);
        listView = ((ListView) view.findViewById(R.id.item_my_help_lv));
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.titleId = R.string.my_item_help;
        actionBarRes.backImageId = R.mipmap.titlebar_back;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        listView.setClickable(true);
        helpTypes = getListString();

        if (helpAdapter == null) {
            helpAdapter = new HelpAdapter(listView, helpTypes);
        } else {
            helpAdapter.refresh(helpTypes);
        }

        listView.setAdapter(helpAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putString("title",helpTypes.get(position));

                AppSimpleBack.postShowWith(outsideAty, SimpleBackPage.HELPANSWER, bundle, true);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        this.outsideAty.finish();
    }

    public List<String> getListString() {
        List<String> strings = new ArrayList<>();
        strings.add("什么是托管");
        strings.add("什么是汇付天下");
        strings.add("登陆注册常见问题");
        strings.add("找回密码常见问题");
        strings.add("绑定银行卡常见问题");
        strings.add("提现常见问题");
        return strings;
    }

}
