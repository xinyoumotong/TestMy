package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.widget.ListView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.InvestHistory2.InvestHistory2Item;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class InvestHistory2Adapter extends ZrcAdapter<InvestHistory2Item> {

	public InvestHistory2Adapter(ZrcListView view, List<InvestHistory2Item> mDatas)
	{
		super(view, mDatas, R.layout.item_invest_history2);
	}

	@Override
	public void convert(AdapterHolder helper, InvestHistory2Item item, boolean isScrolling) 
	{
		helper.setText(R.id.item_invest_history2_BidName,item.getBidName());
//		helper.setText(R.id.item_invest_history2_CreateTime,item.getCreateTime());
//		helper.setText(R.id.item_invest_history2_EndTime,item.getEndTime());
//		helper.setText(R.id.item_invest_history2_InteRest, item.getInteRest()+"元");
//		helper.setText(R.id.item_invest_history2_TransAmt,item.getTransAmt()+"元");
//		helper.setText(R.id.item_invest_history2_AlreadyCollection,item.getAlreadyCollection()+"元");
//		helper.setText(R.id.item_invest_history2_AlreadyInteRest,item.getAlreadyInteRest()+"元");
//		helper.setText(R.id.item_invest_history2_WaitingCollection,item.getWaitingCollection()+"元");
//		helper.setText(R.id.item_invest_history2_WaitingInteRest,item.getWaitingInteRest()+"元");

		helper.setText(R.id.item_invest_history2_CreateTime,item.getCreateTime().substring(0,item.getCreateTime().indexOf(" ")));
		helper.setText(R.id.item_invest_history2_EndTime,item.getEndTime().substring(0,item.getEndTime().indexOf(" ")));
		helper.setText(R.id.item_invest_history2_InteRest, item.getInteRest());
		helper.setText(R.id.item_invest_history2_TransAmt,item.getTransAmt());
		helper.setText(R.id.item_invest_history2_AlreadyCollection,item.getAlreadyCollection());
		helper.setText(R.id.item_invest_history2_AlreadyInteRest,item.getAlreadyInteRest());
		helper.setText(R.id.item_invest_history2_WaitingCollection,item.getWaitingCollection());
		helper.setText(R.id.item_invest_history2_WaitingInteRest,item.getWaitingInteRest());
	}
}
