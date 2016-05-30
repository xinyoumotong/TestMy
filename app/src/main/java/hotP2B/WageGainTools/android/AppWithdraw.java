package hotP2B.WageGainTools.android;

import java.text.DecimalFormat;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import hotP2B.WageGainTools.android.dialog.KeyboardDialog;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils.xx;

public class AppWithdraw extends AppTitleBar {

	//余额
	@BindView(id = R.id.withdraw_tv_balance)
	private TextView m_withdraw_tv_balance;

	//提现金额
	@BindView(id = R.id.withdraw_et_money)
	private EditText m_withdraw_et_money;
		
	//下一步
	@BindView(id = R.id.withdraw_btn_next, click = true)
	private Button m_withdraw_btn_next;
	
	private String strCash="";
	
	private KeyboardDialog keyboardDialog=null;
      
		
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_withdraw);
	}
	
	@Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText(R.string.withdraw);
	}
	
	@Override
	public void initWidget() 
	{
	   super.initWidget();
	   
	   keyboardDialog=new KeyboardDialog(this);
//	   this.keyboardDialog.fillEdit(this.m_withdraw_et_money, 1);
	  
	   Intent intent=this.getIntent();
	   if(intent!=null)
	   {
		   Bundle bundle=intent.getExtras();
		   this.m_withdraw_tv_balance.setText(bundle.getString("balance"));
	   }
	}
	
	 @Override
	 public void onBackClick() 
	 {
		super.onBackClick();
		this.finish();	
	 }
	 
	 @Override
	 public void onMenuClick() 
	 {
		 super.onMenuClick();
		 
	 }
	 
	 @Override
 	 public void widgetClick(View v) 
     {
 		super.widgetClick(v);
 		switch (v.getId()) 
 		{
 		case R.id.withdraw_btn_next:
 			check();
 			break;
 			
 		   default:
 			   break;
 		}
     }

	private void check() 
	{
		String balance=this.m_withdraw_tv_balance.getText().toString().trim();
		String money=this.m_withdraw_et_money.getText().toString().trim();
		Double dBalance;
		Double dMoney;
		if(StringUtils.isEmpty(balance))
		{
			ViewInject.toast("余额非法");
			return;
			
		}
		try
	    {
			dBalance=Double.parseDouble(balance);
	    }
	    catch(Exception e)
	    {
	    	ViewInject.toast("余额不合法");
	    	return;
	    }
		if(dBalance<=0)
		{
			ViewInject.toast("余额为0,不能提现");
	    	return;
		}
		
		
		if(StringUtils.isEmpty(money))
		{
			ViewInject.toast("请先输入提现金额");
			return;
		}
		
	    try
	    {
	    	dMoney=Double.parseDouble(money);
	    }
	    catch(Exception e)
	    {
	    	ViewInject.toast("输入金额不合法");
	    	return;
	    }
	    
	    if(!CommonUtils.isCash(money))
	    {
	    	ViewInject.toast("请输入正确的金额,小数点后最多保留两位！");
	        return;
	    }
	    
	    if(dMoney<100)
		{
			ViewInject.toast("提现金额必须大于等于100元");
	    	return;
		}
		
	    
        if(dMoney>dBalance)
        {
        	ViewInject.toast("提现金额不能大于余额");
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
					Intent intent=new Intent(AppWithdraw.this,AppGestureSetting.class);
					AppWithdraw.this.startActivity(intent);
					
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
	    	 doWithdraw(strCash);
	    	 break;
	    default:
	    	break;
	     }
	}
	
	private void doWithdraw(String strCash)
	{
       if(!StringUtils.isEmpty(strCash))
       {
		  CommonUtils.withdraw(this, AppContext.m_CurrentAccount.getUserpkid(),strCash);
		  this.finish();
       }
	}

}
