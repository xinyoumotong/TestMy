package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.InvestBidInfo;
import hotP2B.WageGainTools.android.bean.LicaiProduct;
import hotP2B.WageGainTools.android.bean.LicaiProduct.LicaiProductItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.dialog.RingProgressDialog;
import hotP2B.WageGainTools.android.inter.HOTAPI;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppProductDetail extends AppTitleBar   
{

	private ImageView m_product_detail_iv_HeartState;
	private ImageView m_product_detail_iv_ConscienceState;
	private ImageView m_product_detail_iv_RealState;
	private ImageView m_product_detail_iv_CreditState;
	private ImageView m_product_detail_iv_SecurityState;
	private ImageView m_product_detail_iv_Star;
	private ImageView m_product_detail_iv_PaidState;
	
	private TextView m_product_detail_name;
	private TextView m_product_detail_year_interest;
	private TextView m_product_detail_total;
	private TextView m_product_detail_chargingway;
	private TextView m_product_detail_duration;
	private TextView m_product_detail_invest_minmoney;
	private TextView m_product_detail_remainmoney;
	private TextView m_product_detail_remaintime;
	private ProgressBar m_product_detail_progress;
	private TextView m_product_detail_percent;
	private WebView m_product_detail_webview;
	private EmptyLayout m_product_detail_empty_layout;
	private Button m_product_detail_btn_invest;
	
	@BindView(id=R.id.product_detail_loading)
	private EmptyLayout m_product_detail_loading;
	
	
	private String bidproid;
	private String url;
	private boolean bFirst=true;
	private LicaiProductItem productItem;



	@Override
	public void setRootView() 
	{
	   setContentView(R.layout.aty_product_detail);
	}
			
	@Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mImgMenu.setVisibility(View.VISIBLE);
		this.mImgMenu.setImageResource(R.mipmap.titlebar_refresh);
		this.mTvTitle.setText("项目详情");
		
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

	   bidproid=this.getIntent().getStringExtra("bidproid");
	   url=this.getIntent().getStringExtra("url");
	   if(StringUtils.isEmpty(bidproid) || StringUtils.isEmpty(url))
	   {
		   this.finish();
		   return;
	   }
	
	   initControl();
	   initWebView();
	 
	   this.m_product_detail_webview.loadUrl(url);//第一次使用旧的url
	   refresh();

	}
	
	 @SuppressLint("SetJavaScriptEnabled")
     private void initWebView() 
	 {
	     WebSettings webSettings = m_product_detail_webview.getSettings();
	     webSettings.setJavaScriptEnabled(true); 
	     webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
	     webSettings.setAllowFileAccess(true);
	     m_product_detail_webview.setWebChromeClient(new MyWebChromeClient());
	     m_product_detail_webview.setWebViewClient(new MyWebViewClient());
	     m_product_detail_webview.addJavascriptInterface(new HOTAPI(this),"HOTAPI");
	 }
	 
	 private void initControl()
	 {
		 m_product_detail_loading.setOnLayoutClickListener(new OnClickListener() 
		 {
			@Override
			public void onClick(View v) 
			{
				bFirst=false;
				refresh();
			}
		 });
		 
		 m_product_detail_iv_HeartState=(ImageView)this.findViewById(R.id.product_detail_iv_HeartState);
		 m_product_detail_iv_ConscienceState=(ImageView)this.findViewById(R.id.product_detail_iv_ConscienceState);
		 m_product_detail_iv_RealState=(ImageView)this.findViewById(R.id.product_detail_iv_RealState);
		 m_product_detail_iv_CreditState=(ImageView)this.findViewById(R.id.product_detail_iv_CreditState);
		 m_product_detail_iv_SecurityState=(ImageView)this.findViewById(R.id.product_detail_iv_SecurityState);
		 m_product_detail_iv_Star=(ImageView)this.findViewById(R.id.product_detail_iv_Star);
		 m_product_detail_iv_PaidState=(ImageView)this.findViewById(R.id.product_detail_iv_PaidState);
		 m_product_detail_name=(TextView)this.findViewById(R.id.product_detail_name);
		 m_product_detail_year_interest=(TextView)this.findViewById(R.id.product_detail_year_interest);
		 m_product_detail_total=(TextView)this.findViewById(R.id.product_detail_total);
		 m_product_detail_chargingway=(TextView)this.findViewById(R.id.product_detail_chargingway);
		 m_product_detail_duration=(TextView)this.findViewById(R.id.product_detail_duration);
		 m_product_detail_invest_minmoney=(TextView)this.findViewById(R.id.product_detail_invest_minmoney);
		 m_product_detail_remainmoney=(TextView)this.findViewById(R.id.product_detail_remainmoney);
		 m_product_detail_remaintime=(TextView)this.findViewById(R.id.product_detail_remaintime);
//		 m_product_detail_progress=(ProgressBar)this.findViewById(R.id.product_detail_progress);
		 m_product_detail_percent=(TextView)this.findViewById(R.id.product_detail_percent);	
		 m_product_detail_webview=(WebView)this.findViewById(R.id.product_detail_webview);
		 m_product_detail_empty_layout=(EmptyLayout)this.findViewById(R.id.product_detail_empty_layout);
	
		 m_product_detail_btn_invest=(Button)this.findViewById(R.id.product_detail_btn_invest);	
		 m_product_detail_btn_invest.setOnClickListener(this);

	 }
	 
	 @SuppressWarnings("deprecation")
	private void fillPrdouctInfo(LicaiProductItem item)
	 {
		this.m_product_detail_btn_invest.setEnabled(false);
		
		if(!bFirst || (!item.getFurl().equals(url)))
		{
			this.m_product_detail_webview.loadUrl(item.getFurl());
		}
		
		if(bFirst)
		{
			this.bFirst=false;
		}
		
	    this.m_product_detail_name.setText(productItem.getFtitle());
		this.m_product_detail_percent.setText(item.getFinancingRate()+"%");
//		this.m_product_detail_progress.setProgress((int)item.getFinancingRate());
		if(item.getHeartState()==1)
		{
			this.m_product_detail_iv_HeartState.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_product_detail_iv_HeartState.setVisibility(View.GONE);
		}
		if(item.getConscienceState()==1)
		{
			this.m_product_detail_iv_ConscienceState.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_product_detail_iv_ConscienceState.setVisibility(View.GONE);
		}
		if(item.getRealState()==1)
		{
			this.m_product_detail_iv_RealState.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_product_detail_iv_RealState.setVisibility(View.GONE);
		}
		
		if(item.getCreditState()==1)
		{
			this.m_product_detail_iv_CreditState.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_product_detail_iv_CreditState.setVisibility(View.GONE);
		}
		if(item.getSecurityState()==1)
		{
			this.m_product_detail_iv_SecurityState.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_product_detail_iv_SecurityState.setVisibility(View.GONE);
		}
		switch(item.getStarLevel())
		{
		case 1:
			this.m_product_detail_iv_Star.setImageResource(R.mipmap.icon_1a);
			break;
		case 2:
			this.m_product_detail_iv_Star.setImageResource(R.mipmap.icon_2a);
			break;
		case 3:
			this.m_product_detail_iv_Star.setImageResource(R.mipmap.icon_3a);
			break;
		case 4:
			this.m_product_detail_iv_Star.setImageResource(R.mipmap.icon_4a);
			break;
		case 5:
			this.m_product_detail_iv_Star.setImageResource(R.mipmap.icon_5a);
			break;
		}
	
		switch(item.getPaidState())
		{
		case AppConfig.BID_STATE_DISCARD://已流标
			this.m_product_detail_iv_PaidState.setImageResource(R.mipmap.icon_invest_state_discard);
			this.m_product_detail_percent.setTextColor(Color.parseColor("#cccccc"));
//			this.m_product_detail_progress.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_gray));
			break;
		
		case AppConfig.BID_STATE_OK://回款成功
			this.m_product_detail_iv_PaidState.setImageResource(R.mipmap.icon_invest_state_ok);
			this.m_product_detail_percent.setTextColor(Color.parseColor("#7acc96"));
//			this.m_product_detail_progress.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_green));
			break;
		case AppConfig.BID_STATE_RETURN://回款中
			this.m_product_detail_iv_PaidState.setImageResource(R.mipmap.icon_invest_state_return);
			this.m_product_detail_percent.setTextColor(Color.parseColor("#7aa3cc"));
//			this.m_product_detail_progress.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_blue));
			break;
		case AppConfig.BID_STATE_ONGOING://投标中
			if(((int)item.getFinancingRate())>=100)
			{
				this.m_product_detail_iv_PaidState.setImageResource(R.mipmap.icon_invest_state_full);
				this.m_product_detail_percent.setTextColor(Color.parseColor("#e5ac73"));
//				this.m_product_detail_progress.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_yellow));
			}
			else
			{
			  this.m_product_detail_iv_PaidState.setImageResource(R.mipmap.icon_invest_state_ongoing);
			  this.m_product_detail_percent.setTextColor(Color.parseColor("#e63054"));
//			  this.m_product_detail_progress.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_red));
			}
			break;
		}
		
		this.m_product_detail_invest_minmoney.setText(item.getInvestmentAmount()+"元");
		this.m_product_detail_year_interest.setText(item.getExpected_Annual_Rate()+"%");
		this.m_product_detail_total.setText(CommonUtils.convertLicaiTotal(item.getProject_Total()));
		this.m_product_detail_chargingway.setText(CommonUtils.convertLicaiChargingway(item.getChargingway()));
		this.m_product_detail_duration.setText(item.getProject_Duration());
		this.m_product_detail_remainmoney.setText(CommonUtils.convertLicaiRemainMoney(item.getProject_Total(), item.getPaidMoney())+"元");
		this.m_product_detail_remaintime.setText(CommonUtils.convertLicaiRemainTime(item.getExpiredate())+"天");
	
		if(productItem!=null && (productItem.getPaidState()==AppConfig.BID_STATE_ONGOING) && (productItem.getFinancingRate()<100))
    	{
    	   m_product_detail_btn_invest.setEnabled(true);
    	}
	 }
	 
	
	 private void refresh()
	 {
		if(StringUtils.isEmpty(this.bidproid))return;
		m_product_detail_loading.setErrorType(EmptyLayout.NETWORK_LOADING);
		HttpUtils.getInvestBidInfo(this,this.bidproid,LicaiProduct.LicaiProductItem.class,new HttpBaseCallBack<LicaiProduct.LicaiProductItem>()
		{
			@Override
			public void onSuccess(LicaiProductItem response) 
			{
			   productItem=response;
			   fillPrdouctInfo(productItem);
			   m_product_detail_loading.dismiss();
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取标的信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
				m_product_detail_loading.setErrorType(EmptyLayout.NETWORK_ERROR);
			}

			@Override
			public void onFinish() 
			{
				
			}
			
		});
	 }
	 
	 private void refresh2()
	 {
		 if(StringUtils.isEmpty(this.bidproid))return;
		 final RingProgressDialog dialog=new RingProgressDialog(this,"正在刷新,请稍等...");
		 dialog.setCancelable(false);
	     dialog.show();
	  
	     HttpUtils.getInvestBidInfo(this,this.bidproid,LicaiProduct.LicaiProductItem.class,new HttpBaseCallBack<LicaiProduct.LicaiProductItem>()
		 {
			@Override
			public void onSuccess(LicaiProductItem response) 
			{
			   productItem=response;
			   fillPrdouctInfo(productItem);
			   ViewInject.toast("刷新成功");
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("刷新失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}
			
		});
 }

	 private class MyWebChromeClient extends WebChromeClient 
	 {
		   @Override
	       public void onProgressChanged(WebView view, int newProgress) 
		   { 
	           super.onProgressChanged(view, newProgress);
	           if (newProgress > 98) 
	           {
	             m_product_detail_empty_layout.dismiss();
	           }
	       }
	 }
	 private class MyWebViewClient extends WebViewClient 
	 {
		  @SuppressWarnings("deprecation")
		  @Override
	      public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) 
		  {
	         super.onReceivedError(view, errorCode, description, failingUrl);
	         ViewInject.toast("没有找到数据");
	         m_product_detail_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
	         m_product_detail_btn_invest.setEnabled(false);
	      }
	 }
	 
	 @Override
	 public void onBackClick() 
	 {
		super.onBackClick();
		this.finish();	
	 }
	 
	 @Override
	 protected void onMenuClick() 
	 {
	    super.onMenuClick();
	    refresh2();
	 }
	 
	 @Override
 	 public void widgetClick(View v) 
     {
 		super.widgetClick(v);
 		switch (v.getId()) 
 		{
 		  case R.id.product_detail_btn_invest:
 		  {
 			 onInvest();
 		  }
 		  break;
 		  default:
 		  break;
 		}
     }

	private void onInvest() 
	{
		if(productItem==null)return;
		if(AppContext.m_CurrentAccount==null)return;
		if(AppContext.IsNewInvestors==0 && (productItem.getNewBidState()==1))
		{
			ViewInject.longToast("你已投过新手标,不可再投");
			return;
		}
		
		final CustomProgressDialog dialog=new CustomProgressDialog(this.aty,"正在获取数据,请稍等...",false);
		dialog.show();
		HttpUtils.getInvestBidCheck(this.aty,AppContext.m_CurrentAccount.getUserpkid(), productItem.getBidProId(),InvestBidInfo.class, new HttpBaseCallBack<InvestBidInfo>()
		{
			@Override
			public void onSuccess(InvestBidInfo response) 
			{
				doBid(response);
			}
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);		
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();	
			}
		 });
		

	}
	
	private void doBid(InvestBidInfo bidinfo)
	{
		if(bidinfo==null)return;
		if(productItem==null)return;
		productItem.setChargingway(bidinfo.getChargingway());
		
        Intent intent=new Intent(AppConfig.BALANCE_BROADCAST_ACTION);//更新可用余额
        intent.putExtra("balance",bidinfo.getUserTransAmt());
		this.sendBroadcast(intent);
		
		productItem.setRemainMoney(bidinfo.getBidTransAmt());
		productItem.setChargingway(bidinfo.getChargingway());
		productItem.setInvestmentAmount(bidinfo.getInvestmentAmount());
		Bundle bundle=new Bundle();
		bundle.putSerializable("productItem", productItem);
		skipActivity(AppProductDetail.this, AppInvest.class,bundle);		
	}

}
