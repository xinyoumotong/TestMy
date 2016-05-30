package hotP2B.WageGainTools.android;

import java.text.DecimalFormat;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.BankWaterResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.VerifyCodeResponse;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppBankWater extends AppTitleBar 
{

	@BindView(id=R.id.bankwater_rg_percent)
	private RadioGroup m_bankwater_rg_percent;
	
	@BindView(id=R.id.bankwater_rb_percent_80)
	private RadioButton m_bankwater_rb_percent_80;
	@BindView(id=R.id.bankwater_rb_percent_90)
	private RadioButton m_bankwater_rb_percent_90;
	@BindView(id=R.id.bankwater_rb_percent_100)
	private RadioButton m_bankwater_rb_percent_100;
	
	@BindView(id=R.id.bankwater_et_cardNumber)
	private EditTextWithDel m_bankwater_et_cardNumber;
	@BindView(id=R.id.bankwater_et_verifyCode)
	private EditTextWithDel m_bankwater_et_verifyCode;
	
	@BindView(id=R.id.bankwater_tv_validation_code,click=true)
	private TextView m_bankwater_tv_validation_code;
	
	@BindView(id = R.id.bankwater_btn_next,click=true)
	private Button m_bankwater_btn_next;
	
   public VerifyCodeResponse verifyCodeResponse=null;
   private TimeCount timeCount=null;
	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_bankwater);
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
	  this.mTvTitle.setText("申请银行流水");

	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
    }
    
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        case R.id.bankwater_tv_validation_code:
        	getVerifyCode();
        	break;
        case R.id.bankwater_btn_next:
        	onNext();
        	break;
        default:
            break;
        }
    }


	//请求发送验证码
    private void getVerifyCode()
    {
    	if(AppContext.m_CurrentAccount==null)return;
    	final String strMobile=AppContext.m_CurrentAccount.getUsername().toString().trim();
    	if(StringUtils.isEmpty(strMobile) || strMobile.length()!=11)return;
    	final String cardNumber=this.m_bankwater_et_cardNumber.getText().toString().trim();
    	if(StringUtils.isEmpty(cardNumber))
    	{
    		ViewInject.toast("请先输入银行卡号");
    		return;
    	}
    	if(m_bankwater_rg_percent.getCheckedRadioButtonId()<0)
    	{
    		ViewInject.toast("请先选择申请比例");
    		return;
    	}
  
    	
      	final CustomProgressDialog dialog=new CustomProgressDialog(this,"正在获取验证码,请稍等...",false);
    	dialog.show();
		HttpUtils.getSMSFromServer(this,strMobile,"2",VerifyCodeResponse.class, new HttpBaseCallBack<VerifyCodeResponse>() 
		{
			@Override
			public void onSuccess(VerifyCodeResponse response) 
			{
				verifyCodeResponse=response;
				if(verifyCodeResponse!=null)
				{
					ViewInject.longToast("验证码已经发送到手机:"+strMobile+",请注意查收");
					timeCount=new TimeCount(60000,1000);
					timeCount.start();
				}
				else
				{
					ViewInject.toast("获取验证码失败,请重新尝试");
				}
			}
			
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取验证码失败,错误代码:" + errorNo + ",错误信息:" + strMsg);   
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}

		});
    }
    
    private void onNext() 
    {
    	if(AppContext.m_CurrentAccount==null)return;
    	final String cardNumber=this.m_bankwater_et_cardNumber.getText().toString().trim();
    	if(StringUtils.isEmpty(cardNumber))
    	{
    		ViewInject.toast("请先输入银行卡号");
    		return;
    	}
    	final int percentId=m_bankwater_rg_percent.getCheckedRadioButtonId();
    	if(percentId<0)
    	{
    		ViewInject.toast("请先选择申请比例");
    		return;
    	}
    	if(this.verifyCodeResponse==null)                                                                                                                            
     	{                                                                                                                                                         
     		ViewInject.toast("请先获取验证码");                                                                                                                    
     		return;                                                                                                                                                 
     	}     
    	final String strVerifyCode=this.m_bankwater_et_verifyCode.getText().toString().trim();        
    	if(StringUtils.isEmpty(strVerifyCode))                                              
     	{                                                                                                                                                         
     		ViewInject.toast("请先输入验证号码");                                                                                                
     		return;                                                                                                                                                 
     	}  
    	String percent="";
    	switch(percentId)
    	{
    	case R.id.bankwater_rb_percent_80:
    		percent="0.8";
    		break;
    	case R.id.bankwater_rb_percent_90:
    		percent="0.9";
    		break;
    	case R.id.bankwater_rb_percent_100:
    		percent="1.0";
    		break;
        default:
        	break;
    		
    	}
    	
    	final String bankid="SPDB";
    	
    	final CustomProgressDialog dialog=new CustomProgressDialog(this,"正在获取工资信息,请稍等...",false);
    	dialog.show();
    	
		HttpUtils.getBankWaterCheck(this,AppContext.m_CurrentAccount.getUserpkid(),bankid,cardNumber,percent,verifyCodeResponse.getAuthorityid(),AppContext.m_CurrentAccount.getUsername(),strVerifyCode,BankWaterResponse.class, new HttpBaseCallBack<BankWaterResponse>() 
		{
			@Override
			public void onSuccess(BankWaterResponse response) 
			{
				if(response.getWages()<=0)
				{
					ViewInject.toast("你本月发放工资金额为0,不能申请银行流水");
					return;
				}
				try
			    {
					Double dBalance=Double.parseDouble(AppContext.m_CurrentAccount.getBalance());
					if(response.getTransamt()>dBalance)
					{
						ViewInject.toast("余额不足,申请失败");
						return;
					}
			    }
			    catch(Exception e)
			    {
			    	ViewInject.toast("余额不合法");
			    	return;
			    }
				ShowBankWater(response,bankid,cardNumber);
			}

			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("获取工资信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);   
			}

			@Override
			public void onFinish() 
			{
				dialog.dismiss();
			}

		});
	}
    
	private void ShowBankWater(final BankWaterResponse response,final String bankid,final String cardNumber) 
	{
		String msg="上月发放工资金额:"+response.getWages()+"\r\n"+
		"申请银行流水金额:"+response.getTransamt()+"\r\n"+
		"手续费金额:"+response.getFee()+"\r\n"+
		"实际转入金额:"+response.getArrivalamt()+"\r\n"+
		"转入银行名称:"+"浦发银行"+"\r\n"+
		"转入银行卡号:"+cardNumber;
		final CustomAlertDialog dialog= new CustomAlertDialog(this).Builder();
		dialog.setTitle(R.string.prompt)
        .setMsg(msg)
        .setPositiveButton("确认", new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				DecimalFormat  df=new DecimalFormat("#.00");   
				String  strCash=df.format(response.getTransamt());
				CommonUtils.applyBankWater(AppBankWater.this,AppContext.m_CurrentAccount.getUserpkid(),strCash,bankid,cardNumber);
				AppBankWater.this.finish();
			}
		})
        .setNegativeButton("取消", null)
        .show();
	}
    
    private class TimeCount extends CountDownTimer 
    {

    	public TimeCount(long millisInFuture, long countDownInterval) 
    	{
			super(millisInFuture, countDownInterval);
		}

		@Override
    	public void onFinish() 
    	{
			m_bankwater_tv_validation_code.setText("重新获取");
			m_bankwater_tv_validation_code.setEnabled(true);
    	}
    	
    	@Override
    	public void onTick(long millisUntilFinished)
    	{
    		m_bankwater_tv_validation_code.setEnabled(false);
    		m_bankwater_tv_validation_code.setText(millisUntilFinished /1000+"秒后重新发送");
    	}
	}

    
    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}
}