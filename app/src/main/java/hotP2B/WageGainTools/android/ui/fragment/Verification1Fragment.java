
package hotP2B.WageGainTools.android.ui.fragment;

import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithPassword;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.RegisterResponse;
import hotP2B.WageGainTools.android.bean.response.VerifyCodeResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户注册界面
 */
public class Verification1Fragment extends TitleBarFragment implements TextWatcher 
{

    @BindView(id = R.id.verification1_et_mobile)
    private EditTextWithDel m_verification1_et_mobile;
    @BindView(id = R.id.verification1_et_verifyCode)
    private EditTextWithDel m_verification1_et_verifyCode;
    @BindView(id = R.id.verification1_et_password)
    private EditTextWithPassword m_verification1_et_password;
    @BindView(id = R.id.verification1_et_password2)
    private EditTextWithPassword m_verification1_et_password2;
    
    @BindView(id = R.id.verification1_iv_mobile_clear)
    private ImageView m_verification1_iv_mobile_clear;
    @BindView(id = R.id.verification1_iv_pwd_clear)
    private ImageView m_verification1_iv_pwd_clear;
    @BindView(id = R.id.verification1_iv_pwd2_clear)
    private ImageView m_verification1_iv_pwd2_clear;
    
    
	@BindView(id = R.id.verification1_tv_validation_code, click = true)
	private TextView m_verification1_tv_validation_code;
    @BindView(id = R.id.verification1_btn_register, click = true)
    private Button m_verification1_btn_register;
    
    private TimeCount timeCount=null;
    private String strUserpkid="";
 
    
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) 
    {
        View view = View.inflate(outsideAty, R.layout.frag_verification1, null);
        return view;
    }

	@Override
    protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
        actionBarRes.title = getString(R.string.verify_phone);
    	actionBarRes.backImageId=R.mipmap.titlebar_back;
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
		
		this.m_verification1_et_mobile.setImageView(m_verification1_iv_mobile_clear);
		this.m_verification1_et_mobile.addTextChangedListener(this);
		this.m_verification1_et_verifyCode.addTextChangedListener(this);
		this.m_verification1_et_password.setImageView(m_verification1_iv_pwd_clear);
		this.m_verification1_et_password.addTextChangedListener(this);
		this.m_verification1_et_password2.setImageView(m_verification1_iv_pwd2_clear);
		this.m_verification1_et_password2.addTextChangedListener(this);


	}
	
	@Override
	public void onBackClick() 
	{
	   AppSimpleBack simple=(AppSimpleBack)this.outsideAty;
	   simple.changeFragment(SimpleBackPage.LOGIN);
	}
	    
  
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        case R.id.verification1_tv_validation_code:
        	getVerifyCode();
            break;
        case R.id.verification1_btn_register:
        	checkRegister();
            break; 
        }
    }
    
    //请求发送验证码
    private void getVerifyCode()
    {
    	final String strMobile=this.m_verification1_et_mobile.getText().toString().trim();
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
		HttpUtils.getVerifyCodeFromServer(this.outsideAty,strMobile,true,VerifyCodeResponse.class, new HttpBaseCallBack<VerifyCodeResponse>() 
		{
			@Override
			public void onSuccess(VerifyCodeResponse response) 
			{
				AppContext.verifyCodeResponse=response;
				if(AppContext.verifyCodeResponse!=null && !StringUtils.isEmpty(AppContext.verifyCodeResponse.getUserpkid()))
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
			public void onFinish() {
				dialog.dismiss();
				
			}

		});
    }
    private void checkRegister() 
    {
    	if(AppContext.verifyCodeResponse==null)                                                                                                                            
     	{                                                                                                                                                         
     		ViewInject.toast("请先获取验证码");                                                                                                                    
     		return;                                                                                                                                                 
     	}                                                                                                                                                         
     	                                                                                                                                                         
     	String strMobile=this.m_verification1_et_mobile.getText().toString().trim();                                                                                            
     	String strVerifyCode=this.m_verification1_et_verifyCode.getText().toString().trim();                                                                                    
     	String strPassword=this.m_verification1_et_password.getText().toString().trim();       
    	String strPassword2=this.m_verification1_et_password2.getText().toString().trim();    
    	String strAuthorityid= AppContext.verifyCodeResponse.getAuthorityid();
    	String strUserpkid=AppContext.verifyCodeResponse.getUserpkid();
     	if(StringUtils.isEmpty(strMobile))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入手机号码");                                                                                                
     		return;                                                                                                                                                 
     	} 
     	if(StringUtils.isEmpty(strVerifyCode))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入验证号码");                                                                                                
     		return;                                                                                                                                                 
     	}  
     	if(StringUtils.isEmpty(strPassword))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入登录密码");                                                                                                
     		return;                                                                                                                                                 
     	}  
     	if(StringUtils.isEmpty(strPassword2))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入确认密码");                                                                                                
     		return;                                                                                                                                                 
     	}  
     	if(strMobile.length()!=11)                                                                                                                       
     	{                                                                                                                                                         
     		ViewInject.toast("手机号码格式不正确");                                                                                                                    
     		return;                                                                                                                                                 
     	}                                                                                                                                                         
     	if(strPassword.length()<6 ||strPassword.length()>16 )                                                                                                                                
     	{                                                                                                                                                         
     		ViewInject.toast("登录密码长度位数为6-16");                                                                                                       
     		return;                                                                                                                                                 
     	}   
     	
    	if(!strPassword.equals(strPassword2))                                                                                                                                
     	{                                                                                                                                                         
     		ViewInject.toast("确认密码和登录密码不一致");                                                                                                       
     		return;                                                                                                                                                 
     	} 
    	doRegister(strMobile,strPassword,strVerifyCode,strAuthorityid,strUserpkid);
    }
    //注册
    private void doRegister(String mobile,String pwd,String verifyCode,String authorityid,String userpkid)
    {  
    	final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在注册,请稍等...",false);
    	dialog.show();
		HttpUtils.registerFromServer(this.outsideAty, mobile,pwd, verifyCode,authorityid,userpkid,RegisterResponse.class,new HttpBaseCallBack<RegisterResponse>()                                                           
		{                                                                                                                                                           
			@Override                                                                                                                                                 
			public void onSuccess(RegisterResponse registerResponse)                                                                                                                      
			{   
		        if(registerResponse!=null && !registerResponse.getUserpkid().equals(""))                                                                            
		        {                                                                                                                                                                                                                                       
			        ViewInject.toast("注册成功"); 
			        Verification1Fragment.this.strUserpkid=registerResponse.getUserpkid();    
			        if(registerResponse.getAuthentication()==0)
			        {
				      gotoNext();   
			        }
			        else
			        {
			          gotoLogin();
			        }
		        }                                                                                                                                                   
			}                                                                                                                                                         
			                                                                                                                                                          
			@Override                                                                                                                                                 
			public void onFailure(int errorNo, String strMsg)                                                                                                         
			{                                                                                                                                                         
				ViewInject.toast("注册失败,错误代码:" + errorNo + ",错误信息:" + strMsg);                                                                                                               
			}  
			
			@Override                                                                                                                                                 
			public void onFinish() 
			{
				dialog.dismiss();
			}
			
		                                                                                                                                                             
		});                                                                                                                                                         
    }      
    
    private void gotoLogin()
    {
  	     new Handler().postDelayed(new Runnable()
    	 {
    	   @Override
    	   public void run()
    	   {
    		   AppSimpleBack simple=(AppSimpleBack)Verification1Fragment.this.outsideAty;
    	 	   simple.changeFragment(SimpleBackPage.LOGIN);   
    	 
    	   }
    	 },1000);
    }
		                                                                                                                                                             
    private void gotoNext()
    {
    	new Handler().postDelayed(new Runnable()
    	{
    	   @Override
    	   public void run()
    	   {
    		  Bundle bundle=new Bundle();
    		  bundle.putString("userpkid",Verification1Fragment.this.strUserpkid);
    		  AppSimpleBack simple=(AppSimpleBack)Verification1Fragment.this.outsideAty;
    	 	  simple.changeFragment(SimpleBackPage.VERIFY2,bundle);   
    	   }
    	},1000);
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
			m_verification1_tv_validation_code.setText("重新获取");
			m_verification1_tv_validation_code.setEnabled(true);
    	}
    	
    	@Override
    	public void onTick(long millisUntilFinished)
    	{
    		m_verification1_tv_validation_code.setEnabled(false);
    		m_verification1_tv_validation_code.setText(millisUntilFinished /1000+"秒后重新发送");
    	}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void afterTextChanged(Editable s) 
	{
	   String mobile = m_verification1_et_mobile.getText().toString().trim();
       String verifyCode = m_verification1_et_verifyCode.getText().toString().trim();
	   String pwd = m_verification1_et_password.getText().toString().trim();
       String pwd2 = m_verification1_et_password2.getText().toString().trim();
	   if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(verifyCode) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(pwd2)) 
	   {
		   this.m_verification1_btn_register.setEnabled(false);
	   } 
	   else 
	   {
		   this.m_verification1_btn_register.setEnabled(true);
	   }
		
	}


}

