package hotP2B.WageGainTools.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hotP2B.WageGainTools.android.AppBrowser;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppFragment;
import hotP2B.WageGainTools.android.AppProductDetail;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.LicaiProductAdapter;
import hotP2B.WageGainTools.android.bean.LicaiProduct;
import hotP2B.WageGainTools.android.bean.LicaiProduct.LicaiProductItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class LicaiFragment extends AppFragment
{

    @BindView(id = R.id.licai_zlistview)
    private ZrcListView m_licai_zlistview;

    private int type = -1;
    private List<LicaiProductItem> datas;
    private LicaiProductAdapter adapter = null;

    public static final int LICAI_GOODLOAN = 11;//良薪贷
    public static final int LICAI_EBDOOR = 12; //一比多

    private LicaiReceiver licaiReceiver = null;

    public LicaiFragment() {
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.frag_licai, container, false);
        return view;
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        datas = new ArrayList<LicaiProductItem>();

        m_licai_zlistview.setDivider(new ColorDrawable(android.R.color.transparent));
        m_licai_zlistview.setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        m_licai_zlistview.setOnLoadMoreStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }

        });

        m_licai_zlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                LicaiProductItem item = datas.get(position);
                if (item == null) return;
                Intent intent = null;
                if (type == LICAI_GOODLOAN) {
                    intent = new Intent(getActivity(), AppProductDetail.class);
                    intent.putExtra("bidproid", item.getBidProId());
                    intent.putExtra("url", item.getFurl());
                } else if (type == LICAI_EBDOOR) {
                    intent = new Intent(getActivity(), AppBrowser.class);
                    intent.putExtra(AppBrowser.BROWSER_KEY, item.getFurl());
                    intent.putExtra(AppBrowser.BROWSER_TITLE_KEY, item.getFtitle());
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }

        });

        licaiReceiver = new LicaiReceiver();
        IntentFilter filterLicai = new IntentFilter();
        filterLicai.addAction(AppConfig.LICAI_BROADCAST_ACTION);
        this.getActivity().registerReceiver(licaiReceiver, filterLicai);

        m_licai_zlistview.refresh();
    }

    private void refresh() {
        HttpUtils.getLicaiProductFromServer(this.getActivity(), "0", AppConfig.PAGE_SIZE + "", type + "", LicaiProduct.class, new HttpBaseCallBack<LicaiProduct>() {
            @Override
            public void onSuccess(LicaiProduct response) {
                List<LicaiProductItem> list = response.getData();
                if (list != null && list.size() > 0) {
                    datas.clear();
                    datas.addAll(list);
                    if (adapter == null) {
                        adapter = new LicaiProductAdapter(m_licai_zlistview, datas);
                        m_licai_zlistview.setAdapter(adapter);
                    } else {
                        adapter.refresh(datas);
                    }
                    m_licai_zlistview.setRefreshSuccess();
                    if (list.size() == AppConfig.PAGE_SIZE) {
                        m_licai_zlistview.startLoadMore();
                    }

                } else {
                    datas.clear();
                    if (adapter == null) {
                        adapter = new LicaiProductAdapter(m_licai_zlistview, datas);
                        m_licai_zlistview.setAdapter(adapter);
                    } else {
                        adapter.refresh(datas);
                    }
                    m_licai_zlistview.setRefreshSuccess("没有数据");
                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
                m_licai_zlistview.setRefreshFail();
            }

            @Override
            public void onFinish() {

            }

        });
    }

    ;


    private void loadMore() {
        int startnum = 0;
        if (datas.size() % AppConfig.PAGE_SIZE == 0) {
            startnum = datas.size() / AppConfig.PAGE_SIZE;
        } else {
            startnum = datas.size() / AppConfig.PAGE_SIZE + 1;
        }
        HttpUtils.getLicaiProductFromServer(this.getActivity(), startnum + "", AppConfig.PAGE_SIZE + "", type + "", LicaiProduct.class, new HttpBaseCallBack<LicaiProduct>() {

            @Override
            public void onSuccess(LicaiProduct response) {
                List<LicaiProductItem> list = response.getData();
                if (list != null && list.size() > 0) {
                    datas.addAll(list);
                    if (adapter == null) {
                        adapter = new LicaiProductAdapter(m_licai_zlistview, datas);
                        m_licai_zlistview.setAdapter(adapter);
                    } else {
                        adapter.refresh(datas);
                    }
                    m_licai_zlistview.setLoadMoreSuccess();

                } else {
                    m_licai_zlistview.stopLoadMore();
                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
                m_licai_zlistview.stopLoadMore();
            }

            @Override
            public void onFinish() {

            }

        });
    }

    private class LicaiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConfig.LICAI_BROADCAST_ACTION) && (type == LICAI_GOODLOAN)) {
                refresh();
            }
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
