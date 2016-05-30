
package hotP2B.WageGainTools.android.ui.fragment;

import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.RealAuthResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 实名认证界面
 */
public class Verification2Fragment extends TitleBarFragment 
{
	  @BindView(id = R.id.verification2_et_mobile)
	  private EditTextWithDel m_verification2_et_mobile;
	  @BindView(id = R.id.verification2_et_realname)
	  private EditTextWithDel m_verification2_et_realname;
	  @BindView(id = R.id.verification2_et_idNumber)
	  private EditTextWithDel m_verification2_et_idNumber;
	    
	  @BindView(id = R.id.verification2_iv_mobile_clear)
	  private ImageView m_verification2_iv_mobile_clear;
	  @BindView(id = R.id.verification2_iv_realname_clear)
	  private ImageView m_verification2_iv_realname_clear;
	  @BindView(id = R.id.verification2_iv_idNumber_clear)
	  private ImageView m_verification2_iv_idNumber_clear;
	  

	    
      @BindView(id = R.id.verification2_btn_skip, click = true)
	  private Button m_verification2_btn_skip;
		
	  @BindView(id = R.id.verification2_btn_auth, click = true)
	  private Button m_verification2_btn_register;
	  
	  
	  private String strUserpkid="";

      @Override
      protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) 
      {
          View view = View.inflate(outsideAty, R.layout.frag_verification2, null);
          return view;
      }

      @Override
      protected void setActionBarRes(ActionBarRes actionBarRes) 
  	  {
          actionBarRes.title = getString(R.string.realauth);
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
          
  		  Bundle bundle=null;
  		  if(AppContext.m_CurrentAccount!=null && AppContext.m_CurrentAccountIsLogin)
  		  {
  			  this.m_verification2_btn_skip.setVisibility(View.GONE);
  			  bundle=((AppSimpleBack)this.outsideAty).getBundleData();
  		  }
  		  else
  		  {
  			  bundle= this.getArguments();
  		  }
  		  
 		  if(bundle!=null)
	      {
	  		 strUserpkid=bundle.getString("userpkid");
	      }
  		  
  		  this.m_verification2_et_mobile.setImageView(this.m_verification2_iv_mobile_clear);
  		  this.m_verification2_et_realname.setImageView(this.m_verification2_iv_realname_clear);
  		  this.m_verification2_et_idNumber.setImageView(this.m_verification2_iv_idNumber_clear);
  		 
  		 

      }
 
	  @Override
      public void widgetClick(View v) 
      {
        super.widgetClick(v);
        switch (v.getId()) 
        {
        case R.id.verification2_btn_skip:
        	gotoLogin();
            break;
        case R.id.verification2_btn_auth:
        	doRealAuth();
        	break;
        default:
        	break;
        }
      }
      
  	  @Override
  	  public void onBackClick() 
  	  {
  		  if(AppContext.m_CurrentAccount!=null && AppContext.m_CurrentAccountIsLogin)
  		  {
  			 this.outsideAty.finish();
  		  }
  		  else
  		  {
  			 gotoLogin();
  		  }
  	   
  	  }
  	    
   
      private void doRealAuth()
      {
    	  
      	String strMobile=this.m_verification2_et_mobile.getText().toString().trim();
      	String strRealName=this.m_verification2_et_realname.getText().toString().trim();
      	String strIdNumber=this.m_verification2_et_idNumber.getText().toString().trim();
      	
      	if(StringUtils.isEmpty(strMobile) || StringUtils.isEmpty(strIdNumber) || StringUtils.isEmpty(strRealName))
      	{
      		ViewInject.toast("请先输入手机号码/真实姓名/身份证号");
      		return;
      	}
      	if(strMobile.length()!=11)
      	{
      		ViewInject.toast("手机号码格式不正确");
      		return;
      	}
      	if(strIdNumber.length()!=18)
      	{
      		ViewInject.toast("身份证号长度必须为18位");
      		return;
      	}     	

      	final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在实名认证,请稍等...",false);
		dialog.show();
  		HttpUtils.realauthFromServer(this.outsideAty, strMobile, strRealName, strIdNumber,strUserpkid,RealAuthResponse.class,new HttpBaseCallBack<RealAuthResponse>() 
  		{
  			@Override
  			public void onSuccess(RealAuthResponse realauthResponse) 
  			{
  				 if(realauthResponse!=null && !realauthResponse.getUserpkid().equals(""))
  				 {
  			      
  			        if(AppContext.m_CurrentAccount!=null && AppContext.m_CurrentAccountIsLogin)
   		  		    {
   		  			   UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_REALAUTH, "1");
   		  		    }
  			        ViewInject.toast("实名认证成功");
  			        onBackClick();
  				 }
  				 else
  				 {
  					 ViewInject.toast("实名认证失败");
  				 }	
  			}
  			
  			@Override
  			public void onFailure(int errorNo, String strMsg) 
  			{
  				ViewInject.toast("实名认证失败,错误代码:" + errorNo + ",错误信息:" + strMsg);  
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
      		  AppSimpleBack simple=(AppSimpleBack)Verification2Fragment.this.outsideAty;
      	 	  simple.changeFragment(SimpleBackPage.LOGIN);   
      	 
      	   }
      	 },1000);
      }
  
}
