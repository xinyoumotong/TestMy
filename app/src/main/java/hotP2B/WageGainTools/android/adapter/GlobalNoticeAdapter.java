package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import android.view.View;
import android.view.View.OnClickListener;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.Notice.NoticeItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;
import hotP2B.WageGainTools.android.utils.UIHelper;

public class GlobalNoticeAdapter extends ZrcAdapter<NoticeItem>  
{
	public GlobalNoticeAdapter(ZrcListView view, List<NoticeItem> mDatas) {
		super(view, mDatas, R.layout.item_globalnotice);
	}

	@Override
	public void convert(AdapterHolder helper, NoticeItem item, boolean arg2) 
	{
		 helper.setText(R.id.globalnotice_tv_title,item.getNoticetitle());
		 helper.setText(R.id.globalnotice_tv_time,item.getEffectivetime());
	     helper.setText(R.id.globalnotice_tv_content,item.getNoticecontent());
	     
	     helper.getView(R.id.globalnotice_ll_root).setOnClickListener(getItemMessageClickListener(item.getNoticeurl(),item.getNoticetitle()));
	}

	
	 private OnClickListener getItemMessageClickListener(final String url,final String title) 
    {
        return new OnClickListener() 
        {
            @Override
            public void onClick(View v)
            {
                UIHelper.toBrowser(GlobalNoticeAdapter.this.mCxt, url,title);
            }
        };
    }
}
