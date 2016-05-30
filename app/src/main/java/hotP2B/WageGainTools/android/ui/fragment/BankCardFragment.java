package hotP2B.WageGainTools.android.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppGestureSetting;
import hotP2B.WageGainTools.android.AppGestureVerify;
import hotP2B.WageGainTools.android.AppRecharge;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SupportBank;
import hotP2B.WageGainTools.android.adapter.BankCardAdapter;
import hotP2B.WageGainTools.android.adapter.NewsAdapter;
import hotP2B.WageGainTools.android.bean.response.BankCardResponse;
import hotP2B.WageGainTools.android.bean.response.BankCardResponse.BankCardItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.UnBankCardResponse;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.dialog.CustomToastDialog;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.ui.widget.swipemenulistview.SwipeMenu;
import hotP2B.WageGainTools.android.ui.widget.swipemenulistview.SwipeMenuCreator;
import hotP2B.WageGainTools.android.ui.widget.swipemenulistview.SwipeMenuItem;
import hotP2B.WageGainTools.android.ui.widget.swipemenulistview.SwipeMenuListView;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class BankCardFragment extends TitleBarFragment {
    @BindView(id = R.id.bankcard_zlistview)
    private SwipeMenuListView m_bankcard_zlistview;

    @BindView(id = R.id.bankcard_empty_layout)
    private EmptyLayout m_bankcard_empty_layout;

    private BankCardAdapter adapter;
    private List<BankCardItem> dataList = new ArrayList<BankCardItem>();
    private String expressFlag;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(outsideAty, R.layout.frag_bankcard, null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "我的银行卡";
        actionBarRes.backImageId = R.mipmap.titlebar_back;
        actionBarRes.menuImageId = R.mipmap.titlebar_add;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        m_bankcard_empty_layout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(BankCardFragment.this.outsideAty);
                deleteItem.setBackground(R.color.titlebar);
                deleteItem.setWidth(DensityUtils.dip2px(outsideAty, 75.0f));
                deleteItem.setMarginTop(DensityUtils.dip2px(outsideAty, 20.0f));
                deleteItem.setIcon(R.mipmap.delete);
                menu.addMenuItem(deleteItem);
            }
        };
        m_bankcard_zlistview.setMenuCreator(creator);
        m_bankcard_zlistview.setDivider(this.outsideAty.getResources().getDrawable(R.color.transparent));
        m_bankcard_zlistview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        unBindBankCard(position);
                        break;
                }
                return false;
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onMenuClick() {
        super.onMenuClick();
        if (expressFlag != null && expressFlag.equals("Y")) {
            ViewInject.toast(BankCardFragment.this.outsideAty, "已绑定快捷卡，不能再添加银行卡！");
        } else {
            CommonUtils.bindBankCard(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid());
        }
    }

    @Override
    public void onBackClick() {
        this.outsideAty.finish();
    }

    private void unBindBankCard(final int position) {
        final BankCardItem item = dataList.get(position);
        if (item != null && !StringUtils.isEmpty(item.OpenAcctId)) {
            new CustomAlertDialog(this.outsideAty).Builder()
                    .setTitle("提示信息")
                    .setMsg("是否删除此银行卡:" + SupportBank.getNameByNick(item.OpenBankId) + ",尾号" + item.OpenAcctId.substring(item.OpenAcctId.length() - 4) + "?")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //
                            check(position);
                            //
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }

    private void check(int position) {
        GestureUtils.xx gestureInfo = GestureUtils.get(outsideAty, AppContext.m_CurrentAccount.getUserpkid());
        if (gestureInfo == null) {
            DialogUtils.showAlertDialog(outsideAty, "提示信息", "检测到你没有设置手势密码,去设置？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(BankCardFragment.this.outsideAty, AppGestureSetting.class);
                    BankCardFragment.this.outsideAty.startActivity(intent);

                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            Intent intent = new Intent(BankCardFragment.this.outsideAty, AppGestureVerify.class);
            intent.putExtra("position", position);
            BankCardFragment.this.startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 0:
                if (data != null) {
                    int position = data.getIntExtra("position", -1);
                    if (position != -1) {
                        unBindBankCard2(position);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void unBindBankCard2(final int position) {
        final BankCardItem card = dataList.get(position);
        if (card != null && !StringUtils.isEmpty(card.OpenAcctId)) {
            final CustomProgressDialog loadingDialog = new CustomProgressDialog(BankCardFragment.this.outsideAty, "正在删除银行卡,请稍等...", true);
            loadingDialog.show();
            HttpUtils.unBindBankCardFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), card.OpenAcctId, UnBankCardResponse.class, new HttpBaseCallBack<UnBankCardResponse>() {
                @Override
                public void onSuccess(UnBankCardResponse response) {
                    if (response != null) {
                        CustomToastDialog.makeSuccess(BankCardFragment.this.outsideAty, "删除银行卡成功！", Toast.LENGTH_SHORT).show();
                        BankCardFragment.this.outsideAty.sendBroadcast(new Intent(AppConfig.ASSET_BROADCAST_ACTION));
                        dataList.remove(position);
                        adapter.refresh(dataList);
                    }

                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    CustomToastDialog.makeError(BankCardFragment.this.outsideAty, "删除失败:" + strMsg, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                }
            });
        }
    }

    private void refresh() {
        m_bankcard_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
        HttpUtils.getBankCardFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), BankCardResponse.class, new HttpBaseCallBack<BankCardResponse>() {
            @Override
            public void onSuccess(BankCardResponse response) {
                dataList = response.getData();
                expressFlag = response.getExpressFlag();
                if (dataList != null && dataList.size() > 0) {
                    if (adapter == null) {
                        adapter = new BankCardAdapter(BankCardFragment.this.outsideAty, dataList);
                        m_bankcard_zlistview.setAdapter(adapter);
                    } else {
                        adapter.refresh(dataList);
                    }
                    m_bankcard_empty_layout.dismiss();
                } else {
                    m_bankcard_empty_layout.setErrorType(EmptyLayout.NODATA);
                    m_bankcard_empty_layout.setErrorImag(R.mipmap.icon_no_bankcard);
                    m_bankcard_empty_layout.setErrorMessage("还未绑定任何银行卡哦~");
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("获取银行卡信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
                m_bankcard_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }

            @Override
            public void onFinish() {

            }

        });

    }


}
