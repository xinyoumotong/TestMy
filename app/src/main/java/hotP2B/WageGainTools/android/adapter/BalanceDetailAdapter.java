package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.widget.AbsListView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.response.BalanceResponse.BalanceItem;

public class BalanceDetailAdapter extends KJAdapter<BalanceItem> 
{
    private static final String TYPE_INPUT="1";
    private static final String TYPE_OUTPUT="2";
    
	public BalanceDetailAdapter(AbsListView view, List<BalanceItem> mDatas) {
		super(view, mDatas, R.layout.item_balance_detail);
	}

	@Override
	public void convert(AdapterHolder helper, BalanceItem item, boolean isScrolling) 
	{
	
		helper.setText(R.id.balance_detail_tv_useinfo,item.getUseinfo());
		if(item.getDebittype().equals(TYPE_INPUT))
		{
			helper.setText(R.id.balance_detail_tv_cashAmt,"+"+item.getCashAmt());
		}
		else if(item.getDebittype().equals(TYPE_OUTPUT))
		{
			helper.setText(R.id.balance_detail_tv_cashAmt,"-"+item.getCashAmt());
		}

		helper.setText(R.id.balance_detail_tv_currentbalance,"余额:"+item.getCurrentbalance());
		helper.setText(R.id.balance_detail_tv_cashDate, item.getCashDate());
	}

}
