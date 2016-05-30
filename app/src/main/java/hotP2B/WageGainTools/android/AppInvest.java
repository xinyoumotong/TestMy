package hotP2B.WageGainTools.android;

import java.text.DecimalFormat;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.LicaiProduct.LicaiProductItem;
import hotP2B.WageGainTools.android.dialog.KeyboardDialog;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils;
import hotP2B.WageGainTools.android.utils.GestureUtils.xx;

public class AppInvest extends AppTitleBar 
{
	@BindView(id = R.id.product_detail_name)
	private TextView m_product_detail_name;
	@BindView(id = R.id.product_detail_invest_minmoney)
	private TextView m_product_detail_invest_minmoney;
	@BindView(id = R.id.product_detail_invest_maxmoney)
	private TextView m_product_detail_invest_maxmoney;
	@BindView(id = R.id.product_detail_remainmoney)
	private TextView m_product_detail_remainmoney;
	@BindView(id = R.id.product_detail_invest_balance)
	private TextView m_product_detail_invest_balance;
	
	@BindView(id = R.id.invest_ll_maxmoney)
	private LinearLayout m_invest_ll_maxmoney;
	
	//投资金额
	@BindView(id = R.id.invest_et_money)
	private EditText m_invest_et_money;
	@BindView(id=R.id.invest_cb_agree)
	private CheckBox m_invest_cb_agree;
	@BindView(id=R.id.invest_tv_agreement)
	private TextView m_invest_tv_agreement;

	@BindView(id=R.id.product_detail_btn_invest,click=true)
	private Button m_product_detail_btn_invest;
	
	private LicaiProductItem productItem=null;
	private KeyboardDialog keyboardDialog=null;
	private String strCash="";
	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_invest);
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
	  this.mTvTitle.setText("投资");

	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
        
        Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null)
        {
           productItem=(LicaiProductItem)bundle.getSerializable("productItem");
         
        }
        if(productItem==null || AppContext.m_CurrentAccount==null)
        {
        	this.finish();
        }
        
        this.m_product_detail_name.setText(productItem.getFtitle());
   	    this.m_product_detail_invest_minmoney.setText(productItem.getInvestmentAmount()+"元");
   	    this.m_product_detail_remainmoney.setText(productItem.getRemainMoney()+"元");
   	    this.m_product_detail_invest_balance.setText(AppContext.m_CurrentAccount.getBalance()+"元");
   	    
   	    if(productItem.getNewBidState()==1)
   	    {
   	    	this.m_product_detail_invest_maxmoney.setText((productItem.getInvestmentAmount()*AppConfig.NEWBID_INVEST_MAX_TIME)+"元");
   	    	this.m_invest_ll_maxmoney.setVisibility(View.VISIBLE);
   	    }
   	    else
   	    {
   	    	this.m_invest_ll_maxmoney.setVisibility(View.GONE);
   	    }
   	    
        this.keyboardDialog=new KeyboardDialog(this);
 	    this.keyboardDialog.fillEdit(this.m_invest_et_money, 1);
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
		  case R.id.product_detail_btn_invest:
		  {
			 onInvest();
		  }
		  break;
		  case R.id.invest_tv_agreement:
			  break;
		 default:
		  break;
		}
    }

	private void onInvest() 
	{
		if(AppContext.m_CurrentAccount==null)return;
		if(this.productItem==null)return;
		
		if(!this.m_invest_cb_agree.isChecked())
		{
			ViewInject.toast("请先阅读并同意<<良薪宝用户理财服务协议>>");
			return;
		}
		if(this.productItem.getRemainMoney()<=0)
		{
			ViewInject.toast("剩余金额为0,不可投标");
			return;
		}
		
		Double dBalance;
		Double dMoney;
		String money=this.m_invest_et_money.getText().toString().trim();
		if(StringUtils.isEmpty(money))
		{
			ViewInject.toast("请先输入投资金额");
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
	    
	    if(dMoney<this.productItem.getInvestmentAmount())
	    {
	    	ViewInject.toast("投资金额必须大于等于起投金额");
	        return;
	    }
	   
	    
	    if(dMoney>this.productItem.getRemainMoney())
	    {
	    	ViewInject.toast("投资金额必须小于等于可投金额");
	        return;
	    }
	    
	    if(dMoney%this.productItem.getInvestmentAmount()!=0)
	    {
	    	ViewInject.toast("投资金额必须为起投金额的整数倍");
	        return;
	    }
	    
	    if(StringUtils.isEmpty(AppContext.m_CurrentAccount.getBalance()))
		{
			ViewInject.toast("余额非法");
			return;
		}
		try
	    {
			dBalance=Double.parseDouble(AppContext.m_CurrentAccount.getBalance());
	    }
	    catch(Exception e)
	    {
	    	ViewInject.toast("余额不合法");
	    	return;
	    }
		if((dBalance<=0) || (dMoney>dBalance))
		{
			ViewInject.toast("余额不足,请到网页端进行充值");
	    	return;
		}
		
		if(this.productItem.getNewBidState()==1 && (dMoney>(AppConfig.NEWBID_INVEST_MAX_TIME*productItem.getInvestmentAmount())))
		{
			ViewInject.toast("新手标限投"+(AppConfig.NEWBID_INVEST_MAX_TIME*productItem.getInvestmentAmount())+"元");
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
					Intent intent=new Intent(AppInvest.this,AppGestureSetting.class);
					startActivity(intent);
					
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
	    	 doInvest(strCash);
	    	 break;
	    default:
	    	break;
	     }
	}
	
	private void doInvest(String strCash)
	{
		if(StringUtils.isEmpty(strCash))return;
		if(AppContext.m_CurrentAccount==null)return;
		if(this.productItem==null)return;
		CommonUtils.invest(this.aty, AppContext.m_CurrentAccount.getUserpkid(),productItem.getBidProId(),strCash);
		this.finish();
	}
}
