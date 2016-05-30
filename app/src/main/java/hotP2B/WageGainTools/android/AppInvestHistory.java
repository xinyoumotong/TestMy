//package hotP2B.WageGainTools.android;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.kymjs.kjframe.ui.BindView;
//import org.kymjs.kjframe.ui.ViewInject;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.drawable.ColorDrawable;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import hotP2B.WageGainTools.android.adapter.InvestHistory1Adapter;
//import hotP2B.WageGainTools.android.adapter.InvestHistory2Adapter;
//import hotP2B.WageGainTools.android.adapter.InvestHistory3Adapter;
//import hotP2B.WageGainTools.android.bean.InvestHistory1;
//import hotP2B.WageGainTools.android.bean.InvestHistory1.InvestHistory1Item;
//import hotP2B.WageGainTools.android.bean.InvestHistory2;
//import hotP2B.WageGainTools.android.bean.InvestHistory2.InvestHistory2Item;
//import hotP2B.WageGainTools.android.bean.InvestHistory3;
//import hotP2B.WageGainTools.android.bean.InvestHistory3.InvestHistory3Item;
//import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
//import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
//import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase;
//import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshList;
//import hotP2B.WageGainTools.android.ui.widget.listview.PullToRefreshBase.OnRefreshListener;
//import hotP2B.WageGainTools.android.utils.CommonUtils;
//import hotP2B.WageGainTools.android.utils.HttpUtils;
//
//public class AppInvestHistory extends AppTitleBar
//{
//	@BindView(id = R.id.invest_history_rb_bar1,click=true)
//	private RadioButton m_invest_history_rb_bar1;
//	@BindView(id = R.id.invest_history_rb_bar2,click=true)
//	private RadioButton m_invest_history_rb_bar2;
//	@BindView(id = R.id.invest_history_rb_bar3,click=true)
//	private RadioButton m_invest_history_rb_bar3;
//
//	@BindView(id = R.id.invest_history_swiperefreshlayout)
//	private PullToRefreshList m_invest_history_swiperefreshlayout;
//	@BindView(id = R.id.invest_history_empty_layout)
//	private EmptyLayout m_invest_history_empty_layout;
//
//	private ListView m_List;
//	private int type=0;
//
//	private InvestHistory1Adapter adapter1=null;
//	private List<InvestHistory1Item> data1;
//
//
//	private InvestHistory2Adapter adapter2=null;
//	private List<InvestHistory2Item> data2;
//
//	private InvestHistory3Adapter adapter3=null;
//	private List<InvestHistory3Item> data3;
//
//	private FlowReceiver flowReceiver=null;
//
//	@Override
//	public void setRootView()
//	{
//		this.setContentView(R.layout.aty_invest_history);
//	}
//
//	@Override
//    public void initData()
//    {
//        super.initData();
//    }
//
//
//    @Override
//	public void initTitleBar()
//	{
//	  this.mImgBack.setVisibility(View.VISIBLE);
//	  this.mTvTitle.setText("投资记录");
//
//	}
//
//    @Override
//    public void initWidget()
//    {
//        super.initWidget();
//        data1=new ArrayList<InvestHistory1Item>();
//        data2=new ArrayList<InvestHistory2Item>();
//        data3=new ArrayList<InvestHistory3Item>();
//
//    	m_List = m_invest_history_swiperefreshlayout.getRefreshView();
//    	m_List.setDivider(new ColorDrawable(android.R.color.transparent));
//    	m_List.setOverscrollFooter(null);
//    	m_List.setOverscrollHeader(null);
//    	m_List.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
//    	m_invest_history_swiperefreshlayout.setPullLoadEnabled(true);
//        m_invest_history_empty_layout.setOnLayoutClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//			    m_invest_history_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
//				refresh();
//			}
//		});
//
//        m_invest_history_swiperefreshlayout.setOnRefreshListener(new OnRefreshListener<ListView>()
//        {
//  	         @Override
//  	         public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView)
//  	         {
//
//  	         	refresh();
//  	         }
//
//			@Override
//			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
//			{
//				pullup();
//			}
//
//
//        });
//
//        flowReceiver=new FlowReceiver();
//	 	IntentFilter filterFlow=new IntentFilter();
//	 	filterFlow.addAction(AppConfig.FLOW_BROADCAST_ACTION);
//    	this.registerReceiver(flowReceiver, filterFlow);
//
//        refresh();
//    }
//
//    private class FlowReceiver extends BroadcastReceiver
//	{
//		@Override
//		public void onReceive(Context context, Intent intent)
//		{
//			if(intent.getAction().equals(AppConfig.FLOW_BROADCAST_ACTION))
//			{
//				 refresh();
//			}
//		}
//	}
//
//    private void refresh()
//    {
//    	data1.clear();
//    	data2.clear();
//    	data3.clear();
//    	switch(this.type)
//    	{
//    	case 0:
//    		refresh1(0,false);
//    		break;
//    	case 1:
//    		refresh2(0,false);
//    		break;
//    	case 2:
//    		refresh3(0,false);
//    		break;
//    	 default:
//    		break;
//    	}
//    }
//	private void pullup()
//	{
//		int startNum=0;
//		switch(this.type)
//    	{
//    	case 0:
//    	{
//    		if(this.data1.size()%AppConfig.PAGE_SIZE==0)
//    		{
//    			startNum=this.data1.size()/AppConfig.PAGE_SIZE;
//    		}
//    		else
//    		{
//    			startNum=this.data1.size()/AppConfig.PAGE_SIZE+1;
//    		}
//    		refresh1(startNum,true);
//    	}
//    	break;
//    	case 1:
//    	{
//    		if(this.data2.size()%AppConfig.PAGE_SIZE==0)
//    		{
//    			startNum=this.data2.size()/AppConfig.PAGE_SIZE;
//    		}
//    		else
//    		{
//    			startNum=this.data2.size()/AppConfig.PAGE_SIZE+1;
//    		}
//    		refresh2(startNum,true);
//    	}
//    	break;
//    	case 2:
//    	{
//    		if(this.data3.size()%AppConfig.PAGE_SIZE==0)
//    		{
//    			startNum=this.data3.size()/AppConfig.PAGE_SIZE;
//    		}
//    		else
//    		{
//    			startNum=this.data3.size()/AppConfig.PAGE_SIZE+1;
//    		}
//    		refresh3(startNum,true);
//    	}
//    	break;
//    	 default:
//    		break;
//    	}
//	}
//
//	private void refresh1(final int startNum,final boolean isPullup)
//	{
//		HttpUtils.getInvestHistory1(this,AppContext.m_CurrentAccount.getUserpkid(),startNum+"",AppConfig.PAGE_SIZE+"",InvestHistory1.class,new HttpBaseCallBack<InvestHistory1>()
//			{
//				@Override
//				public void onSuccess(InvestHistory1 response)
//				{
//					List<InvestHistory1Item> list=response.getData();
//				 if(list!=null && list.size()>0)
//				 {
//					 data1.addAll(list);
//				 }
//
//				if(!isPullup)
//				{
//					adapter1 = new InvestHistory1Adapter(m_List, data1);
//					m_List.setAdapter(adapter1);
//					if(data1.size()>0)
//					{
//			            m_invest_history_empty_layout.dismiss();
//					}
//					else
//					{
//					   m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//				    }
//				}
//				else
//				{
//					adapter1.refresh(data1);
//				}
//
//			}
//
//			@Override
//			public void onFailure(int errorNo, String strMsg)
//			{
//				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
//				 if (adapter1 != null && adapter1.getCount() > 0)
//				 {
//                     return;
//                 }
//				 else
//                 {
//					 m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//                 }
//			}
//
//			@Override
//			public void onFinish()
//			{
//				m_invest_history_swiperefreshlayout.onPullDownRefreshComplete();
//				m_invest_history_swiperefreshlayout.onPullUpRefreshComplete();
//
//			}
//
//		});
//	}
//
//	private void refresh2(final int startNum,final boolean isPullup)
//	{
//		HttpUtils.getInvestHistory2(this,AppContext.m_CurrentAccount.getUserpkid(),startNum+"",AppConfig.PAGE_SIZE+"",InvestHistory2.class,new HttpBaseCallBack<InvestHistory2>()
//		{
//			@Override
//			public void onSuccess(InvestHistory2 response)
//			{
//				 List<InvestHistory2Item> list=response.getData();
//				 if(list!=null && list.size()>0)
//				 {
//					 data2.addAll(list);
//				 }
//
//				if(!isPullup)
//				{
////					adapter2 = new InvestHistory2Adapter(m_List, data2);
//					m_List.setAdapter(adapter2);
//					if(data2.size()>0)
//					{
//			            m_invest_history_empty_layout.dismiss();
//					}
//					else
//					{
//					   m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//				    }
//				}
//				else
//				{
//					adapter2.refresh(data2);
//				}
//
//
//			}
//
//			@Override
//			public void onFailure(int errorNo, String strMsg)
//			{
//				 ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
//				 if (adapter2 != null && adapter2.getCount() > 0)
//				 {
//                     return;
//                 }
//				 else
//                 {
//					 m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//                 }
//			}
//
//			@Override
//			public void onFinish()
//			{
//				m_invest_history_swiperefreshlayout.onPullDownRefreshComplete();
//				m_invest_history_swiperefreshlayout.onPullUpRefreshComplete();
//
//			}
//
//		});
//	}
//
//	private void refresh3(final int startNum,final boolean isPullup)
//	{
//
//		HttpUtils.getInvestHistory3(this,AppContext.m_CurrentAccount.getUserpkid(),startNum+"",AppConfig.PAGE_SIZE+"",InvestHistory3.class,new HttpBaseCallBack<InvestHistory3>()
//		{
//			@Override
//			public void onSuccess(InvestHistory3 response)
//			{
//				 List<InvestHistory3Item> list=response.getData();
//				 if(list!=null && list.size()>0)
//				 {
//					 data3.addAll(list);
//				 }
//
//				if(!isPullup)
//				{
//					adapter3 = new InvestHistory3Adapter(m_List, data3,new InvestHistory3Adapter.FlowBtnClickListener() {
//						@Override
//						public void onClick(InvestHistory3Item item)
//						{
//							doFlowBid(item);
//						}
//					});
//					m_List.setAdapter(adapter3);
//					if(data3.size()>0)
//					{
//			            m_invest_history_empty_layout.dismiss();
//					}
//					else
//					{
//					   m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//				    }
//				}
//				else
//				{
//					adapter3.refresh(data3);
//				}
//			}
//
//			@Override
//			public void onFailure(int errorNo, String strMsg)
//			{
//				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
//				if (adapter3 != null && adapter3.getCount() > 0)
//				{
//                    return;
//                }
//				else
//                {
//					 m_invest_history_empty_layout.setErrorType(EmptyLayout.NODATA);
//                }
//			}
//
//			@Override
//			public void onFinish()
//			{
//				m_invest_history_swiperefreshlayout.onPullDownRefreshComplete();
//			    m_invest_history_swiperefreshlayout.onPullUpRefreshComplete();
//
//			}
//
//		});
//	}
//
//	private void doFlowBid(InvestHistory3Item item)
//	{
//		if(item==null)return;
//		if(AppContext.m_CurrentAccount==null)return;
//		double dMoney;
//		try
//	    {
//	    	dMoney=Double.parseDouble(item.getTransAmt());
//	    }
//	    catch(Exception e)
//	    {
//	    	ViewInject.toast("投资金额不合法");
//	    	return;
//	    }
//		DecimalFormat  df=new DecimalFormat("#.00");
//		String strCash=df.format(dMoney);
//		CommonUtils.flowbid(this.aty,AppContext.m_CurrentAccount.getUserpkid(),item.getOrdId(),strCash, item.getFreezeId(),item.getBidproid());
//
//	}
//
//    @Override
//   	public void onBackClick()
//   	{
//   	    super.onBackClick();
//   		this.finish();
//   	}
//
//    @Override
//	public void widgetClick(View v)
//    {
//		super.widgetClick(v);
//		switch (v.getId())
//		{
//		 case R.id.invest_history_rb_bar1:
//			   changeType(0);
//				break;
//	        case R.id.invest_history_rb_bar2:
//	        	changeType(1);
//	        	break;
//	        case R.id.invest_history_rb_bar3:
//	           changeType(2);
//	        	break;
//	        default:
//	            break;
//
//		}
//    }
//
//	private void changeType(int type)
//	{
//	   if(this.type==type)return;
//	   this.type=type;
//	   m_invest_history_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
//	   refresh();
//	}
//
//}