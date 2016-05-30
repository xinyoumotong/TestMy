
package hotP2B.WageGainTools.android.adapter;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SupportBank;
import hotP2B.WageGainTools.android.bean.response.BankCardResponse.BankCardItem;
import hotP2B.WageGainTools.android.ui.widget.swipemenulistview.BaseSwipListAdapter;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class BankCardAdapter extends BaseSwipListAdapter {

	private List<BankCardItem> listData;
	private Context context;
	private boolean IsDefault = false;

	public BankCardAdapter(Context context, List<BankCardItem> listData) {
		super();
		this.context = context;
		this.listData = listData;

	}

	public void refresh(List<BankCardItem> list) {
		listData=list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public BankCardItem getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_bankcard, null);
			holder = new ViewHolder(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BankCardItem item = listData.get(position);
		String cardId = item.OpenAcctId.trim();
		cardId = cardId.substring(cardId.length() - 4, cardId.length());
		if (item.IsDefault.equals("Y"))
		{
			holder.item_bankcard_iv_identify_logo.setImageResource(R.mipmap.identify);
			holder.item_bankcard_iv_identify_logo.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.item_bankcard_iv_identify_logo.setVisibility(View.GONE);
		}
		holder.item_bankcard_iv_bank_logo.setImageResource(SupportBank.getLogoByNick(item.OpenBankId));
		holder.item_bankcard_tv_bank_name.setText(SupportBank.getNameByNick(item.OpenBankId));
		holder.item_bankcard_iv_bank_logo.setImageResource(SupportBank.getLogoByNick(item.OpenBankId));
		holder.item_bankcard_tv_bank_name.setText(SupportBank.getNameByNick(item.OpenBankId));
		holder.item_bankcard_tv_bank_cardNumber.setText("尾号" + cardId);

		return convertView;
	}

	private static class ViewHolder {
		ImageView item_bankcard_iv_bank_logo;
		TextView item_bankcard_tv_bank_name;
		TextView item_bankcard_tv_bank_cardNumber;
		ImageView item_bankcard_iv_identify_logo;

		public ViewHolder(View view) {
			item_bankcard_iv_bank_logo = (ImageView) view.findViewById(R.id.item_bankcard_iv_bank_logo);
			item_bankcard_tv_bank_name = (TextView) view.findViewById(R.id.item_bankcard_tv_bank_name);
			item_bankcard_tv_bank_cardNumber = (TextView) view.findViewById(R.id.item_bankcard_tv_bank_cardNumber);
			item_bankcard_iv_identify_logo = (ImageView) view.findViewById(R.id.item_bankcard_iv_identify_logo);
			view.setTag(this);
		}
	}
}