package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import hotP2B.WageGainTools.android.inter.HOTAPI;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;

public class AppHuifu extends AppTitleBar 
{

    @BindView(id = R.id.webview)
    private WebView mWebView;
    @BindView(id = R.id.browser_bottom)
    private LinearLayout mLayoutBottom;
    @BindView(id = R.id.progress)
    private ProgressBar mProgress;
    @BindView(id = R.id.browser_empty_layout)
    private EmptyLayout mEmptyLayout;
    
    public static final String BROWSER_KEY = "browser_url";
    public static final String BROWSER_TITLE_KEY = "browser_title_url";

    private String mCurrentUrl ="";
    private String strTitle;

   
    @Override
    public void setRootView() 
    {
        setContentView(R.layout.aty_browser_huifu);
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
                this.finish();
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

        this.mImgBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(strTitle);
        initWebView();
        mWebView.loadUrl(mCurrentUrl);

    }
    
    @Override
    public void onBackClick()
    {
    	this.finish();
    	
    }

    @Override
    protected void onResume() 
    {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        mWebView.destroy();
        
        Intent intent=new Intent(AppConfig.ASSET_BROADCAST_ACTION);
		this.sendBroadcast(intent);
    }

   
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
            if (mWebView.canGoBack()) 
            {
                mWebView.goBack();
            } 
            else
            {
                finish();
            }
            return true;
        } 
        else 
        {
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
    protected void onWebTitle(WebView view, String title) 
    {
        if (aty != null && mWebView != null) { // 必须做判断，由于webview加载属于耗时操作，可能会本Activity已经关闭了才被调用
            mTvTitle.setText(mWebView.getTitle());
        }
    }



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
        mWebView.addJavascriptInterface(new HOTAPI(this),"HOTAPI");
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            onWebTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 进度
            super.onProgressChanged(view, newProgress);
            if (newProgress > 80) 
            {
                mEmptyLayout.dismiss();
                mProgress.setVisibility(View.GONE);
            }
        }
 
    }

    private class MyWebViewClient extends WebViewClient 
    {
    	
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        {
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
    
 
    
  

    
  

}

