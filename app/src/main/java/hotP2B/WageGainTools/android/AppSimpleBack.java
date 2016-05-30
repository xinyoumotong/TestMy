package hotP2B.WageGainTools.android;

import com.zhy.m.permission.MPermissions;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;

/**
 * 应用二级界面
 * 
 * 
 */
public class AppSimpleBack extends AppTitleBar 
{
    public static String TAG = AppSimpleBack.class.getSimpleName();
    public static String CONTENT_KEY = "sba_content_key";
    public static String DATA_KEY = "sba_data_key";
    public static String CANBACK_KEY = "sba_canback_key";
   
    private TitleBarFragment currentFragment;
    private Boolean canKeyBack=true;

    @Override
    public void setRootView() 
    {
        setContentView(R.layout.aty_simple_back);
        int value = getIntent().getIntExtra(CONTENT_KEY, -1);
        canKeyBack=getIntent().getBooleanExtra(CANBACK_KEY, true);
        if (value != -1) 
        {
            try 
            {
                currentFragment = (TitleBarFragment) SimpleBackPage.getPageByValue(value).newInstance();
                changeFragment(currentFragment);
            } 
            catch (InstantiationException e) 
            {
            } 
            catch (IllegalAccessException e) 
            {
            }
        }
    }

    public Bundle getBundleData() {
        return getIntent().getBundleExtra(DATA_KEY);
    }

    public void changeFragment(TitleBarFragment targetFragment) 
    {
        super.changeFragment(R.id.main_content, targetFragment);
        currentFragment=targetFragment;
    }
    public void changeFragment2(TitleBarFragment targetFragment,Bundle bundle) 
    {
    	targetFragment.setArguments(bundle);
        super.changeFragment(R.id.main_content, targetFragment);
        currentFragment=targetFragment;
    }
    public void changeFragment(SimpleBackPage page,Bundle bundle)
    {
    	try 
    	{
			changeFragment2((TitleBarFragment) SimpleBackPage.getPageByValue(page.getValue()).newInstance(),bundle);
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }

    public void changeFragment(SimpleBackPage page)
    {
    	try {
			changeFragment(
					(TitleBarFragment) SimpleBackPage.getPageByValue(
							page.getValue()
							)
					.newInstance());
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
    @Override
    protected void onBackClick() {
        super.onBackClick();
        if (currentFragment != null) {
            currentFragment.onBackClick();
        }
    }

    @Override
    protected void onMenuClick() {
        super.onMenuClick();
        if (currentFragment != null) {
            currentFragment.onMenuClick();
        }
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     * 
     * @param cxt
     * @param page
     * @param data
     */
    public static void postShowWith(Context cxt, SimpleBackPage page,
            Bundle data,Boolean canBack) {
    	
        Intent intent = new Intent(cxt, AppSimpleBack.class);
        intent.putExtra(CONTENT_KEY, page.getValue());
        intent.putExtra(DATA_KEY, data);
        intent.putExtra(CANBACK_KEY, canBack);
        cxt.startActivity(intent);
       
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     * 
     * @param cxt
     * @param page
     */
    public static void postShowWith(Context cxt, SimpleBackPage page) {
        postShowWith(cxt, page, null,true);
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     * 
     * @param cxt
     *            从哪个Activity跳转
     * @param code
     *            启动码
     * @param page
     *            要显示的Fragment
     * @param data
     *            传递的Bundle数据
     */
    public static void postShowForResult(Fragment fragment, int code,
            SimpleBackPage page, Bundle data) {
        Intent intent = new Intent(fragment.getActivity(),
        		AppSimpleBack.class);
        intent.putExtra(CONTENT_KEY, page.getValue());
        intent.putExtra(DATA_KEY, data);
        fragment.startActivityForResult(intent, code);
    }

    /**
     * 跳转到SimpleBackActivity时，只能使用该方法跳转
     * 
     * @param cxt
     *            从哪个Activity跳转
     * @param code
     *            启动码
     * @param page
     *            要显示的Fragment
     */
    public static void postShowForResult(Fragment fragment, int code,
            SimpleBackPage page) {
        postShowForResult(fragment, code, page, null);
    }
    public static Intent getExecPostShowWithIntent(Context cxt, SimpleBackPage page,Bundle data)
    {
        Intent intent = new Intent(cxt, AppSimpleBack.class);
        intent.putExtra(CONTENT_KEY, page.getValue());
        intent.putExtra(DATA_KEY, data);
        return intent;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
        	if(canKeyBack)
        	{
        		onBackClick();
        	}
        	return true;
        }
        else 
        {
            return super.onKeyDown(keyCode, event);
        }
    };
    
  
}
