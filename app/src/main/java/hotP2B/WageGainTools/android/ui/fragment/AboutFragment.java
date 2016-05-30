
package hotP2B.WageGainTools.android.ui.fragment;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.SystemTool;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.UpdateManager;

public class AboutFragment extends TitleBarFragment 
{

	@BindView(id = R.id.about_tv_version)
	private TextView m_about_tv_version;
    @BindView(id = R.id.about_rl_updateVersion, click = true)
    private RelativeLayout m_about_rl_updateVersion;
    @BindView(id = R.id.about_rl_appIntroduce, click = true)
    private RelativeLayout m_about_rl_appIntroduce;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) {
        View view = View.inflate(outsideAty, R.layout.frag_about, null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) 
    {
        actionBarRes.title = "关于我们";
        actionBarRes.backImageId=R.mipmap.titlebar_back;
    }

    @Override
    public void onBackClick()
    {
        super.onBackClick();
        outsideAty.finish();
    }
    
    @Override
    protected void initData()
    {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) 
    {
        super.initWidget(parentView);
        String versionInfo="版本: "+SystemTool.getAppVersionName(this.outsideAty);
        if(AppConfig.Server.startsWith("http://192.168"))
        {
        	versionInfo+="(测试版)";
        }
        else if(AppConfig.Server.startsWith("http://42.159"))
        {
        	versionInfo+="(联调版)";
        }
        this.m_about_tv_version.setText(versionInfo);
    }

    @Override
    protected void widgetClick(View v) 
    {
        super.widgetClick(v);
        switch (v.getId()) 
        {
        case R.id.about_rl_updateVersion:
        	UpdateManager.getInstance().setCheckAppCallBack(null);
        	UpdateManager.getInstance().checkAppUpdate(AboutFragment.this,true);
            break;
        case R.id.about_rl_appIntroduce:
        {
          //UIHelper.toBrowser(this.outsideAty, AppConfig.URL_APP_INTRODUCE, "良薪宝介绍");
            AppSimpleBack.postShowWith(outsideAty, SimpleBackPage.APPINTRODUCE);
        }
        break;
        default:
            break;
        }
    }

     @Override
     public void onRequestPermissionsResult(int requestCode, String[]permissions, int[]grantResults)
     {
   		MPermissions.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
     }   
     
     @PermissionGrant(AppConfig.REQUEST_PERMISSION_STORAGE)
 	 public void requestSDCardSuccess()
 	 {
 		UpdateManager.getInstance().downloadApk();
 	 }

 	 @PermissionDenied(AppConfig.REQUEST_PERMISSION_STORAGE)
 	 public void requestSDCardFailed()
 	 {
 		  new CustomAlertDialog(this.outsideAty).Builder()
      	 .setTitle("权限申请")
         .setMsg("请在设置-应用-良薪宝-权限管理中打开读写手机存储权限")
         .setPositiveButton("去设置", new View.OnClickListener() 
          {
  			@Override
  			public void onClick(View v) 
  			{
  				CommonUtils.goSystemSetting(outsideAty);
  			}
  		})
        .setNegativeButton("取消", null)
        .show();
 	 }
   
}
