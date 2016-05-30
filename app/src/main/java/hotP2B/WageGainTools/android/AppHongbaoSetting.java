package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.UserpkidResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppHongbaoSetting extends AppTitleBar 
{

	@BindView(id=R.id.hongbao_setting_rb_on)
	private RadioButton m_hongbao_setting_rb_on;
	@BindView(id=R.id.hongbao_setting_rb_off)
	private RadioButton m_hongbao_setting_rb_off;
	
	@BindView(id=R.id.hongbao_setting_rg_percent)
	private RadioGroup m_hongbao_setting_rg_percent;
	
	@BindView(id=R.id.hongbao_setting_rb_percent_10)
	private RadioButton m_hongbao_setting_rb_percent_10;
	@BindView(id=R.id.hongbao_setting_rb_percent_20)
	private RadioButton m_hongbao_setting_rb_percent_20;
	@BindView(id=R.id.hongbao_setting_rb_percent_30)
	private RadioButton m_hongbao_setting_rb_percent_30;
	
	@BindView(id=R.id.hongbao_setting_cb_agree)
	private CheckBox m_hongbao_setting_cb_agree;
	@BindView(id=R.id.hongbao_setting_tv_agreement,click=true)
	private TextView m_hongbao_setting_tv_agreement;

	@BindView(id=R.id.hongbao_setting_btn_submit,click=true)
	private Button m_hongbao_setting_btn_submit;
	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_hongbao_setting);
	}

	@Override
    public void initData() 
    {
        super.initData();
    }

    @Override
	public void initTitleBar()
	{
	  this.mImgBack.setVisibility(View.VISIBLE);
	  this.mTvTitle.setText("礼拜红包设置");
	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
        
        int isSetting=this.getIntent().getIntExtra("issetting", 0);
        String percent=this.getIntent().getStringExtra("setamountrate");
        
        m_hongbao_setting_rb_on.setChecked(true);
        if(isSetting==1 && !StringUtils.isEmpty(percent))
        {
			if(percent.equals("0.1"))
			{
				m_hongbao_setting_rb_percent_10.setChecked(true);
			}
			else if(percent.equals("0.2"))
			{
				m_hongbao_setting_rb_percent_20.setChecked(true);
			}
			if(percent.equals("0.3"))
			{
				m_hongbao_setting_rb_percent_30.setChecked(true);
			}
        }
	
    }
    
    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}
    
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
	        case R.id.hongbao_setting_btn_submit:
	        {
	        	onSubmit();
	        }
	        break;
	        default:
	       break;
        }
    }

	private void onSubmit() 
	{
		
		String percent="";
		if(m_hongbao_setting_rb_on.isChecked())
		{
			if(!this.m_hongbao_setting_cb_agree.isChecked())
			{
				ViewInject.toast("请先阅读并同意《礼拜红包协议书》");
				return;
			}
			
			int percentId=this.m_hongbao_setting_rg_percent.getCheckedRadioButtonId();
			if(percentId<=0)
			{
				ViewInject.toast("请先选择转入比例");
				return;
			}
			switch(percentId)
			{
			case R.id.hongbao_setting_rb_percent_10:
				percent="0.1";
				break;
			case R.id.hongbao_setting_rb_percent_20:
				percent="0.2";
				break;
			case R.id.hongbao_setting_rb_percent_30:
				percent="0.3";
				break;
			}
		}
		else
		{
			percent="0";
		}
		
		if(StringUtils.isEmpty(percent))
		{
			ViewInject.toast("请先选择转入比例");
			return;
		}
		
		final CustomProgressDialog dialog=new CustomProgressDialog(this.aty,"正在提交设置数据,请稍等...",false);
		dialog.show();
		HttpUtils.setHongbao(this, AppContext.m_CurrentAccount.getUserpkid(), percent, UserpkidResponse.class, new HttpBaseCallBack<UserpkidResponse>()
		{

			@Override
			public void onSuccess(UserpkidResponse response) 
			{
				if(response!=null && !StringUtils.isEmpty(response.getUserpkid()))
				{
					AppHongbaoSetting.this.sendBroadcast(new Intent(AppConfig.HONGBAO_BROADCAST_ACTION));
					ViewInject.toast("设置成功");
					AppHongbaoSetting.this.finish();
				}
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}
	
		});
	}
}