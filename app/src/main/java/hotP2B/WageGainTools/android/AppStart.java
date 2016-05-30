package hotP2B.WageGainTools.android;

import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.LoginResponse;
import hotP2B.WageGainTools.android.bean.response.TokenResponse;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.TokenUtils;
import hotP2B.WageGainTools.android.utils.UpdateManager;
import hotP2B.WageGainTools.android.utils.UpdateManager.CheckAppCallBack;
import hotP2B.WageGainTools.android.utils.TokenUtils.TokenInfo;
import hotP2B.WageGainTools.android.utils.UserUtils;
import hotP2B.WageGainTools.android.utils.UserUtils.User;
import hotP2B.WageGainTools.android.R;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.utils.SystemTool;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//检查网络->检查更新->获取Token->自动登录->登录

public class AppStart extends KJActivity 
{
	private boolean bCheckNet=false;
	private boolean bCheckPermission=false;
	private boolean bHasCheckUpdate=false;
	private boolean bHasGetToken=false;
	private final static int CheckNet=0;
	private final static int CheckUpdate=1;
	private final static int GetToken=2;
	private final static int StartApp=3;
	private final static int ShowLoading=4;
	
	
	private CustomProgressDialog loadingDialog=null;
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
	    {
		  switch(msg.what)
		  {
		  case CheckNet:
			  checkNet();
			  break;
		  case CheckUpdate:
		      checkUpdate();
		      break;
		  case GetToken:
			  getToken();
			  break;
		  case StartApp:
			  startApp();
			  break;
		  case ShowLoading:
			  if(bCheckNet && bCheckPermission && !AppContext.bHasNewVersion && !AppStart.this.bHasGetToken)
			  {
				  loadingDialog=new CustomProgressDialog(AppStart.this,"正在加载数据,请稍等...",true);
				  loadingDialog.show();
			  }
			  break;
		 default:
			  break;
		  }
		}
	};

	
	@Override
	public void setRootView() 
	{
        Activity aty = KJActivityStack.create().findActivity(AppMain.class);
        Activity aty2 = KJActivityStack.create().findActivity(AppSimpleBack.class);
        if ((aty != null && !aty.isFinishing())|| (aty2 != null && !aty2.isFinishing()))
        {
            this.finish();
            return;
        }
        
		ImageView image = new ImageView(this.aty);
		image.setImageResource(R.mipmap.splash_bg);
		Animation animation = AnimationUtils.loadAnimation(this.aty, R.anim.splash_start);
		animation.setAnimationListener(new Animation.AnimationListener() 
		{
			@Override
			public void onAnimationRepeat(Animation animation) 
			{
			}
			
			@Override
			public void onAnimationStart(Animation animation) 
			{
				handler.sendEmptyMessage(CheckNet);
			}

			@Override
			public void onAnimationEnd(Animation animation) 
			{
				if(AppStart.this.bCheckNet && !AppStart.this.bHasGetToken)
				{
					handler.sendEmptyMessageDelayed(ShowLoading, 2000);
				}
			}

		});
		this.setContentView(image);
		image.setAnimation(animation);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	
	//检查网络
	private void checkNet() 
	{
	  if(SystemTool.checkNet(aty))
	  {
		 bCheckNet=true;
         checkPermission();//检查权限
	  }
	  else 
	  {
		 bCheckNet=false;
		 DialogUtils.showAlertDialog(aty,R.string.prompt, "连接到网络失败,是否重试？",checkNetListener, exitListener);
	  }
	}

	private void checkPermission()
	{
		MPermissions.requestPermissions(this,AppConfig.REQUEST_PERMISSION_READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);	
	}

	//检查更新
	private void checkUpdate()
	{
		UpdateManager update=UpdateManager.getInstance();
		update.setCheckAppCallBack(new CheckAppCallBack() 
		{
			@Override
			public void onFinish() 
			{
				AppStart.this.bHasCheckUpdate=true;
				AppStart.this.handler.sendEmptyMessage(StartApp);
			}
			
		 });
		 update.checkAppUpdate(this,false);
	}

	//获取Token
    private void getToken()
    {
		HttpUtils.getTokenFromServer(this.aty,TokenResponse.class, new HttpBaseCallBack<TokenResponse>()
		{
			@Override
			public void onSuccess(TokenResponse response)
			{
				TokenInfo tokenInfo=null;
				tokenInfo=TokenUtils.parse2TokenInfo(response);
				if(tokenInfo!=null)
				{
				   TokenUtils.saveLocalToken(aty, tokenInfo);
				   AppStart.this.bHasGetToken=true;
				   AppStart.this.handler.sendEmptyMessage(StartApp);
				}
				else
				{
					ViewInject.longToast("未能获取到有效的访问凭证,请重新打开app!");
					AppContext.exitUI();
				}
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				DialogUtils.showAlertDialog(aty, R.string.prompt, "获取加密凭证失败,请切换网络或稍后再试,是否重试？",getTokenListener,exitListener);

			}
			@Override
			public void onFinish() 
			{
			}

		});
    }
	private void startApp() 
	{
	    if(!AppStart.this.bHasCheckUpdate || !AppStart.this.bHasGetToken)return;
	    if(loadingDialog!=null && loadingDialog.isShowing())
	    {
		   loadingDialog.dismiss();
	    }

	    this.handler.removeMessages(StartApp);
	    this.handler.removeMessages(GetToken);
	    this.handler.removeMessages(CheckUpdate);
	   
	    String key="firstopen_"+SystemTool.getAppVersionName(this.aty);
	    boolean isFirst = PreferenceHelper.readBoolean(aty, AppConfig.APPNAME,key,true);
		if (!isFirst) 
		{
			User user=UserUtils.getLocalUser(this.aty);
			boolean bAutoLogin=PreferenceHelper.readBoolean(aty, AppConfig.APPNAME,"autologin",false);
			if(bAutoLogin)
			{
			  if(user!=null && user.getCanautologin().equals("1"))
			  {
				 doAutoLogin(user.getUsername(),user.getLoginkeyid());
				 return;
			  }
			}
			doLogin();

		} 
		else 
		{
		   this.skipActivity(aty, AppGuide.class);
		}
	
	}

	
	//执行自动登录，如自动登录失败，则跳转到登录界面
	private void doAutoLogin(final String username,final String loginkeyid) 
	{
		final CustomProgressDialog dialog=new CustomProgressDialog(this.aty,"正在自动登录,请稍等...",false);
		dialog.show();
		HttpUtils.loginFromServer(this.aty,username,"",AppConfig.LONGIN_AUTO,loginkeyid,LoginResponse.class,new HttpBaseCallBack<LoginResponse>()
		{

			@Override
			public void onSuccess(LoginResponse response) 
			{
				if(response!=null && !StringUtils.isEmpty(response.getUserpkid()))
				{
					AppContext.m_CurrentAccount=UserUtils.parse2UserAccount(response, username);
					if(AppContext.m_CurrentAccount!=null)
					{
				      AppContext.m_CurrentAccountIsLogin=true;
				  	  User u=new User(AppContext.m_CurrentAccount.getUsername(),AppContext.m_CurrentAccount.getUserpkid(),AppContext.m_CurrentAccount.getCanautologin(),AppContext.m_CurrentAccount.getLoginkeyid());
					  UserUtils.saveUser(AppStart.this,u);
				      ViewInject.toast("自动登录成功");	
				      AppStart.this.skipActivity(aty,AppMain.class);
				      return;
					}
				}
			    doLogin();
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("自动登录失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
				doLogin();
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}
			
		});
	}
	
	
	private void doLogin()
	{
		AppSimpleBack.postShowWith(this.aty, SimpleBackPage.LOGIN);
		this.finish();
	}

	private DialogInterface.OnClickListener checkNetListener=new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			dialog.dismiss();
			checkNet();
		}
	};
	
	private DialogInterface.OnClickListener getTokenListener=new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			dialog.dismiss();
			getToken();
		}
	};
	
	private DialogInterface.OnClickListener exitListener=new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			dialog.dismiss();
			AppContext.exitUI();
		}
	};
	
	@Override
    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[]grantResults)
    {
    	 MPermissions.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
    }
	
	@PermissionGrant(AppConfig.REQUEST_PERMISSION_READ_PHONE_STATE)
	public void requestPhoneSuccess()
	{
		 bCheckPermission=true;
		 AppContext.IMEI=SystemTool.getPhoneIMEI(this);
		 AppStart.this.handler.sendEmptyMessage(CheckUpdate);
		 AppStart.this.handler.sendEmptyMessage(GetToken);
	}

	@PermissionDenied(AppConfig.REQUEST_PERMISSION_READ_PHONE_STATE)
	public void requestPhoneFailed()
	{
		bCheckPermission=false;
		new CustomAlertDialog(this).Builder()
    	.setTitle("权限申请")
        .setMsg("请在设置-应用-良薪宝-权限管理中打开电话或获取手机信息权限,以正常使用良薪宝")
        .setCancelable(false)
        .setPositiveButton("去设置", new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				CommonUtils.goSystemSetting(AppStart.this);
				AppContext.exitUI();
			}
		})
        .show();
	}
	
	@PermissionGrant(AppConfig.REQUEST_PERMISSION_STORAGE)
	public void requestSDCardSuccess()
	{
		UpdateManager.getInstance().downloadApk();
	}

	@PermissionDenied(AppConfig.REQUEST_PERMISSION_STORAGE)
	public void requestSDCardFailed()
	{
		new CustomAlertDialog(this).Builder()
    	.setTitle("权限申请")
        .setMsg("请在设置-应用-良薪宝-权限管理中打开读写手机存储权限")
        .setCancelable(false)
        .setPositiveButton("去设置", new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				CommonUtils.goSystemSetting(AppStart.this);
				AppContext.exitUI();
			}
		})
        .show();
	}
	

}