package hotP2B.WageGainTools.android.ui.fragment;

import hotP2B.WageGainTools.android.AppSafe;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.bean.LicaiProduct;
import hotP2B.WageGainTools.android.bean.LicaiProduct.LicaiProductItem;
import hotP2B.WageGainTools.android.bean.response.AssetsResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.receiver.AssetReceiver;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.ErrorProgressUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UIHelper;
import hotP2B.WageGainTools.android.utils.UserUtils;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.StringUtils;

import hotP2B.WageGainTools.android.AppBalance;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppIncome;
import hotP2B.WageGainTools.android.AppMain;
import hotP2B.WageGainTools.android.AppManual;
import hotP2B.WageGainTools.android.AppNotice;
import hotP2B.WageGainTools.android.AppProductDetail;
import hotP2B.WageGainTools.android.AppRecharge;
import hotP2B.WageGainTools.android.AppSalary;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.AppWithdraw;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Welcome1Fragment extends TitleBarFragment {
    private AppMain aty;

    @BindView(id = R.id.welcome1_swipeRefreshLayout)
    private SwipeRefreshLayout m_welcome1_swipeRefreshLayout;
    @BindView(id = R.id.welcome1_empty_layout)
    private EmptyLayout m_welcome1_empty_layout;

    //公司信息
    @BindView(id = R.id.welcome1_tv_companyName)
    private TextView m_welcome1_tv_companyName;
    @BindView(id = R.id.welcome1_tv_realName)
    private TextView m_welcome1_tv_realName;

    //总资产
    @BindView(id = R.id.welcome1_rl_assets, click = true)
    private RelativeLayout m_welcome1_rl_assets;
    @BindView(id = R.id.welcome1_tv_assets)
    private TextView m_welcome1_tv_assets;

    //昨日收益
    @BindView(id = R.id.welcome1_rl_lastday_income, click = true)
    private LinearLayout m_welcome1_rl_lastday_income;
    @BindView(id = R.id.welcome1_tv_lastday_income)
    private TextView m_welcome1_tv_lastday_income;

    //当月收益
    @BindView(id = R.id.welcome1_rl_month_income, click = true)
    private LinearLayout m_welcome1_rl_month_income;
    @BindView(id = R.id.welcome1_tv_month_income)
    private TextView m_welcome1_tv_month_income;

    //银行卡
    @BindView(id = R.id.welcome1_rl_bankcard, click = true)
    private LinearLayout m_welcome1_rl_bankcard;
    @BindView(id = R.id.welcome1_tv_bankcard)
    private TextView m_welcome1_tv_bankcard;

    //余额
    @BindView(id = R.id.welcome1_rl_balance, click = true)
    private LinearLayout m_welcome1_rl_balance;
    @BindView(id = R.id.welcome1_tv_balance)
    private TextView m_welcome1_tv_balance;

    //通知
    @BindView(id = R.id.welcome1_rl_message, click = true)
    private LinearLayout m_welcome1_rl_message;
    @BindView(id = R.id.welcome1_tv_message)
    private TextView m_welcome1_tv_message;


    //列表项
    @BindView(id = R.id.welcome1_rl_hongbao, click = true)
    private LinearLayout m_welcome1_rl_hongbao;
    @BindView(id = R.id.welcome1_rl_recharge, click = true)
    private LinearLayout m_welcome1_rl_recharge;
    @BindView(id = R.id.welcome1_rl_withdraw, click = true)
    private LinearLayout m_welcome1_rl_withdraw;
    @BindView(id = R.id.welcome1_rl_salary, click = true)
    private LinearLayout m_welcome1_rl_salary;
    @BindView(id = R.id.welcome1_rl_safe, click = true)
    private LinearLayout m_welcome1_rl_safe;
    @BindView(id = R.id.welcome1_rl_manual, click = true)
    private LinearLayout m_welcome1_rl_manual;

    //新手标
    @BindView(id = R.id.welcome1_ll_newbid, click = true)
    private LinearLayout m_welcome1_ll_newbid;
    @BindView(id = R.id.welcome1_newbid_year_interest)
    private TextView m_welcome1_newbid_year_interest;
    @BindView(id = R.id.welcome1_newbid_invest_minmoney)
    private TextView m_welcome1_newbid_invest_minmoney;
    @BindView(id = R.id.welcome1_newbid_duration)
    private TextView m_welcome1_newbid_duration;

    //广播
    private AssetReceiver assetReceiver = null;
    private BalanceReceiver balanceReceiver = null;

    private LicaiProductItem newBidItem = null;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        aty = (AppMain) getActivity();
        View view = View.inflate(aty, R.layout.frag_welcome1, null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.titleId = R.string.welcome1;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        m_welcome1_empty_layout.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        m_welcome1_swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        m_welcome1_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh2();
            }
        });

        //注册广播
        assetReceiver = new AssetReceiver(this);
        IntentFilter filterAsset = new IntentFilter();
        filterAsset.addAction(AppConfig.ASSET_BROADCAST_ACTION);
        this.outsideAty.registerReceiver(assetReceiver, filterAsset);

        balanceReceiver = new BalanceReceiver();
        IntentFilter filterBalance = new IntentFilter();
        filterBalance.addAction(AppConfig.BALANCE_BROADCAST_ACTION);
        this.outsideAty.registerReceiver(balanceReceiver, filterBalance);

        refresh();// 刷新数据

    }

    @Override
    public void onResume() {
        super.onResume();
        this.m_welcome1_tv_companyName.setText(AppContext.m_CurrentAccount.getBizname());
        this.m_welcome1_tv_realName.setText(AppContext.m_CurrentAccount.getReallyname());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackClick() {

    }

    @Override
    public void onMenuClick() {
    }

    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.welcome1_rl_assets://总资产
                onAssets();
                break;
            case R.id.welcome1_rl_lastday_income://昨日收益
                onIncome(1);
                break;
            case R.id.welcome1_rl_month_income://当月收益
                onIncome(2);
                break;
            case R.id.welcome1_rl_bankcard://银行卡
                onBankCard();
                break;
            case R.id.welcome1_rl_balance://余额
            {
                this.outsideAty.showActivity(this.outsideAty, AppBalance.class);
            }
            break;
            case R.id.welcome1_rl_message://通知
            {
                this.outsideAty.showActivity(this.outsideAty, AppNotice.class);
            }
            break;
            case R.id.welcome1_rl_hongbao: //礼拜红包
                onHongbao();
                break;
            case R.id.welcome1_rl_recharge://充值
                onRecharge();
                break;
            case R.id.welcome1_rl_withdraw://提现
                onWithdraw();
                break;
            case R.id.welcome1_rl_salary: //工资条
                onSalary();
                break;
            case R.id.welcome1_rl_safe://安全
                onSafe();
                break;
            case R.id.welcome1_rl_manual://新手指南
                onManual();
                break;
            case R.id.welcome1_ll_newbid://新手标
                onNewBid();
                break;
            default:
                break;
        }
    }

    //总资产
    private void onAssets() {
        //this.outsideAty.showActivity(this.outsideAty, AppAssets.class);

    }

    //收益
    private void onIncome(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        this.outsideAty.showActivity(this.outsideAty, AppIncome.class, bundle);
    }

    //银行卡
    private void onBankCard() {
        if (AppContext.m_CurrentAccount.getReallyidentity().equals("0")) {
            gotoRealAuth();
            return;
        }
        if (!AppContext.m_CurrentAccount.getAuthentcustid().equals("1")) {
            gotoOpenAccount();
            return;
        }
        AppSimpleBack.postShowWith(this.aty, SimpleBackPage.BANKCARD);
    }


    //礼拜红包
    private void onHongbao() {
        //this.outsideAty.showActivity(this.outsideAty, AppHongbao.class);
        DialogUtils.showNotOpenAlertDialog(this.outsideAty);
    }

    //充值
    private void onRecharge() {
        if (AppContext.m_CurrentAccount.getReallyidentity().equals("0")) {
            gotoRealAuth();
            return;
        }
        if (!AppContext.m_CurrentAccount.getAuthentcustid().equals("1")) {
            gotoOpenAccount();
            return;
        }
        this.outsideAty.showActivity(this.outsideAty, AppRecharge.class);
    }

    //提现
    private void onWithdraw() {
        if (AppContext.m_CurrentAccount.getReallyidentity().equals("0")) {
            gotoRealAuth();
            return;
        }
        if (!AppContext.m_CurrentAccount.getAuthentcustid().equals("1")) {
            gotoOpenAccount();
            return;
        }

        if (AppContext.m_CurrentAccount.getBankCardCount() <= 0) {
            gotoBindBankCard();
            return;
        }

        String strBalance = this.m_welcome1_tv_balance.getText().toString().trim();
        Double balance = StringUtils.toDouble(strBalance);
        if (balance <= 0) {
            ViewInject.toast("账户余额为0,不能提现");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("balance", strBalance);
        this.outsideAty.showActivity(this.outsideAty, AppWithdraw.class, bundle);
    }

    //工资条
    private void onSalary() {
//		DialogUtils.showNotOpenAlertDialog(this.outsideAty);
        this.outsideAty.showActivity(this.outsideAty, AppSalary.class);

    }

    //安全
    private void onSafe() {
//		UIHelper.toBrowser(this.outsideAty,AppConfig.URL_ACCOUNT_SAFE,"账户安全");
        this.outsideAty.showActivity(this.outsideAty, AppSafe.class);
    }

    //新手指南
    private void onManual() {
        this.outsideAty.showActivity(this.outsideAty, AppManual.class);
    }

    //新手标
    private void onNewBid() {
        if (newBidItem != null && !StringUtils.isEmpty(newBidItem.getBidProId())) {
            Intent intent = new Intent(outsideAty, AppProductDetail.class);
            intent.putExtra("bidproid", newBidItem.getBidProId());
            intent.putExtra("url", newBidItem.getFurl());
            startActivity(intent);
        }
    }

    private void gotoRealAuth() {
        DialogUtils.showAlertDialog(this.outsideAty, R.string.prompt, "你还没有实名认证,去认证？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("userpkid", AppContext.m_CurrentAccount.getUserpkid());
                AppSimpleBack.postShowWith(outsideAty, SimpleBackPage.VERIFY2, bundle, true);
            }
        }, null);
    }

    private void gotoOpenAccount() {
        DialogUtils.showAlertDialog(this.outsideAty, R.string.prompt, "你还没有开通托管账户,去开通？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CommonUtils.openHuifuAccount(outsideAty, AppContext.m_CurrentAccount.getUserpkid());
            }
        }, null);
    }

    private void gotoBindBankCard() {
        DialogUtils.showAlertDialog(this.outsideAty, R.string.prompt, "你还没有绑定银行卡,去绑定？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CommonUtils.bindBankCard(outsideAty, AppContext.m_CurrentAccount.getUserpkid());
            }
        }, null);
    }

    public void refresh() {
        m_welcome1_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);

        if (AppContext.m_CurrentAccount != null) {

            HttpUtils.getAssetsFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), AssetsResponse.class, new HttpBaseCallBack<AssetsResponse>() {

                @Override
                public void onSuccess(AssetsResponse response) {
                    fillUI(response);
                    m_welcome1_empty_layout.dismiss();
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
                    m_welcome1_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
//				ErrorProgressUtils.programError(outsideAty,errorNo,strMsg);
                }

                @Override
                public void onFinish() {

                }

            });
        } else {
            AppContext.logout(outsideAty);
        }
    }

    public void refresh2() {
        m_welcome1_swipeRefreshLayout.setRefreshing(true);
        HttpUtils.getAssetsFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), AssetsResponse.class, new HttpBaseCallBack<AssetsResponse>() {

            @Override
            public void onSuccess(AssetsResponse response) {
                fillUI(response);
                ViewInject.toast("刷新成功");
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("刷新失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
//				ErrorProgressUtils.programError(outsideAty,errorNo,strMsg);
            }

            @Override
            public void onFinish() {
                m_welcome1_swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    private void fillUI(AssetsResponse response) {
        if (response != null) {
            this.m_welcome1_tv_assets.setText(CommonUtils.keep2Decimal(StringUtils.isEmpty(response.getUserAssets()) ? "0.00" : response.getUserAssets()));
            this.m_welcome1_tv_lastday_income.setText(CommonUtils.keep2Decimal(StringUtils.isEmpty(response.getEarningsYes()) ? "0.00" : response.getEarningsYes()));
            this.m_welcome1_tv_month_income.setText(CommonUtils.keep2Decimal(StringUtils.isEmpty(response.getEarningsMon()) ? "0.00" : response.getEarningsMon()));
            this.m_welcome1_tv_bankcard.setText(response.getBankCardCount() + "");
            this.m_welcome1_tv_message.setText(response.getUserNoticeCount() + "");
            this.m_welcome1_tv_balance.setText(CommonUtils.keep2Decimal(StringUtils.isEmpty(response.getBalance()) ? "0.00" : response.getBalance()));

            if (response.getIsNewInvestors() == 1) {
                AppContext.IsNewInvestors = 1;
                getBidInfo();
            } else {
                AppContext.IsNewInvestors = 0;
                this.m_welcome1_ll_newbid.setVisibility(View.GONE);
            }

            UserUtils.updateUserBankInfo(response.getBankCardCount(), response.getBalance());

        }
    }

    private void getBidInfo() {
        HttpUtils.getInvestBidInfo(this.outsideAty, "1", LicaiProductItem.class, new HttpBaseCallBack<LicaiProductItem>() {

            @Override
            public void onSuccess(LicaiProductItem response) {
                newBidItem = response;
                if ((response.getPaidState() == AppConfig.BID_STATE_ONGOING) && (response.getFinancingRate() < 100)) {
                    m_welcome1_newbid_year_interest.setText(response.getExpected_Annual_Rate() + "%");
                    m_welcome1_newbid_invest_minmoney.setText(response.getInvestmentAmount() + "元");
                    m_welcome1_newbid_duration.setText(response.getProject_Duration() + "");
                    m_welcome1_ll_newbid.setVisibility(View.VISIBLE);
                } else {
                    m_welcome1_ll_newbid.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                m_welcome1_ll_newbid.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {

            }

        });
    }


    private class BalanceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConfig.BALANCE_BROADCAST_ACTION)) {
                String balance = intent.getStringExtra("balance");
                if (!StringUtils.isEmpty(balance)) {
                    m_welcome1_tv_balance.setText(balance);
                    UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_BALANCE, balance);
                }
            }
        }

    }

    public void addGuideImage() {
        View view = getActivity().getWindow().getDecorView().findViewById(R.id.main_root);
        if (view == null) return;
        boolean isFirst = PreferenceHelper.readBoolean(aty, AppConfig.APPNAME, "manual_guide", true);
        if (!isFirst) {
            return;
        }
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) viewParent;
            final ImageView guideImage = new ImageView(getActivity());

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            guideImage.setLayoutParams(params);
            guideImage.setScaleType(ScaleType.FIT_XY);
            guideImage.setImageResource(R.mipmap.manual);
            guideImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameLayout.removeView(guideImage);
                    PreferenceHelper.write(aty, AppConfig.APPNAME, "manual_guide", false);
                }
            });
            frameLayout.addView(guideImage);
        }
    }
}


