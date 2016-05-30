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
import hotP2B.WageGainTools.android.adapter.InvestHistory2Adapter;
import hotP2B.WageGainTools.android.adapter.UserNoticeAdapter;
import hotP2B.WageGainTools.android.bean.InvestHistory2;
import hotP2B.WageGainTools.android.bean.InvestHistory2.InvestHistory2Item;
import hotP2B.WageGainTools.android.bean.UserNotice;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.HttpUtils;


public class InvestHistoryBackFragment extends TitleBarFragment
{
	
	@BindView(id = R.id.invest_history_zlistview)
	private ZrcListView invest_history_zlistview;

	private InvestHistory2Adapter adapter2 =null;
	private List<InvestHistory2Item> data2;


	

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

		 data2=new ArrayList<InvestHistory2Item>();

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
    	 data2.clear();
    	 if(adapter2 !=null)
    	 {
    		 adapter2.refresh(data2);
    	 }

		 HttpUtils.getInvestHistory2(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),0+"",AppConfig.PAGE_SIZE+"",InvestHistory2.class,new HttpBaseCallBack<InvestHistory2>()
		 {
			 @Override
			 public void onSuccess(InvestHistory2 response)
			 {
				 List<InvestHistory2Item> list=response.getData();
 				if(list!=null && list.size()>0)
 				{
 					data2.addAll(list);
 					if(adapter2 ==null)
 					{
 			    		adapter2 = new InvestHistory2Adapter(invest_history_zlistview, data2);
						invest_history_zlistview.setAdapter(adapter2);
 			    	}
 					else
 					{
 			    		adapter2.refresh(data2);
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
   	     if(data2.size()%AppConfig.PAGE_SIZE==0)
   	     {
   		   startnum= data2.size()/AppConfig.PAGE_SIZE;
   	     }
   	     else
   	     {
   		   startnum= data2.size()/AppConfig.PAGE_SIZE+1;
   	     }

		  HttpUtils.getInvestHistory2(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),startnum+"",AppConfig.PAGE_SIZE+"",InvestHistory2.class,new HttpBaseCallBack<InvestHistory2>()
		  {
			  @Override
			  public void onSuccess(InvestHistory2 response)
			  {
				  List<InvestHistory2Item> list=response.getData();
				if(list!=null && list.size()>0)
				{
					data2.addAll(list);
					if(adapter2 ==null)
					{
			    		adapter2 = new InvestHistory2Adapter(invest_history_zlistview, data2);
						invest_history_zlistview.setAdapter(adapter2);
			    	}
					else
					{
			    		adapter2.refresh(data2);
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
