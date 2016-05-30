package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.widget.AdapterHolder;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.News.NewsItem;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class NewsAdapter extends ZrcAdapter<NewsItem> 
{
	private final KJBitmap kjb = new KJBitmap();
	public NewsAdapter(ZrcListView view, List<NewsItem> mDatas) {
		super(view, mDatas, R.layout.item_news);
	}

	@Override
	public void convert(AdapterHolder helper, NewsItem item, boolean arg2) 
	{
//		helper.setText(R.id.item_news_time, StringUtils.friendlyTime(item.Createtime));
		StringBuffer stringBuffer = new StringBuffer();
		String createtime = item.Createtime;
		stringBuffer.append(createtime.substring(0,createtime.indexOf(" ")));
		stringBuffer.append("  ");
		stringBuffer.append(createtime.substring(createtime.indexOf(" ")+1,createtime.lastIndexOf(":")));
		helper.setText(R.id.item_news_time, stringBuffer.toString());
        helper.setText(R.id.item_news_title_single, item.Title);
//        helper.setText(R.id.item_news_content_single, item.Content);

	   if(!StringUtils.isEmpty(item.ImageUrl))
	   {
		   helper.setImageByUrl(kjb,R.id.item_news_image_single, item.ImageUrl);
	   }
//	   if(!StringUtils.isEmpty(item.Url))
//	   {
//	       helper.getView(R.id.item_news_detail_single).setVisibility(View.VISIBLE);
//	       helper.getView(R.id.item_news_layout_single).setOnClickListener(getItemMessageClickListener(item.Url,item.Title));
//	   }
//	   else
//	   {
//		   helper.getView(R.id.item_news_detail_single).setVisibility(View.GONE);
//	   }
	}
	
    /**
     * 当点击一个标题时，跳转到浏览器显示参数地址
     * 
     * @param url
     * 要显示的url
     * @return 点击事件监听器
     */
//    private OnClickListener getItemMessageClickListener(final String url,final String title)
//    {
//        return new OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                UIHelper.toBrowser(NewsAdapter.this.mCxt, url,title);
//            }
//        };
//    }
}