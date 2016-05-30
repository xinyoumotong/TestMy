package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;

import android.graphics.Color;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.HongbaoBalance.HongbaoBalanceItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class HongbaoBalanceAdapter extends ZrcAdapter<HongbaoBalanceItem>  
{
	public HongbaoBalanceAdapter(ZrcListView view, List<HongbaoBalanceItem> mDatas) {
		super(view, mDatas, R.layout.item_hongbao_balance);
	}

	@Override
	public void convert(AdapterHolder helper, HongbaoBalanceItem item, boolean isScrolling) 
	{
	   helper.setText(R.id.hongbao_balance_tv_time,item.getCreatetime());
	   TextView tv_content=(TextView)helper.getView(R.id.hongbao_balance_tv_content);
	   TextView tv_money=(TextView)helper.getView(R.id.hongbao_balance_tv_money);
	   
	   if(item.getDebittype()==1)
	   {
		   tv_content.setText(item.getChangedesc());
		   tv_content.setTextColor(Color.parseColor("#ff0000"));
		   tv_money.setTextColor(Color.parseColor("#ff0000"));
		   tv_money.setText("-"+item.getUserdebitmoney());
	   }
	   else 
	   {
		   tv_content.setText(item.getChangedesc());
		   tv_money.setText("+"+item.getUserdebitmoney());
	   }

	}
	

}
