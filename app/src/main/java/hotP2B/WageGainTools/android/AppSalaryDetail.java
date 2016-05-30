package hotP2B.WageGainTools.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import hotP2B.WageGainTools.android.adapter.SalaryDetailAdapter;
import hotP2B.WageGainTools.android.bean.Salary.SalaryDetailItem;
import hotP2B.WageGainTools.android.bean.Salary.SalaryItem;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.utils.CommonUtils;

public class AppSalaryDetail extends AppTitleBar {

    @BindView(id = R.id.salary_detail_tv_salary)
    private TextView m_salary_detail_tv_salary;
    @BindView(id = R.id.salary_detail_tv_yuan)
    private TextView salary_detail_tv_yuan;

    @BindView(id = R.id.item_salary_detail_tv_month)
    private TextView m_item_salary_detail_tv_month;

    @BindView(id = R.id.item_salary_detail_tv_year)
    private TextView m_item_salary_detail_tv_year;

    @BindView(id = R.id.salary_detail_gv)
    private GridView m_salary_detail_gv;
    @BindView(id = R.id.salary_detail_empty_layout, click = true)
    private EmptyLayout m_salary_detail_empty_layout;
    private SalaryDetailAdapter adapter;
    private List<SalaryDetailItem> datas;

    private SalaryItem salaryItem = null;


    @Override
    public void setRootView() {
        this.setContentView(R.layout.aty_salary_detail);

    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initTitleBar() {
        this.mImgBack.setVisibility(View.VISIBLE);
        this.mTvTitle.setText(R.string.salary_detail);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        datas = new ArrayList<SalaryDetailItem>();

        Intent intent = this.getIntent();
        if (intent == null) return;
        Bundle bundle = intent.getBundleExtra("salaryItem");
        if (bundle != null) {
            salaryItem = (SalaryItem) bundle.getSerializable("data");
        }

        if (salaryItem != null) {
            Date d = StringUtils.toDate(salaryItem.getWagestime(), new SimpleDateFormat("yyyy-MM-dd"));
            if (d != null) {
                int year = d.getYear() + 1900;
                int month = d.getMonth() + 1;
//                this.mTvTitle.setText(year + "年" + month + "月工资明细");
                this.m_item_salary_detail_tv_month.setText(month + "");
                this.m_item_salary_detail_tv_year.setText(year + "");
            } else {
                this.mTvTitle.setText("工资明细");
                this.m_item_salary_detail_tv_month.setText("0");
                this.m_item_salary_detail_tv_year.setText("0");
            }

            AssetManager mgr=getAssets();//得到AssetManager
            Typeface tf=Typeface.createFromAsset(mgr, "fonts/STXINWEI.TTF");//根据路径得到Typeface
            this.m_salary_detail_tv_salary.setTypeface(tf);//设置字体
            this.m_salary_detail_tv_salary.setText(CommonUtils.keep2Decimal(salaryItem.getCreditedwages()));
            this.salary_detail_tv_yuan.setTypeface(tf);//设置字体
            datas = this.salaryItem.getDetails();


            ArrayList<SalaryDetailItem> datasLose = new ArrayList<>();
            ArrayList<SalaryDetailItem> datasAdd = new ArrayList<>();
            datas = this.salaryItem.getDetails();

            if (datas != null) {
                for (int i = 0; i < datas.size(); i++) {
                    int singletype = datas.get(i).getSingletype();
                    if (singletype != 1) {
                        datasAdd.add(datas.get(i));
                    } else {
                        datasLose.add(datas.get(i));
                    }
                }
            }

            if (datasAdd.size() % 2 == 1) {
                datasAdd.add(new SalaryDetailItem());
            }

            datasAdd.addAll(datasLose);
            datas = datasAdd;
        }

        m_salary_detail_empty_layout.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                m_salary_detail_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
                refresh();
            }
        });
        refresh();

    }

    @Override
    public void onBackClick() {
        super.onBackClick();
        this.finish();
    }

    private void refresh() {
        if (adapter == null) {
            adapter = new SalaryDetailAdapter(m_salary_detail_gv, datas);
            m_salary_detail_gv.setAdapter(adapter);
        } else {
            adapter.refresh(datas);
        }
        m_salary_detail_empty_layout.dismiss();
    }
}
