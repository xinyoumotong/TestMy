package hotP2B.WageGainTools.android.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.InvestHistory3Adapter;
import hotP2B.WageGainTools.android.adapter.UserNoticeAdapter;
import hotP2B.WageGainTools.android.bean.InvestHistory3;
import hotP2B.WageGainTools.android.bean.InvestHistory3.InvestHistory3Item;
import hotP2B.WageGainTools.android.bean.UserNotice;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;


public class InvestHistoryWithdrawFragment extends TitleBarFragment
{
	
	@BindView(id = R.id.invest_history_zlistview)
	private ZrcListView invest_history_zlistview;

	private InvestHistory3Adapter adapter3 =null;
	private List<InvestHistory3Item> data3;




	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		View view = View.inflate(outsideAty, R.layout.frag_invest_history_new, null);
	    return view;
	}

	 @Override
	 protected void setActionBarRes(ActionBarRes actionBarRes) 
	 {
		 actionBarRes.title ="投资记录";
	     actionBarRes.backImageId=R.mipmap.titlebar_back;
	 }
	 
	 @Override
	 protected void initData() 
	 {
	   super.initData();    
	 }

	 @Override
	 protected void initWidget(View parentView) 
	 {
	   super.initWidget(parentView);

		 data3=new ArrayList<InvestHistory3Item>();

		 invest_history_zlistview.setDivider(new ColorDrawable(android.R.color.transparent));
	   invest_history_zlistview.setOnRefreshStartListener(new OnStartListener() {
           @Override
           public void onStart() {
               refresh();
           }
       });

	   invest_history_zlistview.setOnLoadMoreStartListener(new OnStartListener() {
           @Override
           public void onStart() {
               loadMore();
           }
       });
    
      invest_history_zlistview.refresh();
	   
	 }
	 

	public void onMenuClick() 
	 {
	   super.onMenuClick();
	 }
	 

     @Override
	 public void onBackClick() 
	 {
		super.onBackClick();
		this.outsideAty.finish();	
	 }
     
     @Override
 	 public void widgetClick(View v) 
     {
 		super.widgetClick(v);

     }
     
     private void refresh()
     {
    	 data3.clear();
    	 if(adapter3 !=null)
    	 {
    		 adapter3.refresh(data3);
    	 }

		 HttpUtils.getInvestHistory3(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),0+"",AppConfig.PAGE_SIZE+"",InvestHistory3.class,new HttpBaseCallBack<InvestHistory3>()
		 {
			 @Override
			 public void onSuccess(InvestHistory3 response)
			 {
				 List<InvestHistory3Item> list=response.getData();
 				if(list!=null && list.size()>0)
 				{
 					data3.addAll(list);
 					if(adapter3 ==null)
 					{
 			    		adapter3 = new InvestHistory3Adapter(invest_history_zlistview, data3, new InvestHistory3Adapter.FlowBtnClickListener() {
							@Override
							public void onClick(InvestHistory3Item item) {
								doFlowBid(item);
							}
						});
 			    		invest_history_zlistview.setAdapter(adapter3);
 			    	}
 					else
 					{
 			    		adapter3.refresh(data3);
 			    	}
 					invest_history_zlistview.setRefreshSuccess();
 					invest_history_zlistview.startLoadMore();
 				}
 				else
 				{
 					invest_history_zlistview.setRefreshSuccess("没有数据");
 				}
 			 }

 			 @Override
 			 public void onFailure(int errorNo, String strMsg) 
 			 {
 				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				 invest_history_zlistview.setRefreshFail();
 			 }

 			 @Override
 			 public void onFinish() 
 			 {

         	 }
 		   });	
      }

	private void doFlowBid(InvestHistory3Item item)
	{
		if(item==null)return;
		if(AppContext.m_CurrentAccount==null)return;
		double dMoney;
		try
		{
			dMoney=Double.parseDouble(item.getTransAmt());
		}
		catch(Exception e)
		{
			ViewInject.toast("投资金额不合法");
			return;
		}
		DecimalFormat df=new DecimalFormat("#.00");
		String strCash=df.format(dMoney);
		CommonUtils.flowbid(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),item.getOrdId(),strCash, item.getFreezeId(),item.getBidproid());

	}

	private void loadMore()
      {
    	 int startnum=0;
   	     if(data3.size()%AppConfig.PAGE_SIZE==0)
   	     {
   		   startnum= data3.size()/AppConfig.PAGE_SIZE;
   	     }
   	     else
   	     {
   		   startnum= data3.size()/AppConfig.PAGE_SIZE+1;
   	     }

		  HttpUtils.getInvestHistory3(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),startnum+"",AppConfig.PAGE_SIZE+"",InvestHistory3.class,new HttpBaseCallBack<InvestHistory3>()
		  {
			  @Override
			  public void onSuccess(InvestHistory3 response)
			  {
				  List<InvestHistory3Item> list=response.getData();
				if(list!=null && list.size()>0)
				{
					data3.addAll(list);
					if(adapter3 ==null)
					{
			    		adapter3 = new InvestHistory3Adapter(invest_history_zlistview, data3, new InvestHistory3Adapter.FlowBtnClickListener() {
							@Override
							public void onClick(InvestHistory3Item item) {
								doFlowBid(item);
							}
						});
			    		invest_history_zlistview.setAdapter(adapter3);
			    	}
					else
					{
			    		adapter3.refresh(data3);
			    	}
					invest_history_zlistview.setLoadMoreSuccess();
				}
				else
				{
					invest_history_zlistview.stopLoadMore();
				}
			 }

			 @Override
			 public void onFailure(int errorNo, String strMsg) 
			 {
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				 invest_history_zlistview.stopLoadMore();
			 }

			 @Override
			 public void onFinish() 
			 {

      	     }
		   });	
 
     }



}
