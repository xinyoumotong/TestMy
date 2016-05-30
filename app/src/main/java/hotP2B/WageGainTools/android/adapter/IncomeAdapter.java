package hotP2B.WageGainTools.android.adapter;

import java.text.DecimalFormat;
import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.response.IncomeResponse.IncomeItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.utils.CommonUtils;

public class IncomeAdapter extends ZrcAdapter<IncomeItem> {

	public IncomeAdapter(ZrcListView view, List<IncomeItem> mDatas) {
		super(view, mDatas, R.layout.item_income);
	}

	@Override
	public void convert(AdapterHolder helper, IncomeItem item, boolean isScrolling) 
	{
		String item_income_tv_amount = CommonUtils.keep2Decimal(item.getProfitAmount());

		helper.setText(R.id.item_income_tv_time ,item.getProfitTime());
		helper.setText(R.id.item_income_tv_name, item.getProfitReMark());
		helper.setText(R.id.item_income_tv_amount, item_income_tv_amount);
	}
}
