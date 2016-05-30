package hotP2B.WageGainTools.android;

import java.util.Calendar;
import java.util.Date;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.utils.SystemTool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import hotP2B.WageGainTools.android.utils.TimeUtils;

public class AppBalance extends AppTitleBar 
{
    //7天内
	@BindView(id = R.id.balance_btn_oneWeek,click=true)
	private Button m_balance_btn_oneWeek;
	
	//最近一个月
	@BindView(id = R.id.balance_btn_oneMonth,click=true)
	private Button m_balance_btn_oneMonth;

	//最近三个月
	@BindView(id = R.id.balance_btn_threeMonth,click=true)
	private Button m_balance_btn_threeMonth;
	
	
	//开始日期
	@BindView(id = R.id.balance_et_startDT)
	private EditText m_balance_et_startDT;
		
	//结束日期
	@BindView(id = R.id.balance_et_endDT)
	private EditText m_balance_et_endDT;
		
	//查询
	@BindView(id = R.id.balance_btn_query, click = true)
	private Button m_balance_btn_query;
	

		
	@Override
	public void setRootView() 
	{
      this.setContentView(R.layout.aty_balance);
		
	}
	
	@Override
    public void initData() 
    {
        super.initData();
   
  
    }

    @Override
    public void initWidget() 
    {
        super.initWidget();        
        fillUI(1,Calendar.DATE,-7);
        m_balance_btn_oneWeek.setSelected(true);
       
    }
    
    @Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText("余额明细查询");
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
        case R.id.balance_btn_oneWeek:
        	fillUI(1,Calendar.DATE,-7);
        	break;
        case R.id.balance_btn_oneMonth:
        	fillUI(2,Calendar.MONTH,-1);
        	break;
        case R.id.balance_btn_threeMonth:
        	fillUI(3,Calendar.MONTH,-3);
        	break;
        case R.id.balance_btn_query:
        	check();
        	break;
        default:
            break;
        }
    }

	private void check() 
	{
       String startDT=this.m_balance_et_startDT.getText().toString().trim();
       String endDT=this.m_balance_et_endDT.getText().toString().trim();
       
       if(StringUtils.isEmpty(startDT))
       {
    	   ViewInject.toast("开始日期不能为空");
    	   return;
       }
       
       if(StringUtils.isEmpty(endDT))
       {
    	   ViewInject.toast("结束日期不能为空");
    	   return;
       }
       
       if(startDT.length()!=8 || !TimeUtils.isValidDate(startDT,"yyyyMMdd"))
       {
    	   ViewInject.toast("开始日期格式不合法，合法的格式如20150812");
    	   return;
       }
       
       if(endDT.length()!=8 || !TimeUtils.isValidDate(endDT,"yyyyMMdd"))
       {
    	   ViewInject.toast("结束日期格式不合法，合法的格式如20150912");
    	   return;
       }

       if(startDT.compareTo(endDT)>0)
       {
    	   ViewInject.toast("开始日期不能大于结束日期");
    	   return;
       }
       
       String startdate=startDT.substring(0, 4)+"-"+startDT.substring(4,6)+"-"+startDT.substring(6, 8);
       String enddate=endDT.substring(0, 4)+"-"+endDT.substring(4,6)+"-"+endDT.substring(6, 8);
       doQuery(startdate,enddate);
	}
	
	private void doQuery(String startdate,String enddate)
	{
		
		Bundle bundle=new Bundle();
		bundle.putString("startdate", startdate);
		bundle.putString("enddate", enddate);
		
		this.showActivity(this, AppBalanceDetail.class, bundle);
		
		
	}

	private void fillUI(int index,int field,int value) 
	{
	   
    	this.m_balance_et_startDT.setText(TimeUtils.formatDateTime(TimeUtils.addDate(new Date(), field, value),"yyyyMMdd"));
       	this.m_balance_et_endDT.setText(SystemTool.getDataTime("yyyyMMdd"));
       	
       	this.m_balance_btn_oneWeek.setSelected(false);
       	this.m_balance_btn_oneMonth.setSelected(false);
       	this.m_balance_btn_threeMonth.setSelected(false);
       	
       	switch(index)
       	{
       	case 1:
       		this.m_balance_btn_oneWeek.setSelected(true);
       		break;
       	case 2:
       		this.m_balance_btn_oneMonth.setSelected(true);
       		break;
       	case 3:
       		this.m_balance_btn_threeMonth.setSelected(true);
       		break;
       	default:
       		break;
       	}
		
	}
	    
	 


}
