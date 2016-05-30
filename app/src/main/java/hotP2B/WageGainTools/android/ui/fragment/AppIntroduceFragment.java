package hotP2B.WageGainTools.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hotP2B.WageGainTools.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppIntroduceFragment extends TitleBarFragment {


    public AppIntroduceFragment() {
        // Required empty public constructor
    }


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(this.outsideAty,R.layout.fragment_app_introduce,null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        super.setActionBarRes(actionBarRes);
        actionBarRes.title = "良薪宝介绍";
        actionBarRes.backImageId=R.mipmap.titlebar_back;
    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        this.outsideAty.finish();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
