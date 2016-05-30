package hotP2B.WageGainTools.android.ui.fragment;

import org.kymjs.kjframe.ui.BindView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppGestureModify;
import hotP2B.WageGainTools.android.AppGestureSetting;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils.xx;

public class AccountSafeFragment extends TitleBarFragment  
{
	//实名认证
	@BindView(id=R.id.accountsafe_rl_realauth_no,click=true)
	private RelativeLayout m_accountsafe_rl_realauth_no;
	@BindView(id=R.id.accountsafe_rl_realauth_ok,click=true)
	private RelativeLayout m_accountsafe_rl_realauth_ok;
	
	
	//登录密码
	@BindView(id=R.id.accountsafe_rl_changePwd,click=true)
	private RelativeLayout m_accountsafe_rl_changePwd;
	//设置手势密码
	@BindView(id=R.id.accountsafe_rl_setGesturePwd,click=true)
	private RelativeLayout m_accountsafe_rl_setGesturePwd;
	//修改手势密码
	@BindView(id=R.id.accountsafe_rl_changeGesturePwd,click=true)
	private RelativeLayout m_accountsafe_rl_changeGesturePwd;
	
	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) 
	{
		View view = View.inflate(this.outsideAty, R.layout.frag_accountsafe, null);
		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
		actionBarRes.titleId = R.string.my_item_accountSafe;
		actionBarRes.backImageId = R.mipmap.titlebar_back;
	}

	@Override
	protected void initData() 
	{
		super.initData();
	}
	
	@Override
	protected void widgetClick(View v) 
	{
		super.widgetClick(v);
		
		switch (v.getId()) 
		{
		 case R.id.accountsafe_rl_realauth_no:
	     {
			 AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.MODIFYPERSONAL);
//	         if(AppContext.m_CurrentAccount.getReallyidentity().equals("0"))
//	         {
//	        	Bundle bundle=new Bundle();
//	        	bundle.putString("userpkid",AppContext.m_CurrentAccount.getUserpkid());
//	        	AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.VERIFY2,bundle,true);
//	         }
		 }
	     break;
		case R.id.accountsafe_rl_realauth_ok:
		{
		   AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.PERSONAL); 
		}
		break;
		case R.id.accountsafe_rl_changePwd:
		{
			AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.CHANGEPASSWORD);
		}
		break;
		case R.id.accountsafe_rl_setGesturePwd:
		{
			this.outsideAty.showActivity(this.outsideAty, AppGestureSetting.class);

		}
		break;
		case R.id.accountsafe_rl_changeGesturePwd:
		{
		   this.outsideAty.showActivity(this.outsideAty, AppGestureModify.class);
		}
		break;
		default:
			break;
		}
	}
	
	
	 @Override
	 public void onResume() 
	 {
		super.onResume();

		 if (AppContext.m_CurrentAccount.getAuthentcustid().equals("1")) {
			this.m_accountsafe_rl_realauth_no.setVisibility(View.GONE);
			this.m_accountsafe_rl_realauth_ok.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_accountsafe_rl_realauth_no.setVisibility(View.VISIBLE);
			this.m_accountsafe_rl_realauth_ok.setVisibility(View.GONE);
		}
		
		xx gestureInfo=GestureUtils.get(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid());
		if(gestureInfo!=null)
		{
			this.m_accountsafe_rl_setGesturePwd.setVisibility(View.GONE);
			this.m_accountsafe_rl_changeGesturePwd.setVisibility(View.VISIBLE);
		}
		else
		{
			this.m_accountsafe_rl_setGesturePwd.setVisibility(View.VISIBLE);
			this.m_accountsafe_rl_changeGesturePwd.setVisibility(View.GONE);
		}
	 }

	
	@Override
	public void onBackClick() 
	{
		this.outsideAty.finish();
	}
}
