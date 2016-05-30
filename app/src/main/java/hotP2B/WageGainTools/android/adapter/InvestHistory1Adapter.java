package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.widget.ListView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.InvestHistory1.InvestHistory1Item;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class InvestHistory1Adapter extends ZrcAdapter<InvestHistory1Item> {

	public InvestHistory1Adapter(ZrcListView view, List<InvestHistory1Item> mDatas)
	{
		super(view, mDatas, R.layout.item_invest_history1);
	}

	@Override
	public void convert(AdapterHolder helper, InvestHistory1Item item, boolean isScrolling) 
	{
		helper.setText(R.id.item_invest_history1_BidName,item.getBidName());
		helper.setText(R.id.item_invest_history1_CreateTime,item.getCreateTime().substring(0,item.getCreateTime().indexOf(" ")));
//		helper.setText(R.id.item_invest_history1_TransAmt,item.getTransAmt()+"元");
		helper.setText(R.id.item_invest_history1_TransAmt,item.getTransAmt());
//		helper.setText(R.id.item_invest_history1_lixi2,item.getLixi2()+"%");
		helper.setText(R.id.item_invest_history1_lixi2,item.getLixi2());
	}
}