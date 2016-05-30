package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;

/**
 * 浏览器
 * 
 * 
 */
public class AppBrowser extends AppTitleBar {

    @BindView(id = R.id.webview)
    WebView mWebView;
    @BindView(id = R.id.browser_back, click = true)
    ImageView mImgBrowserBack;
    @BindView(id = R.id.browser_forward, click = true)
    ImageView mImgForward;
    @BindView(id = R.id.browser_refresh, click = true)
    ImageView mImgRefresh;
    @BindView(id = R.id.browser_system_browser, click = true)
    ImageView mImgSystemBrowser;
    @BindView(id = R.id.browser_bottom)
    LinearLayout mLayoutBottom;
    @BindView(id = R.id.progress)
    ProgressBar mProgress;
    @BindView(id = R.id.browser_empty_layout)
    private EmptyLayout mEmptyLayout;
    


    public static final String BROWSER_KEY = "browser_url";
    public static final String BROWSER_TITLE_KEY = "browser_title_url";
    
    public static final String DEFAULT = "http://www.baidu.com";

    private int flag = 1; // 双击事件需要
    private String mCurrentUrl = DEFAULT;
    private String strTitle;

    private Animation animBottomIn, animBottomOut;
    private GestureDetector mGestureDetector;

    @Override
    public void setRootView() 
    {
        setContentView(R.layout.aty_browser);
    }

    @Override
    public void initData() 
    {
    	super.initData();
        Intent intent = getIntent();
        if (intent != null) 
        {
            mCurrentUrl = intent.getStringExtra(BROWSER_KEY);
            strTitle = intent.getStringExtra(BROWSER_TITLE_KEY);
         
            if (StringUtils.isEmpty(mCurrentUrl)) 
            {
                mCurrentUrl = DEFAULT;
            }
            if (StringUtils.isEmpty(strTitle)) 
            {
                strTitle = getString(R.string.app_name);
            }

        }
    }

    @Override
    public void initWidget() 
    {
        super.initWidget();
        initWebView();
        initBarAnim();
        mGestureDetector = new GestureDetector(aty, new MyGestureListener());
        mTvTitle.setText(strTitle);
        this.mImgBack.setVisibility(View.VISIBLE);
     
        
        mWebView.loadUrl(mCurrentUrl);
        mWebView.setOnTouchListener(new OnTouchListener() 
        {
            @SuppressLint("ClickableViewAccessibility")
			@Override
            public boolean onTouch(View v, MotionEvent event) 
            {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }
    
    @Override
    public void onBackClick()
    {
    	this.finish();
    	
    }


    /**
     * 初始化上下栏的动画并设置结束监听事件
     */
    private void initBarAnim() {
        animBottomIn = AnimationUtils.loadAnimation(aty, R.anim.anim_bottom_in);
        animBottomOut = AnimationUtils.loadAnimation(aty,R.anim.anim_bottom_out);
        animBottomIn.setAnimationListener(new AnimationListener() 
        {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) 
            {
                mLayoutBottom.setVisibility(View.VISIBLE);
            }
        });
        animBottomOut.setAnimationListener(new AnimationListener() 
        {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayoutBottom.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }

    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        switch (v.getId()) {
        case R.id.browser_back:
            mWebView.goBack();
            break;
        case R.id.browser_forward:
            mWebView.goForward();
            break;
        case R.id.browser_refresh:
            mWebView.loadUrl(mWebView.getUrl());
            break;
        case R.id.browser_system_browser:
        	callSystemBrowser();
            break;

        default:
            break;
        }
    }
    private void callSystemBrowser()
    {
    	try 
    	{
            Uri uri = Uri.parse(mCurrentUrl);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            aty.startActivity(it);
        } 
    	catch (Exception e) 
    	{
            ViewInject.toast("网页地址错误");
        }
    }

   
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
            if (mWebView.canGoBack()) 
            {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 当前WebView显示页面的标题
     * 
     * @param view
     *            WebView
     * @param title
     *            web页面标题
     */
    protected void onWebTitle(WebView view, String title) {
        if (aty != null && mWebView != null) { // 必须做判断，由于webview加载属于耗时操作，可能会本Activity已经关闭了才被调用
            mTvTitle.setText(mWebView.getTitle());
        }
    }

    /**
     * 当前WebView显示页面的图标
     * 
     * @param view
     *            WebView
     * @param icon
     *            web页面图标
     */
    protected void onWebIcon(WebView view, Bitmap icon) {}

    /**
     * 初始化浏览器设置信息
     */
    @SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用支持javascript
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);// 优先使用缓存
        webSettings.setAllowFileAccess(true);// 可以访问文件
        //webSettings.setBuiltInZoomControls(true);// 支持缩放

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            onWebIcon(view, icon);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 进度
            super.onProgressChanged(view, newProgress);
            if (newProgress > 60) {
                mEmptyLayout.dismiss();
                mProgress.setVisibility(View.GONE);
            }
        }
        
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mProgress.setVisibility(View.VISIBLE);
            mCurrentUrl = url;
            boolean flag = super.shouldOverrideUrlLoading(view, url);
            return flag;
        }

        @Override
        public void onPageFinished(WebView view, String url) 
        {
            mProgress.setVisibility(View.GONE);
            mCurrentUrl = url;
            super.onPageFinished(view, url);
        }

        @SuppressWarnings("deprecation")
		@Override
        public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) 
        {
            super.onReceivedError(view, errorCode, description, failingUrl);
            ViewInject.toast("加载失败,错误代码:" + errorCode + ",错误信息:" + description);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        }
    }

    private class MyGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {// webview的双击事件
            if (flag % 2 == 0) {
                flag++;
                mLayoutBottom.startAnimation(animBottomIn);
            } else {
                flag++;
                mLayoutBottom.startAnimation(animBottomOut);
            }
            return super.onDoubleTap(e);
        }
    }


  

}
