package hotP2B.WageGainTools.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import hotP2B.WageGainTools.android.adapter.SalaryAdapter;
import hotP2B.WageGainTools.android.bean.BaseResponse;
import hotP2B.WageGainTools.android.bean.Salary;
import hotP2B.WageGainTools.android.bean.Salary.SalaryItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase.OnRefreshListener;
import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshList;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppSalary extends AppTitleBar implements SalaryAdapter.SalaryItemClickListener {

    @BindView(id = R.id.salary_swiperefreshlayout)
    private PullToRefreshList m_salary_swiperefreshlayout;
    @BindView(id = R.id.salary_empty_layout, click = true)
    private EmptyLayout m_salary_empty_layout;
    @BindView(id = R.id.item_salary_tv_time)
    private TextView item_salary_tv_time;

    private ListView m_List;
    private List<SalaryItem> datas;
    private SalaryAdapter adapter = null;

    @Override
    public void setRootView() {
        this.setContentView(R.layout.aty_salary);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initTitleBar() {
        this.mImgBack.setVisibility(View.VISIBLE);
        this.mTvTitle.setText(R.string.salary);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        datas = new ArrayList<SalaryItem>();
        m_List = m_salary_swiperefreshlayout.getRefreshView();
        m_List.setDivider(new ColorDrawable(android.R.color.transparent));
        m_List.setOverscrollFooter(null);
        m_List.setOverscrollHeader(null);
        m_List.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        m_salary_swiperefreshlayout.setPullRefreshEnabled(false);
        m_salary_swiperefreshlayout.setPullLoadEnabled(true);
        m_salary_swiperefreshlayout.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                refresh(0,false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullup();
            }
        });

        m_salary_empty_layout.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                m_salary_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
                refresh(0,false);
            }
        });
        refresh(0,false);
    }

    private void pullup() {
        int startNum=0;
        if(this.datas.size()%AppConfig.PAGE_SIZE==0)
        {
            startNum=this.datas.size()/AppConfig.PAGE_SIZE;
        }
        else
        {
            startNum=this.datas.size()/AppConfig.PAGE_SIZE+1;
        }
        refresh(startNum,true);
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


    private void refresh(final int startNum, final boolean isPullup) {
        if (!isPullup) {
            datas.clear();
        }

        HttpUtils.getSalary(this, AppContext.m_CurrentAccount.getUserpkid(), startNum, AppConfig.PAGE_SIZE, Salary.class, new HttpBaseCallBack<Salary>() {
            @Override
            public void onSuccess(Salary response) {
                List<SalaryItem> list = response.getData();

                if (list != null && list.size() > 0) {
                    datas.addAll(list);
                }

                if (!isPullup) {
                    adapter = new SalaryAdapter(AppSalary.this, datas);
                    adapter.setSalaryItemClickListener(AppSalary.this);
                    m_List.setAdapter(adapter);
                    if (datas.size() > 0) {
                        m_salary_empty_layout.dismiss();

                        SalaryItem salaryItemStart = datas.get(0);
                        SalaryItem salaryItemLast = datas.get(datas.size() - 1);
                        Date dStart = StringUtils.toDate(salaryItemStart.getWagestime(), new SimpleDateFormat("yyyy-MM-dd"));

                        StringBuffer stringBuffer = new StringBuffer();
                        if (dStart != null) {
                            stringBuffer.append(dStart.getYear() + 1900);
                            stringBuffer.append(".");
                            stringBuffer.append(dStart.getMonth() + 1);
                            stringBuffer.append(" - ");
                        }
//                        if (datas.size() > 1) {
                            Date dEnd = StringUtils.toDate(salaryItemLast.getWagestime(), new SimpleDateFormat("yyyy-MM-dd"));
                            if (dEnd != null) {
                                stringBuffer.append(dEnd.getYear() + 1900);
                                stringBuffer.append(".");
                                stringBuffer.append(dEnd.getMonth() + 1);
//                            }
                        }

                        AppSalary.this.item_salary_tv_time.setText(stringBuffer.toString());
                    } else {
                        m_salary_empty_layout.setErrorType(EmptyLayout.NODATA);
                        m_salary_empty_layout.setErrorImag(R.mipmap.nosalary_2px);
                        m_salary_empty_layout.setErrorMessage("还没有工资记录哦~");
                    }
                } else {
                    adapter.refresh(datas);
                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Log.e("", "----------2-------" + errorNo + "-----" + strMsg);
                if (adapter != null && adapter.getCount() > 0 ) {
                    return;
                } else  {
                    m_salary_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
            }

            @Override
            public void onFinish() {
                m_salary_swiperefreshlayout.onPullDownRefreshComplete();
                m_salary_swiperefreshlayout.onPullUpRefreshComplete();
            }
        });

    }

    @Override
    public void salaryItemClcik(View v, int position) {
        switch (v.getId()) {
            case R.id.item_go_salary_details_rl:
                SalaryItem salaryItem = datas.get(position);
                if (salaryItem != null && salaryItem.getDetails() != null && salaryItem.getDetails().size() > 0) {
                    Intent intent = new Intent(AppSalary.this, AppSalaryDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", salaryItem);
                    intent.putExtra("salaryItem", bundle);
                    startActivity(intent);
                }
        }
    }
}
