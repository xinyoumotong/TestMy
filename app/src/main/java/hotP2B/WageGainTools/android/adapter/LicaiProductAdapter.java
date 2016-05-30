package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;

import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.LicaiProduct.LicaiProductItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.utils.CommonUtils;

public class LicaiProductAdapter extends ZrcAdapter<LicaiProductItem>
{

	public LicaiProductAdapter(ZrcListView view, List<LicaiProductItem> mDatas) 
	{
		super(view, mDatas, R.layout.item_product);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void convert(AdapterHolder helper, LicaiProductItem item, boolean isScrolling) 
	{
		helper.setText(R.id.product_name, item.getFtitle());
		helper.setText(R.id.product_name_newbid, item.getFtitle());
		helper.setText(R.id.product_year_interest, item.getExpected_Annual_Rate()+"%");
		helper.setText(R.id.product_duration, item.getProject_Duration());
		helper.setText(R.id.product_total,CommonUtils.convertLicaiTotal(item.getProject_Total()));
		ProgressBar progressBar = (ProgressBar)helper.getView(R.id.product_progress);
		progressBar.setProgress((int)item.getFinancingRate());
		TextView tv_percent=(TextView)helper.getView(R.id.product_percent);
		tv_percent.setText(item.getFinancingRate()+"%");

		
		switch(item.getPaidState())
		{

		case AppConfig.BID_STATE_DISCARD://已流标
			helper.setImageResource(R.id.product_PaidState, R.mipmap.icon_invest_state_discard);
			tv_percent.setTextColor(Color.parseColor("#cccccc"));
			progressBar.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_gray));
			break;
		
		case AppConfig.BID_STATE_OK://回款成功
			helper.setImageResource(R.id.product_PaidState, R.mipmap.icon_invest_state_ok);
			tv_percent.setTextColor(Color.parseColor("#7acc96"));
			progressBar.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_green));
			break;
		case AppConfig.BID_STATE_RETURN://回款中
			helper.setImageResource(R.id.product_PaidState, R.mipmap.icon_invest_state_return);
			tv_percent.setTextColor(Color.parseColor("#7aa3cc"));
			progressBar.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_blue));
			break;
			
		case AppConfig.BID_STATE_ONGOING://投标中
			if((int)item.getFinancingRate()>=100)//满标
			{
				helper.setImageResource(R.id.product_PaidState, R.mipmap.icon_invest_state_full);
				tv_percent.setTextColor(Color.parseColor("#e5ac73"));
				progressBar.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_yellow));
			}
			else
			{
			  helper.setImageResource(R.id.product_PaidState, R.mipmap.icon_invest_state_ongoing);//投资中
			  tv_percent.setTextColor(Color.parseColor("#e63054"));
			  progressBar.setProgressDrawable(AppContext.application.getResources().getDrawable(R.drawable.progressbar_red));
			}
		
			
			break;
		}
		
		if(item.getHeartState()==1)
		{
			helper.getView(R.id.product_iv_HeartState).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_iv_HeartState).setVisibility(View.GONE);
		}
		if(item.getConscienceState()==1)
		{
			helper.getView(R.id.product_iv_ConscienceState).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_iv_ConscienceState).setVisibility(View.GONE);
		}
		if(item.getRealState()==1)
		{
			helper.getView(R.id.product_iv_RealState).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_iv_RealState).setVisibility(View.GONE);
		}
		
		if(item.getCreditState()==1)
		{
			helper.getView(R.id.product_iv_CreditState).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_iv_CreditState).setVisibility(View.GONE);
		}
		if(item.getSecurityState()==1)
		{
			helper.getView(R.id.product_iv_SecurityState).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_iv_SecurityState).setVisibility(View.GONE);
		}
		
		if(item.getNewBidState()==1)
		{
			helper.getView(R.id.product_ll_title).setVisibility(View.GONE);
			helper.getView(R.id.product_ll_newbid_title).setVisibility(View.VISIBLE);
		}
		else
		{
			helper.getView(R.id.product_ll_title).setVisibility(View.VISIBLE);
			helper.getView(R.id.product_ll_newbid_title).setVisibility(View.GONE);
		}
	 
	}


}
