package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;

import android.graphics.Color;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.HongbaoIncome.HongbaoIncomeItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;


public class HongbaoIncomeAdapter extends ZrcAdapter<HongbaoIncomeItem>  
{
	public HongbaoIncomeAdapter(ZrcListView view, List<HongbaoIncomeItem> mDatas) {
		super(view, mDatas, R.layout.item_hongbao_income);
	}

	@Override
	public void convert(AdapterHolder helper, HongbaoIncomeItem item, boolean isScrolling) 
	{
	   helper.setText(R.id.hongbao_income_tv_time,item.getInvesttime());
	   TextView tv_money=(TextView)helper.getView(R.id.hongbao_income_tv_money);
	   TextView tv_content=(TextView)helper.getView(R.id.hongbao_income_tv_content);
	   if(item.getRecordedstate()==0)
	   {
		   tv_content.setText(item.getBenefitcontent()+"-待入账");
		   tv_content.setTextColor(Color.parseColor("#eb9c2f"));
		   tv_money.setTextColor(Color.parseColor("#eb9c2f"));
	   }
	   else 
	   {
		   tv_content.setText(item.getBenefitcontent()+"-已入账");
	   }
	   tv_money.setText("+"+item.getInvestamount());

	}

}
