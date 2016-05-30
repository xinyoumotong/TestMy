package hotP2B.WageGainTools.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.adapter.HongbaoBalanceAdapter;
import hotP2B.WageGainTools.android.bean.HongbaoBalance;
import hotP2B.WageGainTools.android.bean.HongbaoBalance.HongbaoBalanceItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class HongbaoBalanceFragment extends TitleBarFragment 
{

	@BindView(id = R.id.hongbao_balance_zlistview)
	private ZrcListView m_hongbao_balance_zlistview;
	
	private HongbaoBalanceAdapter adapter=null;
	private List<HongbaoBalanceItem>datas;

	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) 
	{
		View view = View.inflate(outsideAty, R.layout.frag_hongbao_balance, null);
	    return view;
	}

	 @Override
	 protected void setActionBarRes(ActionBarRes actionBarRes) 
	 {
		 actionBarRes.title ="礼拜红包记录";
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
	   
	   datas=new ArrayList<HongbaoBalanceItem>();
	   
	   m_hongbao_balance_zlistview.setOnRefreshStartListener(new OnStartListener() 
	   {
           @Override
           public void onStart() 
           {
               refresh();
           }
       });

	   m_hongbao_balance_zlistview.setOnLoadMoreStartListener(new OnStartListener() 
	   {
           @Override
           public void onStart() 
           {
               loadMore();
           }
       });
    
	   m_hongbao_balance_zlistview.refresh();
	   
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
    	 datas.clear();
    	 if(adapter!=null)
    	 {
    		 adapter.refresh(datas);
    	 }
    	 
    	 HttpUtils.getHongbaoBalanceFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),"0",AppConfig.PAGE_SIZE+"", HongbaoBalance.class, new HttpBaseCallBack<HongbaoBalance>() 
 		 {

 			@Override
 			public void onSuccess(HongbaoBalance response) 
 			{
 				List<HongbaoBalanceItem>list=response.getList();
 				if(list!=null && list.size()>0)
 				{
 					datas.addAll(list);
 					if(adapter==null)
 					{
 			    		adapter = new HongbaoBalanceAdapter(m_hongbao_balance_zlistview, datas);
 			    		m_hongbao_balance_zlistview.setAdapter(adapter);
 			    	}
 					else
 					{
 			    		adapter.refresh(datas);
 			    	}
 					m_hongbao_balance_zlistview.setRefreshSuccess();
 					
 					m_hongbao_balance_zlistview.startLoadMore();
 				}
 				else
 				{
 					m_hongbao_balance_zlistview.setRefreshSuccess("没有数据");
 				}
 			 }

 			 @Override
 			 public void onFailure(int errorNo, String strMsg) 
 			 {
 				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
 				m_hongbao_balance_zlistview.setRefreshFail();
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
   	     if(datas.size()%AppConfig.PAGE_SIZE==0)
   	     {
   		   startnum=datas.size()/AppConfig.PAGE_SIZE;
   	     }
   	     else
   	     {
   		   startnum=datas.size()/AppConfig.PAGE_SIZE+1;
   	     }
   	     
   	     HttpUtils.getHongbaoBalanceFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),startnum+"",AppConfig.PAGE_SIZE+"", HongbaoBalance.class, new HttpBaseCallBack<HongbaoBalance>() 
		 {

			@Override
			public void onSuccess(HongbaoBalance response) 
			{
				List<HongbaoBalanceItem>list=response.getList();
				if(list!=null && list.size()>0)
				{
					datas.addAll(list);
					if(adapter==null)
					{
			    		adapter = new HongbaoBalanceAdapter(m_hongbao_balance_zlistview, datas);
			    		m_hongbao_balance_zlistview.setAdapter(adapter);
			    	}
					else
					{
			    		adapter.refresh(datas);
			    	}
					m_hongbao_balance_zlistview.setLoadMoreSuccess();
				}
				else
				{
					m_hongbao_balance_zlistview.stopLoadMore();
				}
			 }

			 @Override
			 public void onFailure(int errorNo, String strMsg) 
			 {
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				m_hongbao_balance_zlistview.stopLoadMore();
			 }

			 @Override
			 public void onFinish() 
			 {

      	     }
		   });	
 
     }



}

