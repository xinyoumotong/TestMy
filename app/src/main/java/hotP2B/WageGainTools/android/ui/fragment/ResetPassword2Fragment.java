package hotP2B.WageGainTools.android.ui.fragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;

/**
 * 忘记密码界面
 * 
 */
public class ResetPassword2Fragment extends TitleBarFragment {

	@BindView(id = R.id.reset2_tv_newpassword)
    private TextView m_reset2_tv_newpassword;
	
	@BindView(id = R.id.reset2_btn_copy, click = true)
	private TextView m_reset2_btn_copy;
	
	@BindView(id = R.id.reset2_btn_goLogin, click = true)
	private Button m_reset2_btn_goLogin;
	
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) 
	{
		View view = View.inflate(outsideAty, R.layout.frag_reset_password2, null);
		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
		actionBarRes.title ="忘记密码";
		actionBarRes.backImageId=R.mipmap.titlebar_back;

	}

	@Override
	protected void initData() 
	{
		super.initData();
		this.setValidateAccount(false);
		this.outsideAty.mRlTitleBar.setVisibility(View.VISIBLE);

	}

	@Override
	public void onBackClick() 
	{
		AppSimpleBack simple=(AppSimpleBack)this.outsideAty;
	    simple.changeFragment(SimpleBackPage.LOGIN);
	}

	@Override
	protected void initWidget(View parentView) 
	{
		Bundle bundle=this.getArguments();
		if(bundle!=null)
		{
			String temporary = bundle.getString("temporary");
			if(!StringUtils.isEmpty(temporary))
			{
				m_reset2_tv_newpassword.setText(temporary);
			}
		}

	}

	// 给控件挂载事件
	@Override
	public void widgetClick(View v) 
	{
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.reset2_btn_copy:
			copy(m_reset2_tv_newpassword.getText().toString(), outsideAty);
			ViewInject.toast("密码已经复制到剪贴板");
			break;
		case R.id.reset2_btn_goLogin:
			onBackClick();
			break;
        default:
			break;
		}
	}
	

	
	/** 
	* 实现文本复制功能 
	* @param content 
	*/  
	@SuppressWarnings("deprecation")
	public void copy(String content, Context context)  
	{  
		// 得到剪贴板管理器  
		ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		cmb.setText(content.trim());  
	}
	
	/** 
	* 实现粘贴功能 
	* @param context 
	* @return 
	*/  
	@SuppressWarnings("deprecation")
	public static String paste(Context context)  
	{  
		// 得到剪贴板管理器  
		ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		return cmb.getText().toString().trim();  
	} 
}

	
