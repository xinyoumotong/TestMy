package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class UserNoticeAdapter extends ZrcAdapter<UserNoticeItem>  
{
	public UserNoticeAdapter(ZrcListView view, List<UserNoticeItem> mDatas) {
		super(view, mDatas, R.layout.item_usernotice);
	}

	@Override
	public void convert(AdapterHolder helper, UserNoticeItem item, boolean isScrolling) 
	{
	   helper.setText(R.id.usernotice_tv_title,item.getUsernoticetitle());
	   helper.setText(R.id.usernotice_tv_time,item.getCreatetime());
	   helper.setText(R.id.usernotice_tv_content,item.getUsernoticecontent());
	}
	

}
