package hotP2B.WageGainTools.android.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import hotP2B.WageGainTools.android.AppHongbaoSetting;
import hotP2B.WageGainTools.android.R;

public class CustomHongbaoDialog extends CustomDialog {

	public CustomHongbaoDialog(Context context) {
		super(context);
	}

	@Override
	public int getLayoutId() 
	{
		return R.layout.dialog_hongbao;
	}
	
	@Override
	public void initView() 
	{
		this.findViewById(R.id.hongbao_setting_iv_close).setOnClickListener(this);
		this.findViewById(R.id.hongbao_setting_btn_setting).setOnClickListener(this);

	}
	
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.hongbao_setting_btn_setting:
		{
			this.context.startActivity(new Intent(this.context,AppHongbaoSetting.class));
		}
		break;
		default:
			break;
		}
		super.onClick(v);
	}
}