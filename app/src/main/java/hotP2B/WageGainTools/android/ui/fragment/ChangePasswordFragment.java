package hotP2B.WageGainTools.android.ui.fragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithPassword;
import hotP2B.WageGainTools.android.bean.response.ChangePasswordResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;

/**
 * 修改密码界面
 * 
 */
public class ChangePasswordFragment extends TitleBarFragment implements TextWatcher 
{

	@BindView(id = R.id.change_et_oldpassword)
    private EditTextWithPassword m_change_et_oldpassword;
	@BindView(id = R.id.change_et_newpassword)
    private EditTextWithPassword m_change_et_newpassword;
	@BindView(id = R.id.change_et_newpassword2)
    private EditTextWithPassword m_change_et_newpassword2;
	
	@BindView(id = R.id.change_iv_oldpassword_eye)
    private ImageView m_change_iv_oldpassword_eye;
	@BindView(id = R.id.change_iv_newpassword_eye)
    private ImageView m_change_iv_newpassword_eye;
	@BindView(id = R.id.change_iv_newpassword2_eye)
    private ImageView m_change_iv_newpassword2_eye;
	
	
	@BindView(id = R.id.change_btn_submit, click = true)
    private Button m_change_btn_submit;
	

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) 
	{
		View view = View.inflate(outsideAty, R.layout.frag_change_password, null);
		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
		actionBarRes.title = getString(R.string.changeLoginPwd);
		actionBarRes.backImageId=R.mipmap.titlebar_back;

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
		this.m_change_btn_submit.setEnabled(false);
		this.m_change_et_oldpassword.addTextChangedListener(this);
		this.m_change_et_newpassword.addTextChangedListener(this);
		this.m_change_et_newpassword2.addTextChangedListener(this);
		
		this.m_change_et_oldpassword.setImageView(this.m_change_iv_oldpassword_eye);
		this.m_change_et_newpassword.setImageView(this.m_change_iv_newpassword_eye);
		this.m_change_et_newpassword2.setImageView(this.m_change_iv_newpassword2_eye);

	}
	
	@Override
	public void onBackClick() 
	{
	   this.outsideAty.finish();
	}
	    
	
	// 给控件挂载事件
	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.change_btn_submit:
			updatePassword();
			break;
        default:
			break;
		}
	}

	// 检查新密码规则合法性
	private boolean checkNewPwd() 
	{

		String oldpwd = m_change_et_oldpassword.getText().toString();
		String newpwd = m_change_et_newpassword.getText().toString();
		String newpwd2 = m_change_et_newpassword2.getText().toString();
		if(!(oldpwd.length()>=6 && oldpwd.length() <= 16))
		{
			ViewInject.toast("原密码长度不对");
			return false;
		}
		if(!(newpwd.length()>=6 && newpwd.length() <= 16))
		{
			ViewInject.toast("新密码长度不对");
			return false;
		}
		if(newpwd.equals(oldpwd))
		{
			ViewInject.toast("新密码和旧密码不能相同");
			return false;
		}
		
		if(!newpwd.equals(newpwd2))
		{
			ViewInject.toast("两次输入新密码不一致");
			return false;
		}
        
		return true;

	}

	private void updatePassword()
	{
		if (checkNewPwd()) 
		{
			final String oldpwd = m_change_et_oldpassword.getText().toString();
			final String newpwd = m_change_et_newpassword.getText().toString();
			final CustomProgressDialog dialog = new CustomProgressDialog(this.outsideAty,"正在修改密码,请稍等...",false);
			dialog.show();
			
			HttpUtils.changePwdFromServer(this.outsideAty, oldpwd, newpwd, AppContext.m_CurrentAccount.getUserpkid(),ChangePasswordResponse.class,new HttpBaseCallBack<ChangePasswordResponse>()
			{
				@Override
				public void onSuccess(ChangePasswordResponse changepasswordResponse) 
				{
					ViewInject.toast("密码修改成功");
					AppContext.logout(outsideAty);
				}
				@Override
				public void onFailure(int errorNo, String strMsg) 
				{
					ViewInject.toast("修改密码失败,错误代码:"+errorNo+",错误信息:"+strMsg);
				}

				@Override
				public void onFinish() 
				{
					dialog.dismiss();
					
				}
			});
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	
		
	}

	@Override
	public void afterTextChanged(Editable s) 
	{
		String oldpwd = this.m_change_et_oldpassword.getText().toString();
		String newpwd = this.m_change_et_newpassword.getText().toString();
	    String newpwd2 =this.m_change_et_newpassword2.getText().toString();
		if (StringUtils.isEmpty(oldpwd) || StringUtils.isEmpty(newpwd) ||  StringUtils.isEmpty(newpwd2)) 
		{
		   this.m_change_btn_submit.setEnabled(false);
		} 
		else 
		{
		   this.m_change_btn_submit.setEnabled(true);
		}
	}

	
}

	
