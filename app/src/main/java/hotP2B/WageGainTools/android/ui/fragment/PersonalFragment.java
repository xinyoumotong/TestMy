package hotP2B.WageGainTools.android.ui.fragment;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.PersonalResponse;
import hotP2B.WageGainTools.android.bean.response.UserpkidResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;

public class PersonalFragment  extends TitleBarFragment  
{
	//空布局
	@BindView(id = R.id.personal_empty_layout)
	private EmptyLayout m_personal_empty_layout;

	//真实姓名
	@BindView(id=R.id.personal_tv_realname)
	private TextView m_personal_tv_realname;
	//身份证号
	@BindView(id=R.id.personal_tv_idNumber_show)
    private TextView m_personal_tv_idNumber_show;
	@BindView(id=R.id.personal_tv_idNumber_hide)
    private TextView m_personal_tv_idNumber_hide;
	//手机号码
	@BindView(id=R.id.personal_tv_phoneNumber)
	private TextView personal_tv_phoneNumber;


	private PersonalResponse personalResponse=null;

	
	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) 
	{
		View view = View.inflate(this.outsideAty, R.layout.frag_personal, null);
		return view;
	}

	@Override
	protected void setActionBarRes(ActionBarRes actionBarRes) 
	{
		actionBarRes.titleId = R.string.personal;
		actionBarRes.backImageId = R.mipmap.titlebar_back;
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

		m_personal_empty_layout.setOnLayoutClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
	
				refresh();
			}
		});
		
		refresh();// 刷新数据
	}
	
	 @Override
	 public void onResume() 
	 {
		super.onResume();
	 }

	@Override
	protected void widgetClick(View v) 
	{
		super.widgetClick(v);
	}
	
	@Override
	public void onBackClick() 
	{
		this.outsideAty.finish();
	}
	
	@Override
	public void onMenuClick() 
	{
	   super.onMenuClick();
//	   onModify();
	}
	
//	@SuppressLint("InflateParams")
//	private void onModify()
//	{
//		if(personalResponse==null)return;
//		if(!personalResponse.getAuthentication().equals("1") || !personalResponse.getAuthentcustid().equals("0"))return;
//
//		Builder builder = new Builder(this.outsideAty);
//		builder.setTitle("修改个人信息");
//		builder.setCancelable(true);
//
//		View view=LayoutInflater.from(this.outsideAty).inflate(R.layout.view_modify_personal, null);
//		final EditTextWithDel et_realname=(EditTextWithDel)view.findViewById(R.id.personal_et_realname);
//		et_realname.setText(this.m_personal_tv_realname.getText().toString().trim());
//		ImageView personal_iv_realname_clear=(ImageView)view.findViewById(R.id.personal_iv_realname_clear);
//		et_realname.setImageView(personal_iv_realname_clear);
//		final EditTextWithDel et_idNumber=(EditTextWithDel)view.findViewById(R.id.personal_et_idNumber);
//		et_idNumber.setText(this.m_personal_tv_idNumber_hide.getText().toString().trim());
//		ImageView personal_iv_idNumber_clear=(ImageView)view.findViewById(R.id.personal_iv_idNumber_clear);
//		et_idNumber.setImageView(personal_iv_idNumber_clear);
//		et_idNumber.setSelection(et_idNumber.length());
//		et_idNumber.requestFocus();
//		builder.setView(view);
//
//		builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				dialog.dismiss();
//				doModify(et_realname.getText().toString().trim(),et_idNumber.getText().toString().trim());
//			}
//		});
//		builder.setNegativeButton(R.string.cancel, null);
//
//		builder.show();
//
//	}
	
	private void doModify(final String realname,final String idNumber)
	{
		if(StringUtils.isEmpty(realname) || StringUtils.isEmpty(idNumber))
	    {
			ViewInject.toast("真实姓名或身份证号不能为空");
			return;	
	    }
			
		if(idNumber.length()!=18)
      	{
      		ViewInject.toast("身份证号长度必须为18位");
      		return;
      	}     	
        
		if(idNumber.contains("*"))
		{
			ViewInject.toast("身份证号不能含有*字符");
      		return;
		}
      
	
	    final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在修改信息,请稍等...",false);
    	dialog.show();
		
		HttpUtils.modifyPersonalFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(), realname, idNumber,UserpkidResponse.class, new HttpBaseCallBack<UserpkidResponse>()
		{
			private boolean IS_MODIFY_SUCEESS;

			@Override
			public void onSuccess(UserpkidResponse loginResponse) 
			{
				IS_MODIFY_SUCEESS = true;
//				fillUI(realname,idNumber,"");
				ViewInject.toast("修改成功");
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("修改个人信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
				IS_MODIFY_SUCEESS = false;
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
				if (IS_MODIFY_SUCEESS) {
					refresh();
				}
			}
		});
	}
	

	private void refresh()
	{
		m_personal_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
		HttpUtils.getPersonalFromServer(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid(),PersonalResponse.class,new HttpBaseCallBack<PersonalResponse>()
		{

			@Override
			public void onSuccess(PersonalResponse response) 
			{
				m_personal_empty_layout.dismiss();
				personalResponse=response;
				if(personalResponse.getAuthentication().equals("1") && personalResponse.getAuthentcustid().equals("0"))
				{
					outsideAty.mImgMenu.setImageResource(R.mipmap.titlebar_edit);
					outsideAty.mImgMenu.setVisibility(View.VISIBLE);
				}
				else
				{
					outsideAty.mImgMenu.setVisibility(View.GONE);
				}
				fillUI( personalResponse.getUsertruename(),personalResponse.getUseridnumber(),personalResponse.getUsermobile());
			
			}
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取个人信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
				m_personal_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
			}

			@Override
			public void onFinish() 
			{

			}
			
		});
	}
	
	private void fillUI(String realname,String idNumber,String mobile)
	{
		this.m_personal_tv_realname.setText(realname == null ? "" : realname);
		this.m_personal_tv_idNumber_hide.setText(idNumber == null ? "" : idNumber);
		this.personal_tv_phoneNumber.setText(mobile == null ? "" : (mobile.substring(0,3) + "****" + mobile.substring(7)));
		this.m_personal_tv_idNumber_show.setText(idNumber == null ? "" : (idNumber.substring(0, 3)+"*** **** ****"+idNumber.substring(idNumber.length()-4)));
		UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_REALNAME, realname);//更新用户信息
	}
	

}
