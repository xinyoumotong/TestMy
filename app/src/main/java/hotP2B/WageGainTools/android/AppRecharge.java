package hotP2B.WageGainTools.android;

import java.text.DecimalFormat;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import hotP2B.WageGainTools.android.dialog.KeyboardDialog;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils.xx;

public class AppRecharge extends AppTitleBar
{
	//金额
	@BindView(id = R.id.recharge_et_money)
	private EditText m_recharge_et_money;
	//下一步
	@BindView(id = R.id.recharge_btn_next, click = true)
	private Button m_recharge_btn_next;
	
    private String strCash="";
	private KeyboardDialog keyboardDialog=null;
	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_recharge);
	}
	
	@Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText(R.string.recharge);
	}
	
	@Override
	public void initWidget() 
	{
	   super.initWidget();
	   
	   keyboardDialog=new KeyboardDialog(this);
//	   this.keyboardDialog.fillEdit(this.m_recharge_et_money, 1);
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
		case R.id.recharge_btn_next:
 			check();
 			break;
 			
		   default:
			   break;
		}
     }
	
	private void check() 
	{
		String money=this.m_recharge_et_money.getText().toString().trim();
		Double dMoney;
	
		if(StringUtils.isEmpty(money))
		{
			ViewInject.toast("请先输入充值金额");
			return;
		}
		
	    try
	    {
	    	dMoney=Double.parseDouble(money);
	    }
	    catch(Exception e)
	    {
	    	ViewInject.toast("充值金额不合法");
	    	return;
	    }
	    
	    if(!CommonUtils.isCash(money))
	    {
	    	ViewInject.toast("请输入正确的金额,小数点后最多保留两位！");
	        return;
	    }
	    
	    if(dMoney<1)
		{
			ViewInject.toast("充值金额必须大于等于1元");
	    	return;
		}
		
        DecimalFormat  df=new DecimalFormat("#.00");   
	    strCash=df.format(dMoney);
	    
	    xx gestureInfo=GestureUtils.get(this,AppContext.m_CurrentAccount.getUserpkid());
	    if(gestureInfo==null)
	    {
	    	DialogUtils.showAlertDialog(aty, "提示信息", "检测到你没有设置手势密码,去设置？",new OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					dialog.dismiss();
					Intent intent=new Intent(AppRecharge.this,AppGestureSetting.class);
					AppRecharge.this.startActivity(intent);
					
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
	    else
	    {
	    	Intent intent=new Intent(this,AppGestureVerify.class);
		    this.startActivityForResult(intent, 0);
	    }

	}
	
	@Override
	public void onActivityResult(final int requestCode, final int resultCode,final Intent imageReturnIntent) 
	{
	     if (resultCode != Activity.RESULT_OK) return;
	     switch (requestCode) 
	     {
	     case 0:
	    	 doRecharge(strCash);
	    	 break;
	    default:
	    	break;
	     }
	}
	
	private void doRecharge(String strCash)
	{
	   if(AppContext.m_CurrentAccount==null)return;
       if(!StringUtils.isEmpty(strCash))
       {
		  CommonUtils.recharge(this, AppContext.m_CurrentAccount.getUserpkid(),strCash);
		  this.finish();
       }
	}

}
