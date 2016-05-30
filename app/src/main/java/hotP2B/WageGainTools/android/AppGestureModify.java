package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import hotP2B.WageGainTools.android.ui.widget.GestureLockView.OnGestureFinishListener;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.LoginResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils.xx;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppGestureModify extends AppGestureSetting {

	 private boolean bCheckOldPassword= true;
	 protected String strKey;
	

	 @Override
	 public void initWidget() 
	 {
		
	    super.initWidget(); 
	    this.mTvTitle.setText("修改手势密码");
	    this.m_lock_tv_tip.setText("请绘制旧的手势密码");
	    this.m_lock_tv_forget.setVisibility(View.VISIBLE);
	    
	    xx gestureInfo=GestureUtils.get(this,AppContext.m_CurrentAccount.getUserpkid());
		if(gestureInfo!=null)
		{
		   strKey=GestureUtils.Decode(gestureInfo.getXb(), 4);
		}
		   
		if(StringUtils.isEmpty(strKey))
		{
		    ViewInject.toast("没有检测到手势密码!!!");
			this.finish();
		}
		this.m_lock_gestureLockView.setOnGestureFinishListener(new ModifyGestureListener());
	 }
	 
	 @Override
    public void widgetClick(View v) 
	 {
		super.widgetClick(v);
		        
		switch (v.getId()) 
		{
		case R.id.lock_tv_forget:
			doLoginVerify();
			break;
		}
     }
	 
	
		
	@SuppressLint("InflateParams")
	private void doLoginVerify() 
	{
		View view=LayoutInflater.from(this).inflate(R.layout.aty_login2, null);
		final EditText et_password=(EditText)view.findViewById(R.id.login2_et_password);
		
		DialogUtils.showAlertDialog(this,"登录验证",view,new OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				String username=AppContext.m_CurrentAccount.getUsername();
				String password=et_password.getText().toString().trim();
				if(StringUtils.isEmpty(username)|| StringUtils.isEmpty(password))
				{
					ViewInject.toast("用户名和密码不能为空!!!");
					return;
				}
				final CustomProgressDialog customDialog=new CustomProgressDialog(AppGestureModify.this,"正在验证登录,请稍等...",false);
				customDialog.show();
				HttpUtils.loginFromServer(AppGestureModify.this, username, password, "0","",LoginResponse.class, new HttpBaseCallBack<LoginResponse>()
				{

					@Override
					public void onSuccess(LoginResponse response) 
					{
						ViewInject.toast("验证登录成功");
						m_lock_tv_forget.setVisibility(View.GONE);
						m_lock_tv_tip.setTextColor(Color.WHITE);
			        	m_lock_tv_tip.setVisibility(View.VISIBLE);
			        	m_lock_tv_tip.setText("请绘制新密码");
			            bCheckOldPassword=false;
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {
						ViewInject.toast("验证登录失败,错误代码:"+errorNo+",错误信息:"+strMsg);
					}

					@Override
					public void onFinish() 
					{
						customDialog.dismiss();
					}
					
				});
				
			}
		}, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{

				dialog.dismiss();
			}
		});
		
	}
	
	 
	 private class ModifyGestureListener implements OnGestureFinishListener
	 {

			@Override
			public void OnGestureFinish(boolean success, String key) 
			{
				if(success)
				{
					if(bCheckOldPassword)
					{
				        if (key.equals(strKey))
				        {
				        	m_lock_tv_forget.setVisibility(View.GONE);
				        	m_lock_tv_tip.setTextColor(Color.WHITE);
				        	m_lock_tv_tip.setVisibility(View.VISIBLE);
				        	m_lock_tv_tip.setText("旧密码正确,请绘制新密码");
				            bCheckOldPassword=false;
				            return;
				        }
				        m_lock_tv_tip.setTextColor(Color.parseColor("#FF2525"));
				        m_lock_tv_tip.setVisibility(0);
				        m_lock_tv_tip.setText("旧密码输入错误");
				        m_lock_tv_tip.startAnimation(animation);
				        return;
				      
					}
					if(bFirst)
					{
						adapter.setData(key);
						m_lock_tv_tip.setText("再次绘制手势密码");
						m_lock_gestureLockView.setKey(key);
						bFirst=false;
						return;
					}
					m_lock_tv_tip.setTextColor(Color.WHITE);
					m_lock_tv_tip.setVisibility(View.VISIBLE);
					m_lock_tv_tip.setText("密码正确");
					GestureUtils.save(AppGestureModify.this,AppContext.m_CurrentAccount.getUserpkid(),key);
					ViewInject.toast("手势密码修改成功");
					onFinish();
				}
				else
				{
					if(key.length()<4)
					{
						m_lock_tv_tip.setText("绘制的点数不能低于4个");
					}
					else
					{
						m_lock_tv_tip.setText("绘制的密码与上次不一致!");
					}
					m_lock_tv_tip.setTextColor(Color.parseColor("#FF2525"));
					m_lock_tv_tip.setVisibility(View.VISIBLE);
					m_lock_tv_tip.startAnimation(animation);
				}
		     }//end OnGestureFinish
	    	
	   }//end method
}
