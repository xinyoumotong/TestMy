package hotP2B.WageGainTools.android;


import org.kymjs.kjframe.ui.AnnotateUtil;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public abstract class AppFragment extends Fragment implements OnClickListener {

    public static final int WHICH_MSG = 0X37211;

    protected View fragmentRootView;

    /**
     * 一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack {
        void onSuccess();
    }

    private static ThreadDataCallBack callback;

    // 当线程中初始化的数据初始化完成后，调用回调方法
    private static Handler threadHandle = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == WHICH_MSG) {
                callback.onSuccess();
            }
        };
    };

    protected abstract View inflaterView(LayoutInflater inflater,
            ViewGroup container, Bundle bundle);

    /**
     * initialization widget, you should look like parentView.findviewbyid(id);
     * call method
     * 
     * @param parentView
     */
    protected void initWidget(View parentView) {}

    /** initialization data */
    protected void initData() {}

    /**
     * 当通过changeFragment()显示时会被调用(类似于onResume)
     */
    protected void onChange() {}

    /**
     * initialization data. And this method run in background thread, so you
     * shouldn't change ui<br>
     * on initializated, will call threadDataInited();
     */
    protected void initDataFromThread() {
        callback = new ThreadDataCallBack() {
            @Override
            public void onSuccess() {
                threadDataInited();
            }
        };
    }

    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited() {}

    /** widget click method */
    protected void widgetClick(View v) {}

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	if(fragmentRootView==null)
    	{
		 fragmentRootView = inflaterView(inflater, container, savedInstanceState);
		  AnnotateUtil.initBindView(this, fragmentRootView);
	        initData();
	        initWidget(fragmentRootView);
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                initDataFromThread();
	                threadHandle.sendEmptyMessage(WHICH_MSG);
	            }
	        }).start();
    	}
    	ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
        if (parent != null) 
        {
               parent.removeView(fragmentRootView);
        }
        return fragmentRootView;
    }

    @SuppressWarnings("unchecked")
	protected <T extends View> T bindView(int id) {
        return (T) fragmentRootView.findViewById(id);
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id, boolean click) {
       
		T view = (T) fragmentRootView.findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }
}