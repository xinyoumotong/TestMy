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
import android.view.View.OnClickListener;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView.OnStartListener;
import hotP2B.WageGainTools.android.adapter.NewsAdapter;
import hotP2B.WageGainTools.android.bean.News;
import hotP2B.WageGainTools.android.bean.News.NewsItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UIHelper;

/**
 * 
 *平台资讯页面
 *
 */
public class NewsFragment extends TitleBarFragment 
{
	 private static final String type="1";
	 private static final String start="0";

	 //列表
	 @BindView(id = R.id.news_zlistview)
	 private ZrcListView m_news_zlistview;
	 
	 //适配器
	 private NewsAdapter adapter=null;
	 
	 @BindView(id = R.id.news_empty_layout)
	 private EmptyLayout m_news_empty_layout;
	private List<NewsItem> list = new ArrayList<>();

	@Override
	 protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) 
	 {
	      View root = View.inflate(outsideAty, R.layout.frag_news, null);
	      return root;
	 }

	 @Override
	 protected void setActionBarRes(ActionBarRes actionBarRes) 
	 {
	     super.setActionBarRes(actionBarRes);
	     actionBarRes.backImageId = R.mipmap.titlebar_back;
	     actionBarRes.title="平台资讯";
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
	

	      m_news_empty_layout.setOnLayoutClickListener(new OnClickListener() 
	      {
			 @Override
			public void onClick(View v) 
			{
				refresh();
			}
		   });
	      
	       m_news_zlistview.setDivider(new ColorDrawable(android.R.color.transparent));
	       m_news_zlistview.setOnRefreshStartListener(new OnStartListener() 
	        {
	            @Override
	            public void onStart() 
	            {
	                refresh2();
	            }

	        });

		 m_news_zlistview.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			 @Override
			 public void onItemClick(ZrcListView parent, View view, int position, long id) {
				 NewsItem newsItem = list.get(position);
				 if (newsItem != null && newsItem.Url != null) {
					 UIHelper.toBrowser(NewsFragment.this.outsideAty, newsItem.Url,newsItem.Title == null ? "" : newsItem.Title);
				 }
			 }
		 });

	
		   refresh();// 刷新数据
	 }

	 @Override
	 public void onBackClick() 
	 {
	     super.onBackClick();
	     outsideAty.finish();
	 }
	 
	@Override
	public void onMenuClick() 
	{
		super.onMenuClick();
	}

    private void refresh() 
    {
        m_news_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
    	HttpUtils.getFindInfoFromServer(this.outsideAty, type,start,AppConfig.PAGE_SIZE+"",News.class,new HttpBaseCallBack<News>()
    	{
			@Override
			public void onSuccess(News news) 
			{
				 list=news.getData();
				 if(list!=null && list.size()>0)
				 {
		
					 if (adapter == null)
					 {
	                     adapter = new NewsAdapter(m_news_zlistview, list);
	                     m_news_zlistview.setAdapter(adapter);
	                 } 
					 else 
					 {
	                     adapter.refresh(list);
	                 }
					 m_news_empty_layout.dismiss();
				 }
				 else
				 {
					 m_news_empty_layout.setErrorType(EmptyLayout.NODATA);
				 }
				
			}
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取平台资讯信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg); 
				m_news_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
			}

			@Override
			public void onFinish() 
			{
				m_news_empty_layout.dismiss();
			}
    		
    	});
    	
    }
    
    private void refresh2() 
    {
    	HttpUtils.getFindInfoFromServer(this.outsideAty, type,start,AppConfig.PAGE_SIZE+"",News.class,new HttpBaseCallBack<News>()
    	{
			@Override
			public void onSuccess(News news) 
			{
				 List<NewsItem> list=news.getData();
				 if(list!=null && list.size()>0)
				 {
		
					 if (adapter == null)
					 {
	                     adapter = new NewsAdapter(m_news_zlistview, list);
	                     m_news_zlistview.setAdapter(adapter);
	                 } 
					 else 
					 {
	                     adapter.refresh(list);
	                 }
					 m_news_zlistview.setRefreshSuccess();
	
				 }
				 else
				 {
					 m_news_zlistview.setRefreshSuccess("没有新的数据");
				 }
				
			}
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取平台资讯信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg); 
				m_news_zlistview.setRefreshFail();
			}

			@Override
			public void onFinish() 
			{
			}
    	});
    }
}
