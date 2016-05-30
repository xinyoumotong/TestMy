package hotP2B.WageGainTools.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hotP2B.WageGainTools.android.AppInvestHistoryNew;
import hotP2B.WageGainTools.android.AppMain;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.TabStripAdapter;
import hotP2B.WageGainTools.android.ui.widget.PagerSlidingTabStrip;

public class Welcome2Fragment extends TitleBarFragment {

    private AppMain aty;

    @BindView(id = R.id.licai_tabstrip)
    private PagerSlidingTabStrip m_licai_tabstrip;
    @BindView(id = R.id.licai_viewpager)
    private ViewPager m_licai_viewpager;

    private List<Fragment> mFragments;


    @Override
    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        aty = (AppMain) getActivity();
        View view = View.inflate(outsideAty, R.layout.frag_welcome2, null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        super.setActionBarRes(actionBarRes);
        actionBarRes.titleId = R.string.welcome2;
        actionBarRes.menuImageId = R.mipmap.icon_invest_history;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        this.mFragments = new ArrayList<Fragment>();

        LicaiFragment licaiFragment1 = new LicaiFragment();
        licaiFragment1.setType(LicaiFragment.LICAI_GOODLOAN);
        mFragments.add(licaiFragment1);
        LicaiFragment licaiFragment2 = new LicaiFragment();
        licaiFragment2.setType(LicaiFragment.LICAI_EBDOOR);
        mFragments.add(licaiFragment2);
        this.m_licai_viewpager.setAdapter(new TabStripAdapter(this.aty.getSupportFragmentManager(), this.mFragments, this.aty, R.array.licai_option));
        this.m_licai_tabstrip.setViewPager(m_licai_viewpager);

    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        this.outsideAty.finish();
    }

    @Override
    public void onMenuClick() {
        super.onMenuClick();
        this.outsideAty.showActivity(this.outsideAty, AppInvestHistoryNew.class);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            default:
                break;
        }
    }
}