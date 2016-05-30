package hotP2B.WageGainTools.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RadioButton;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.adapter.InvestHistory1Adapter;
import hotP2B.WageGainTools.android.adapter.InvestHistory2Adapter;
import hotP2B.WageGainTools.android.adapter.InvestHistory3Adapter;
import hotP2B.WageGainTools.android.adapter.TabStripAdapter;
import hotP2B.WageGainTools.android.bean.InvestHistory1;
import hotP2B.WageGainTools.android.bean.InvestHistory1.InvestHistory1Item;
import hotP2B.WageGainTools.android.bean.InvestHistory2;
import hotP2B.WageGainTools.android.bean.InvestHistory2.InvestHistory2Item;
import hotP2B.WageGainTools.android.bean.InvestHistory3;
import hotP2B.WageGainTools.android.bean.InvestHistory3.InvestHistory3Item;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.fragment.InvestHistoryBackFragment;
import hotP2B.WageGainTools.android.ui.fragment.InvestHistoryBidFragment;
import hotP2B.WageGainTools.android.ui.fragment.InvestHistoryWithdrawFragment;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.ui.widget.PagerSlidingTabStrip;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase.OnRefreshListener;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshList;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppInvestHistoryNew extends AppTitleBar {

    @BindView(id = R.id.invest_history_tabstrip)
    private PagerSlidingTabStrip invest_history_tabstrip;

    @BindView(id = R.id.invest_history_viewpager)
    private ViewPager invest_history_viewpager;

    private List<Fragment> mFragments;


    @Override
    public void setRootView() {
        this.setContentView(R.layout.aty_invest_history_new);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initTitleBar() {
        this.mImgBack.setVisibility(View.VISIBLE);
        this.mTvTitle.setText("投资记录");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        this.mFragments = new ArrayList<Fragment>();

        mFragments.add(new InvestHistoryBidFragment());
        mFragments.add(new InvestHistoryBackFragment());
        mFragments.add(new InvestHistoryWithdrawFragment());

        this.invest_history_viewpager.setAdapter(new TabStripAdapter(getSupportFragmentManager(), mFragments, getApplicationContext(), R.array.invest_history));
        this.invest_history_tabstrip.setViewPager(invest_history_viewpager);
    }


    @Override
    public void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
    }
}