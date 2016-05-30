package hotP2B.WageGainTools.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.adapter.IncomeAdapter;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.IncomeResponse;
import hotP2B.WageGainTools.android.bean.response.IncomeResponse.IncomeItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.TimeUtils;


public class AppIncome extends AppTitleBar 
{

	private int type=0;
	private String startdate;
	private String enddate;
	
	@BindView(id=R.id.income_tv_date)
	private TextView m_income_tv_date;
	@BindView(id=R.id.income_tv_money)
	private TextView m_income_tv_money;
	
	@BindView(id=R.id.income_iv_prev,click=true)
	private ImageView m_income_iv_prev;
	
	@BindView(id=R.id.income_iv_next,click=true)
	private ImageView m_income_iv_next;
	
	@BindView(id=R.id.income_zlistview)
	private ZrcListView m_income_zlistview;
	
	private IncomeAdapter adapter;
	private List<IncomeItem> datas;

	
	@Override
	public void setRootView() 
	{
	   this.setContentView(R.layout.aty_income);
	}
	
	@Override
    public void initData() 
    {
        super.initData();
    }


    @Override
	public void initTitleBar()
	{
	  this.mImgBack.setVisibility(View.VISIBLE);

	}
   
    
    @Override
    public void initWidget() 
    {
        super.initWidget();    
    
        datas=new ArrayList<IncomeItem>();
        m_income_zlistview.setDivider(new ColorDrawable(android.R.color.transparent));
        
        adapter=new IncomeAdapter(m_income_zlistview,datas);
		m_income_zlistview.setAdapter(adapter);
        
        
        Intent intent=this.getIntent();
        if(intent!=null)
        {
        	type=intent.getIntExtra("type", 1);
        	if(type==1)
        	{
        		this.mTvTitle.setText(R.string.lastday_income);
        		fillDateTime(new Date(),Calendar.DATE,-1);
        		
        	}
        	else if(type==2)
        	{
        		this.mTvTitle.setText(R.string.month_income);
                fillDateTime(new Date(),Calendar.MONTH,0);
        	}
        }
        m_income_zlistview.setOnRefreshStartListener(new OnStartListener() 
        {
            @Override
            public void onStart() 
            {
                refresh();
            }

        });
        m_income_zlistview.refresh();
        
    }
    

    @SuppressLint("SimpleDateFormat")
	private void fillDateTime(Date date,int filed,int value)
    {
    	Date curDate=TimeUtils.addDate(date,filed,value);
    	if(type==1)
    	{
    		
    		this.startdate=TimeUtils.formatDateTime(curDate, "yyyy-MM-dd");
    		this.enddate=this.startdate;
    		this.m_income_tv_date.setText(this.startdate);
    		
    		Date prevDay=TimeUtils.addDate(new Date(),Calendar.DATE,-2);
    		if(curDate.getTime()<=prevDay.getTime())
    		{
    			this.m_income_iv_next.setEnabled(true);
    		}
    		else
    		{
    			this.m_income_iv_next.setEnabled(false);
    		}
    	}
    	else if(type==2)
    	{
    		this.startdate=TimeUtils.formatDateTime(curDate, "yyyy-MM-01");
    		
    		Date d1=StringUtils.toDate(startdate,new SimpleDateFormat("yyyy-MM-dd"));
    		d1=TimeUtils.addDate(d1, Calendar.MONDAY,1);
    		d1=TimeUtils.addDate(d1,Calendar.DATE,-1);
    		this.enddate=TimeUtils.formatDateTime(d1, "yyyy-MM-dd");
    		this.m_income_tv_date.setText(TimeUtils.formatDateTime(curDate, "yyyy-MM"));
    		
    		Date prevMonth=TimeUtils.addDate(new Date(),Calendar.MONTH,-1);
    		if(curDate.getTime()<=prevMonth.getTime())
    		{
    			this.m_income_iv_next.setEnabled(true);
    		}
    		else
    		{
    			this.m_income_iv_next.setEnabled(false);
    		}
    	}
    	
    }
	private void refresh() 
	{
       HttpUtils.getIncomeFromServer(this, AppContext.m_CurrentAccount.getUserpkid(),startdate,enddate, IncomeResponse.class, new HttpBaseCallBack<IncomeResponse>()
       {

		@Override
		public void onSuccess(IncomeResponse response) 
		{
			if (response.getTotalEarnings().equals("0.0") ) {
				m_income_tv_money.setText("0.00");
			} else {
				m_income_tv_money.setText(response.getTotalEarnings()+"");
			}
			datas=response.getData();
			adapter.refresh(datas);
			if(datas!=null && datas.size()>0)
			{
			    m_income_zlistview.setRefreshSuccess();
			}
			else
			{
			   m_income_zlistview.setRefreshSuccess("没有数据");
			}
		}

		@Override
		public void onFailure(int errorNo, String strMsg) {
			ViewInject.toast("获取收益信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
			m_income_tv_money.setText("0.00");
			datas.clear();
			adapter.refresh(datas);
			m_income_zlistview.setRefreshFail();
			
		}

		@Override
		public void onFinish() 
		{
			m_income_zlistview.setLoadMoreSuccess();
			
		}
    	   
       });
		
	}
    @Override
    protected void onResume() 
    {
    	super.onResume();

    }

    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}
    
    @Override
	public void widgetClick(View v) 
	{
		super.widgetClick(v);
		switch (v.getId()) 
		{
		case R.id.income_iv_prev:
			onPrev();
			break;
		case R.id.income_iv_next:
			onNext();
			break;
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void onPrev() 
	{
		if(type==1)
		{
			Date curDate=StringUtils.toDate(this.m_income_tv_date.getText().toString(), new SimpleDateFormat("yyyy-MM-dd"));
			if(curDate==null)return;
			fillDateTime(curDate,Calendar.DATE,-1);
		}
		else if(type==2)
		{
			Date curMonth=StringUtils.toDate(this.m_income_tv_date.getText().toString(), new SimpleDateFormat("yyyy-MM"));
			if(curMonth==null)return;
			fillDateTime(curMonth,Calendar.MONTH,-1);
		}
		m_income_zlistview.refresh(); 
	}
	@SuppressLint("SimpleDateFormat")
	private void onNext() {

		if(type==1)
		{
			Date curDate=StringUtils.toDate(this.m_income_tv_date.getText().toString(), new SimpleDateFormat("yyyy-MM-dd"));
			if(curDate==null)return;
			fillDateTime(curDate,Calendar.DATE,1);
		}
		else if(type==2)
		{
			Date curMonth=StringUtils.toDate(this.m_income_tv_date.getText().toString(), new SimpleDateFormat("yyyy-MM"));
			if(curMonth==null)return;
			fillDateTime(curMonth,Calendar.MONTH,1);
		}
	
		m_income_zlistview.refresh(); 
	}


	
}
