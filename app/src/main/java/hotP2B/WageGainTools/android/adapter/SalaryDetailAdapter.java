package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.graphics.Color;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.Salary.SalaryDetailItem;
import hotP2B.WageGainTools.android.utils.CommonUtils;


public class SalaryDetailAdapter extends KJAdapter<SalaryDetailItem> {

	public SalaryDetailAdapter(GridView view, List<SalaryDetailItem> mDatas) {
		super(view, mDatas, R.layout.item_salary_detail);
	}

	@Override
	public void convert(AdapterHolder helper, SalaryDetailItem item, boolean isScrolling) 
	{
		helper.setText(R.id.item_salary_detail_tv_itemName,item.getSinglename());
		TextView tv_money=(TextView)helper.getView(R.id.item_salary_detail_tv_itemMoney);
		if(item.getSingletype()==1)
		{
			tv_money.setText("-"+ CommonUtils.keep2Decimal(item.getSingleamount()));
		}
		else
		{
			tv_money.setText("+"+CommonUtils.keep2Decimal(item.getSingleamount()));
		}
	}

}
