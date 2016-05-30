package hotP2B.WageGainTools.android.ui.fragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.bean.response.GetTemporaryResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.VerifyCodeResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;

/**
 * 忘记密码界面
 * 
 */
public class ResetPasswordFragment extends TitleBarFragment 
{

	@BindView(id = R.id.reset_et_mobile)
    private EditTextWithDel m_reset_et_mobile;
	@BindView(id = R.id.reset_et_verifyCode)
    private EditTextWithDel m_reset_et_verifyCode;
	
	@BindView(id = R.id.reset_iv_mobile_clear)
	 private ImageView m_reset_iv_mobile_clear;
	
	@BindView(id = R.id.reset_tv_validation_code, click = true)
	private TextView m_reset_tv_validation_code;
	@BindView(id = R.id.reset_btn_next, click = true)
	private Button m_reset_btn_next;
	
	private TimeCount timeCount=null;

	
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) 
	{
		View view = View.inflate(outsideAty, R.layout.frag_reset_password, null);
		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
		actionBarRes.title ="忘记密码";
		actionBarRes.backImageId = R.mipmap.titlebar_back;
	}

	@Override
	protected void initData() 
	{
		super.initData();
		this.setValidateAccount(false);

	}

	@Override
	protected void initWidget(View parentView) 
	{
	   super.initWidget(parentView);
       this.outsideAty.mRlTitleBar.setVisibility(View.VISIBLE);
       this.m_reset_et_mobile.setImageView(m_reset_iv_mobile_clear);
	}

	@Override
	public void onBackClick() 
	{
		super.onBackClick();
		AppSimpleBack simple=(AppSimpleBack)this.outsideAty;
	    simple.changeFragment(SimpleBackPage.LOGIN);
	}

	@Override
	public void widgetClick(View v) 
	{
		super.widgetClick(v);
		switch (v.getId()) 
		{
		case R.id.reset_tv_validation_code:
			getVerifyCode();
			break;
		case R.id.reset_btn_next:
			getTemporaryPassword();

			break;
        default:
			break;
		}
	}

	//请求发送验证码
    private void getVerifyCode()
    {
    	final String strMobile=this.m_reset_et_mobile.getText().toString().trim();
    	
    	if(StringUtils.isEmpty(strMobile))
    	{
    		ViewInject.toast("请输入手机号码");
    		return;
    	}
    	if(strMobile.length()!=11)
    	{
    		ViewInject.toast("手机号码格式不正确");
    		return;
    	}

       	final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在获取验证码,请稍等...",false);
    	dialog.show();
		HttpUtils.getVerifyCodeFromServer(this.outsideAty,strMobile,false,VerifyCodeResponse.class,new HttpBaseCallBack<VerifyCodeResponse>() 
		{
			@Override
			public void onSuccess(VerifyCodeResponse response) 
			{
				AppContext.verifyCodeResponse2=response;
				if(AppContext.verifyCodeResponse2!=null && !AppContext.verifyCodeResponse2.getUserpkid().equals(""))
				{
					ViewInject.toast("验证码已经发送,请注意查收");
					timeCount=new TimeCount(60000,1000);
					timeCount.start();
				}
				else
				{
					ViewInject.toast("获取验证码失败,请重新尝试");
				}
			}
			
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取验证码失败,错误代码:" + errorNo + ",错误信息:" + strMsg);   
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}

		});
    }
    
    //验证验证码，获得随机密码
    private void getTemporaryPassword()
    {
    	if(AppContext.verifyCodeResponse2==null)                                                                                                                            
     	{                                                                                                                                                         
     		ViewInject.toast("请先获取验证码");                                                                                                                    
     		return;                                                                                                                                                 
     	}                                                                                                                                                         
     	                                                                                                                                                         
     	String strMobile=this.m_reset_et_mobile.getText().toString().trim();                                                                                            
     	String strVerifyCode=this.m_reset_et_verifyCode.getText().toString().trim();                                                                                    
    	String strAuthorityid= AppContext.verifyCodeResponse2.getAuthorityid();
    	String strUserpkid=AppContext.verifyCodeResponse2.getUserpkid();
    	
     	if(StringUtils.isEmpty(strMobile))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入手机号码");                                                                                                
     		return;                                                                                                                                                 
     	} 
     	if(strMobile.length()!=11)                                                                                                                       
     	{                                                                                                                                                         
     		ViewInject.toast("手机号码格式不正确");                                                                                                                    
     		return;                                                                                                                                                 
     	}                                                                                                                                                         
     	
     	if(StringUtils.isEmpty(strVerifyCode))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入验证号码");                                                                                                
     		return;                                                                                                                                                 
     	}  
    
     	getTemporary(strMobile,strVerifyCode,strAuthorityid,strUserpkid);
    }
    
    private void getTemporary(String mobile,String verifyCode,String authorityid,String userpkid)
    {
    	final CustomProgressDialog dialog = new CustomProgressDialog(this.outsideAty,"正在获取新的密码,请稍等...",false);
		dialog.show();
		
    	final AppSimpleBack simple=(AppSimpleBack)this.outsideAty;
		HttpUtils.resetPasswordFromServer(this.outsideAty, mobile, verifyCode,authorityid,userpkid,GetTemporaryResponse.class,new HttpBaseCallBack<GetTemporaryResponse>()                                                           
		{                                                                                                                                                           
			@Override                                                                                                                                                 
			public void onSuccess(GetTemporaryResponse response)
			{   
				Bundle mBundle = new Bundle();   
				mBundle.putString("temporary", response.getUserpass());
				simple.changeFragment(SimpleBackPage.RESETPASSWORD2,mBundle);
				
			}                                                                                                                                                         
			                                                                                                                                                          
			@Override                                                                                                                                                 
			public void onFailure(int errorNo, String strMsg)                                                                                                         
			{                                                                                                                                                         
				ViewInject.toast("获取新的密码失败,错误代码:" + errorNo + ",错误信息:" + strMsg);                                                                                                               
			}  
			
			@Override                                                                                                                                                 
			public void onFinish() 
			{
				dialog.dismiss();
			}

		                                                                                                                                                             
		});                                                                                                                                                         
    }  
	
    
    
    private class TimeCount extends CountDownTimer 
    {

    	public TimeCount(long millisInFuture, long countDownInterval) 
    	{
			super(millisInFuture, countDownInterval);
		}

		@Override
    	public void onFinish() 
    	{
			m_reset_tv_validation_code.setText("重新获取");
			m_reset_tv_validation_code.setEnabled(true);
    	}
    	
    	@Override
    	public void onTick(long millisUntilFinished)
    	{
    		m_reset_tv_validation_code.setEnabled(false);
    		m_reset_tv_validation_code.setText(millisUntilFinished /1000+"秒后重新发送");
    	}
	}
    
    
   
}

	
