package hotP2B.WageGainTools.android.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.kymjs.kjframe.widget.AdapterHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcAbsListView;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public abstract class ZrcAdapter<T> extends BaseAdapter implements
ZrcListView.OnScrollListener {

	protected Collection<T> mDatas;
	protected final int mItemLayoutId;
	protected ZrcListView mList;
	protected boolean isScrolling;
	protected Context mCxt;
	protected LayoutInflater mInflater;
	
	private ZrcListView.OnScrollListener listener;
	
	public ZrcAdapter(ZrcListView view, Collection<T> mDatas, int itemLayoutId) {
	if (mDatas == null) {
	    mDatas = new ArrayList<T>(0);
	}
	this.mDatas = mDatas;
	this.mItemLayoutId = itemLayoutId;
	this.mList = view;
	mCxt = view.getContext();
	mInflater = LayoutInflater.from(mCxt);
	mList.setOnScrollListener(this);
	}
	
	public void refresh(Collection<T> datas) {
	if (datas == null) {
	    datas = new ArrayList<T>(0);
	}
	this.mDatas = datas;
	notifyDataSetChanged();
	}
	
	public void addOnScrollListener(ZrcListView.OnScrollListener l) {
	this.listener = l;
	}
	
	@Override
	public int getCount() {
	return mDatas.size();
	}
	
	@Override
	public T getItem(int position) {
	if (mDatas instanceof List) {
	    return ((List<T>) mDatas).get(position);
	} else if (mDatas instanceof Set) {
	    return new ArrayList<T>(mDatas).get(position);
	} else {
	    return null;
	}
	}
	
	@Override
	public long getItemId(int position) {
	return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	final AdapterHolder viewHolder = getViewHolder(position, convertView,
	        parent);
	convert(viewHolder, getItem(position), isScrolling, position);
	return viewHolder.getConvertView();
	
	}
	
	private AdapterHolder getViewHolder(int position, View convertView,
	                                ViewGroup parent) {
	return AdapterHolder.get(convertView, parent, mItemLayoutId, position);
	}
	
	public void convert(AdapterHolder helper, T item,
	                boolean isScrolling) {
	}
	
	public void convert(AdapterHolder helper, T item, boolean isScrolling,
	                int position) {
	convert(helper, getItem(position), isScrolling);
	}
	
	@Override
	public void onScrollStateChanged(ZrcAbsListView view, int scrollState) {
	if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
	    isScrolling = false;
	    this.notifyDataSetChanged();
	} else {
	    isScrolling = true;
	}
	if (listener != null) {
	    listener.onScrollStateChanged(view, scrollState);
	}
	}
	
	@Override
	public void onScroll(ZrcAbsListView view, int firstVisibleItem,
	                 int visibleItemCount, int totalItemCount) {
	if (listener != null) {
	    listener.onScroll(view, firstVisibleItem, visibleItemCount,
	            totalItemCount);
	}
	}
}
