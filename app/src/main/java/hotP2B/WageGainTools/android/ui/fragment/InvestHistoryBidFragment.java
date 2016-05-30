package hotP2B.WageGainTools.android.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import java.util.ArrayList;
import java.util.List;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.InvestHistory1Adapter;
import hotP2B.WageGainTools.android.adapter.UserNoticeAdapter;
import hotP2B.WageGainTools.android.bean.InvestHistory1;
import hotP2B.WageGainTools.android.bean.InvestHistory1.InvestHistory1Item;
import hotP2B.WageGainTools.android.bean.UserNotice;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.HttpUtils;


public class InvestHistoryBidFragment extends TitleBarFragment
{
	
	@BindView(id = R.id.invest_history_zlistview)
	private ZrcListView invest_history_zlistview;

	private InvestHistory1Adapter adapter1 =null;
	private List<InvestHistory1Item> data1;


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
	   
	   data1 =new ArrayList<InvestHistory1Item>();

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
    	 data1.clear();
    	 if(adapter1 !=null)
    	 {
    		 adapter1.refresh(data1);
    	 }

		 HttpUtils.getInvestHistory1(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),"0",AppConfig.PAGE_SIZE+"",InvestHistory1.class,new HttpBaseCallBack<InvestHistory1>()
		 {
			 @Override
			 public void onSuccess(InvestHistory1 response)
			 {
				 List<InvestHistory1Item> list=response.getData();
 				if(list!=null && list.size()>0)
 				{
 					data1.addAll(list);
 					if(adapter1 ==null)
 					{
 			    		adapter1 = new InvestHistory1Adapter(invest_history_zlistview, data1);
						invest_history_zlistview.setAdapter(adapter1);
 			    	}
 					else
 					{
 			    		adapter1.refresh(data1);
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
     
      private void loadMore()
      {
    	 int startnum=0;
   	     if(data1.size()%AppConfig.PAGE_SIZE==0)
   	     {
   		   startnum= data1.size()/AppConfig.PAGE_SIZE;
   	     }
   	     else
   	     {
   		   startnum= data1.size()/AppConfig.PAGE_SIZE+1;
   	     }

		  HttpUtils.getInvestHistory1(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),startnum+"",AppConfig.PAGE_SIZE+"",InvestHistory1.class,new HttpBaseCallBack<InvestHistory1>()
		  {
			  @Override
			  public void onSuccess(InvestHistory1 response)
			  {
				  List<InvestHistory1Item> list=response.getData();
				if(list!=null && list.size()>0)
				{
					data1.addAll(list);
					if(adapter1 ==null)
					{
			    		adapter1 = new InvestHistory1Adapter(invest_history_zlistview, data1);
						invest_history_zlistview.setAdapter(adapter1);
			    	}
					else
					{
			    		adapter1.refresh(data1);
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
