package hotP2B.WageGainTools.android.ui.fragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppMain;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.LoginResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;
import hotP2B.WageGainTools.android.utils.UserUtils.User;

/**
 * 用户登录界面
 * 
 */
public class LoginFragment extends TitleBarFragment 
{

	@BindView(id = R.id.login_et_username)
	private EditText m_login_et_username;

	@BindView(id = R.id.login_et_password)
	private EditText m_login_et_password;
	
	@BindView(id = R.id.login_iv_showPassword, click = true)
	private ImageView m_iv_showPassword;
	
	@BindView(id = R.id.login_btn_login, click = true)
	private Button m_login_btn_login;

	@BindView(id = R.id.login_btn_register, click = true)
	private Button m_login_btn_register;
	
	@BindView(id = R.id.login_cb_autoLogin, click = true)
	private CheckBox m_login_cb_autoLogin;
	
	@BindView(id = R.id.login_tv_forgetPassword, click = true)
	private TextView m_login_tv_forgetPassword;
	
	private boolean bAutoLogin=false;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) 
	{
		View view = View.inflate(outsideAty, R.layout.frag_login, null);
		return view;
	}


	@Override
	protected void initData() 
	{
		super.initData();
		this.setValidateAccount(false);
		bAutoLogin=PreferenceHelper.readBoolean(this.outsideAty, AppConfig.APPNAME,"autologin",false);

	}

	@Override
	protected void initWidget(View parentView) 
	{
		super.initWidget(parentView);
		
		if(bAutoLogin)
		{
			m_login_cb_autoLogin.setChecked(true);
		}
		else
		{
			m_login_cb_autoLogin.setChecked(false);
		}
		
		User user=UserUtils.getLocalUser(this.outsideAty);
		if(user!=null)
		{
			m_login_et_username.setText(user.getUsername());
			m_login_et_password.requestFocus();
		}
		else
		{
			m_login_et_username.requestFocus();
		}
		
	}

	@Override
	public void widgetClick(View v) 
	{
		super.widgetClick(v);
		switch (v.getId()) 
		{
		case R.id.login_btn_login:
			doLogin();
			break;
		case R.id.login_btn_register:
			AppSimpleBack simple=(AppSimpleBack)this.outsideAty;
			simple.changeFragment(SimpleBackPage.VERIFY1);
			break;
		case R.id.login_tv_forgetPassword:
			AppSimpleBack simple2=(AppSimpleBack)this.outsideAty;
			simple2.changeFragment(SimpleBackPage.RESETPASSWORD);
			break;
		case R.id.login_iv_showPassword:
			doShowPassword();
			break;
        default:
			break;
		}
	}

	@Override
	public void onBackClick() 
	{
		super.onBackClick();
		this.outsideAty.finish();
		
	}
	
	private void doShowPassword() 
	{
		TransformationMethod method=m_login_et_password.getTransformationMethod();
		if(method instanceof HideReturnsTransformationMethod)//隐藏密码
		{
			m_login_et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
			m_iv_showPassword.setSelected(false);
		}
		else
		{
			m_login_et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			m_iv_showPassword.setSelected(true);
		}
		m_login_et_password.setSelection(m_login_et_password.getText().length());

	}

	private boolean checkAccountAndPwd() 
	{
		boolean result = false;
		String username = m_login_et_username.getText().toString().trim();
		String pwd = m_login_et_password.getText().toString().trim();
		if (username.length()==11) 
		{
			if (pwd.length() >= 6 && pwd.length() <= 16) 
			{
				result = true;
			} 
			else 
			{
				ViewInject.toast("密码长度不对");
			}
		} 
		else 
		{
			ViewInject.toast("手机号码格式不正确");
		}
		return result;

	}

	//登录
	private void doLogin()
	{
		if (checkAccountAndPwd()) 
		{
			final String username = m_login_et_username.getText().toString().trim();
			final String userpass = m_login_et_password.getText().toString().trim();

			final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在登录,请稍等...",false);
			dialog.show();
			
			HttpUtils.loginFromServer(this.outsideAty, username, userpass,"0","",LoginResponse.class, new HttpBaseCallBack<LoginResponse>()
			{
				@Override
				public void onSuccess(LoginResponse loginResponse) 
				{
					if(loginResponse!=null && !StringUtils.isEmpty(loginResponse.getUserpkid()))
					{
						AppContext.m_CurrentAccount=UserUtils.parse2UserAccount(loginResponse, username);
						if(AppContext.m_CurrentAccount!=null)
						{
							AppContext.m_CurrentAccountIsLogin=true;
							User u=new User(AppContext.m_CurrentAccount.getUsername(),AppContext.m_CurrentAccount.getUserpkid(),AppContext.m_CurrentAccount.getCanautologin(),AppContext.m_CurrentAccount.getLoginkeyid());
							UserUtils.saveUser(outsideAty,u);
							if(m_login_cb_autoLogin.isChecked())
							{
							   PreferenceHelper.write(outsideAty,AppConfig.APPNAME,"autologin",true); 
							}
							else
							{
								PreferenceHelper.write(outsideAty,AppConfig.APPNAME,"autologin",false);
							}
							ViewInject.toast("登录成功");                                 
							outsideAty.skipActivity(outsideAty, AppMain.class);
						}
						else
						{
							ViewInject.toast("转换返回信息失败,请重新尝试.");
						}
					}                            
				}
				
				@Override
				public void onFailure(int errorNo, String strMsg) 
				{
//					ViewInject.toast("登录失败,错误代码:"+errorNo+",错误信息:"+strMsg);
				}

				@Override
				public void onFinish() 
				{
					dialog.dismiss();
				}
			});
		}
	}
}

	
