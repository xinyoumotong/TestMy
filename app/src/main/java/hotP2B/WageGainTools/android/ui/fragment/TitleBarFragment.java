
package hotP2B.WageGainTools.android.ui.fragment;


import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppFragment;
import hotP2B.WageGainTools.android.AppTitleBar;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.view.View;

/**
 * 
 * 具有ActionBar的Activity的基类
 * 
 */
public abstract class TitleBarFragment extends AppFragment 
{

    /**
     * 封装一下方便一起返回(JAVA没有结构体这么一种东西实在是个遗憾)
     * 
     */
    public class ActionBarRes 
    {
    	public int titleId;
        public CharSequence title;
        public int backImageId;
        public int menuImageId;
    }
    

    private boolean validateAccount=true;
    //设置该部件是否需要登录
    public void setValidateAccount(Boolean validate)
    {
    	validateAccount=validate;
    }
    private final ActionBarRes actionBarRes = new ActionBarRes();
    protected AppTitleBar outsideAty;
    protected AppContext app;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        if (getActivity() instanceof AppTitleBar) 
        {
            outsideAty = (AppTitleBar) getActivity();
        }
        app = (AppContext) getActivity().getApplication();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() 
    {
    	if(validateAccount)
        {
        	if(AppContext.m_CurrentAccount==null || AppContext.m_CurrentAccountIsLogin==false)
        	{
	    		ViewInject.toast("检测到用户没有登录，程序将退出");
	    		AppContext.exitUI();
        	}
        }
        super.onResume();
        
        
        setActionBarRes(actionBarRes);
        
        
        if(actionBarRes.titleId==0 && StringUtils.isEmpty(actionBarRes.title))
        {
        	this.outsideAty.mRlTitleBar.setVisibility(View.GONE);
        }
        else
        {
        	this.outsideAty.mRlTitleBar.setVisibility(View.VISIBLE);
        }
    
        
        if(actionBarRes.titleId!=0)
        {
           setTitle(actionBarRes.titleId);
        }
        else
        {
          setTitle(actionBarRes.title);
        }
      
        if (actionBarRes.backImageId != 0) 
        {
          setBackImage(actionBarRes.backImageId);
        } 
        else
        {
        	this.outsideAty.mImgBack.setVisibility(View.GONE);
        }
    
        if (actionBarRes.menuImageId != 0) 
        {
        	setMenuImage(actionBarRes.menuImageId);
        } 
        else
        {
        	this.outsideAty.mImgMenu.setVisibility(View.GONE);
        }
        
   
    }


    /**
     * 方便Fragment中设置ActionBar资源
     * 
     * @param actionBarRes
     * @return
     */
    protected void setActionBarRes(ActionBarRes actionBarRes) {}

    /**
     * 当ActionBar上的返回键被按下时
     */
    public void onBackClick() {}

    /**
     * 当ActionBar上的菜单键被按下时
     */
    public void onMenuClick() {}

    /**
     * 设置标题
     * 
     * @param text
     */
    protected void setTitle(int resId) 
    {
        if (outsideAty != null) 
        {
            outsideAty.mTvTitle.setText(resId);
        }
    }
    /**
     * 设置标题
     * 
     * @param text
     */
    protected void setTitle(CharSequence text) 
    {
        if (outsideAty != null) 
        {
            outsideAty.mTvTitle.setText(text);
        }
    }

    /**
     * 设置返回键图标
     */
    protected void setBackImage(int resId) 
    {
        if (outsideAty != null) 
        {
        	outsideAty.mImgBack.setVisibility(View.VISIBLE);
            outsideAty.mImgBack.setImageResource(resId);
        }
    }



    /**
     * 设置菜单键图标
     */
    protected void setMenuImage(int resId) 
    {
        if (outsideAty != null) 
        {
           outsideAty.mImgMenu.setVisibility(View.VISIBLE);
           outsideAty.mImgMenu.setImageResource(resId);
        }
    }


 
}
