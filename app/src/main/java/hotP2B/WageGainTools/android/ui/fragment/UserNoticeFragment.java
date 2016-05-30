package hotP2B.WageGainTools.android.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.adapter.UserNoticeAdapter;
import hotP2B.WageGainTools.android.bean.UserNotice;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.utils.HttpUtils;


public class UserNoticeFragment extends TitleBarFragment 
{
	
	@BindView(id = R.id.usernotice_zlistview)
	private ZrcListView m_usernotice_zlistview;
	
	private UserNoticeAdapter adapter=null;
	private List<UserNoticeItem>datas;


	

	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		View view = View.inflate(outsideAty, R.layout.frag_usernotice, null);
	    return view;
	}

	 @Override
	 protected void setActionBarRes(ActionBarRes actionBarRes) 
	 {
		 actionBarRes.title ="通知中心";
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
	   
	   datas=new ArrayList<UserNoticeItem>();
	   
	   m_usernotice_zlistview.setDivider(new ColorDrawable(android.R.color.transparent));
	   m_usernotice_zlistview.setOnRefreshStartListener(new OnStartListener() {
           @Override
           public void onStart() {
               refresh();
           }
       });

	   m_usernotice_zlistview.setOnLoadMoreStartListener(new OnStartListener() {
           @Override
           public void onStart() {
               loadMore();
           }
       });
    
      m_usernotice_zlistview.refresh();
	   
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
    	 
    	 HttpUtils.getAllUserNoticeFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),"0",AppConfig.PAGE_SIZE+"", UserNotice.class, new HttpBaseCallBack<UserNotice>() 
 		 {

 			@Override
 			public void onSuccess(UserNotice response) 
 			{
 				List<UserNoticeItem>list=response.getList();
 				if(list!=null && list.size()>0)
 				{
 					datas.addAll(list);
 					if(adapter==null)
 					{
 			    		adapter = new UserNoticeAdapter(m_usernotice_zlistview, datas);
 			    		m_usernotice_zlistview.setAdapter(adapter);
 			    	}
 					else
 					{
 			    		adapter.refresh(datas);
 			    	}
 					m_usernotice_zlistview.setRefreshSuccess();
 					m_usernotice_zlistview.startLoadMore();
 				}
 				else
 				{
 					m_usernotice_zlistview.setRefreshSuccess("没有数据");
 				}
 			 }

 			 @Override
 			 public void onFailure(int errorNo, String strMsg) 
 			 {
 				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
 				m_usernotice_zlistview.setRefreshFail();
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
   	     
   	     HttpUtils.getAllUserNoticeFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),startnum+"",AppConfig.PAGE_SIZE+"", UserNotice.class, new HttpBaseCallBack<UserNotice>() 
		 {

			@Override
			public void onSuccess(UserNotice response) 
			{
				List<UserNoticeItem>list=response.getList();
				if(list!=null && list.size()>0)
				{
					datas.addAll(list);
					if(adapter==null)
					{
			    		adapter = new UserNoticeAdapter(m_usernotice_zlistview, datas);
			    		m_usernotice_zlistview.setAdapter(adapter);
			    	}
					else
					{
			    		adapter.refresh(datas);
			    	}
					m_usernotice_zlistview.setLoadMoreSuccess();
				}
				else
				{
					m_usernotice_zlistview.stopLoadMore();
				}
			 }

			 @Override
			 public void onFailure(int errorNo, String strMsg) 
			 {
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				m_usernotice_zlistview.stopLoadMore();
			 }

			 @Override
			 public void onFinish() 
			 {

      	     }
		   });	
 
     }



}
