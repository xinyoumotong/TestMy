package hotP2B.WageGainTools.android;

import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.adapter.BalanceDetailAdapter;
import hotP2B.WageGainTools.android.bean.response.BalanceResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.BalanceResponse.BalanceItem;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppBalanceDetail extends AppTitleBar  
{

	@BindView(id = R.id.balance_detail_empty_layout)
	private EmptyLayout m_balance_detail_empty_layout;
	
	@BindView(id = R.id.balance_detail_lv)
    private ListView m_balance_detail_lv;
	
	private String startdate;
	private String enddate;
	
	private  BalanceDetailAdapter adapter=null;
	
	@Override
	public void setRootView() 
	{
       this.setContentView(R.layout.aty_balance_detail);
	}
	
	@Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText("余额明细");
	}
	
	@Override
	public void initWidget() 
	{
	   super.initWidget();
	   m_balance_detail_empty_layout.setOnLayoutClickListener(new OnClickListener() 
	   {
			@Override
			public void onClick(View v) 
			{
				refresh();
			}
	   });

	   
	   Intent intent=this.getIntent();
	   if(intent!=null)
	   {
		   startdate=intent.getStringExtra("startdate");
		   enddate=intent.getStringExtra("enddate");
		   refresh();
	   }
	}
	
	 private void refresh()
	 {
          if(!StringUtils.isEmpty(startdate) && !StringUtils.isEmpty(enddate))
          {
        	 m_balance_detail_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
        	 HttpUtils.getBalanceFromServer(this.aty,AppContext.m_CurrentAccount.getUserpkid(),startdate,enddate,BalanceResponse.class,new HttpBaseCallBack<BalanceResponse>()
      		 {

      			@Override
      			public void onSuccess(BalanceResponse response) 
      			{
      		       List<BalanceItem>list=response.getData();
      		       if(list!=null && list.size()>0)
      		       {
                       fillUI(list);
                       m_balance_detail_empty_layout.dismiss();
      		       }
      		       else
      		       {
      		    	 m_balance_detail_empty_layout.setErrorType(EmptyLayout.NODATA);
      		       }

      			}

				@Override
      			public void onFailure(int errorNo, String strMsg) {
      	
      				ViewInject.toast("获取余额明细失败,错误代码:" + errorNo + "错误信息:" + strMsg);
      				m_balance_detail_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
      			}

      			@Override
      			public void onFinish() {
      	
      				
      			}
      			
      		});
          }
		
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
 		   default:
 			   break;
 		}
     }

	 
	private void fillUI(List<BalanceItem> list) 
	{
	   adapter=new BalanceDetailAdapter(m_balance_detail_lv,list);
	   m_balance_detail_lv.setAdapter(adapter);
			
	}

}
