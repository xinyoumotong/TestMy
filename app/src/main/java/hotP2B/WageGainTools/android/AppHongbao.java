package hotP2B.WageGainTools.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.HongbaoInfo;
import hotP2B.WageGainTools.android.bean.HongbaoInfo.HongbaoItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.dialog.CustomHongbaoDialog;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.utils.HttpUtils;

public class AppHongbao extends AppTitleBar 
{
	@BindView(id=R.id.hongbao_swipeRefreshLayout)
	private SwipeRefreshLayout m_hongbao_swipeRefreshLayout;
	@BindView(id=R.id.hongbao_empty_layout)
	private EmptyLayout m_hongbao_empty_layout;
	
	@BindView(id=R.id.hongbao_tv_balance)
	private TextView m_hongbao_tv_balance;
	@BindView(id=R.id.hongbao_tv_income)
	private TextView m_hongbao_tv_income;
	@BindView(id=R.id.hongbao_tv_remaintime)
	private TextView m_hongbao_tv_remaintime;
	
	@BindView(id=R.id.hongbao_tv_waitpackets)
	private TextView m_hongbao_tv_waitpackets;
	@BindView(id=R.id.hongbao_tv_totalpackets)
	private TextView m_hongbao_tv_totalpackets;
	@BindView(id=R.id.hongbao_iv_week)
	private ImageView m_hongbao_iv_week;
	
	@BindView(id=R.id.hongbao_btn_shake,click = true)
	private Button m_hongbao_btn_shake;
	@BindView(id=R.id.hongbao_btn_setting,click = true)
	private Button m_hongbao_btn_setting;
	@BindView(id=R.id.hongbao_btn_rollout,click = true)
	private Button m_hongbao_btn_rollout;
	
	private HongbaoReceiver hongbaoReceiver=null;
	private HongbaoInfo hongbaoInfo=null;
	private Timer timer=null;
	
	private final int UPDATETIME=0;
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
	    {
			switch(msg.what)
			{
			case UPDATETIME:
			{
				String str=(String)msg.obj;	
				m_hongbao_tv_remaintime.setText(Html.fromHtml(str));
			}
			break;
			default:
				break;
			}
		}
	};

	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_hongbao);
	}

	@Override
    public void initData() 
    {
        super.initData();
    }

    @Override
	public void initTitleBar()
	{
      this.mImgMenu.setImageResource(R.mipmap.icon_hongbao_record);
      this.mImgMenu.setVisibility(View.VISIBLE);
	  this.mImgBack.setVisibility(View.VISIBLE);
	  this.mTvTitle.setText("礼拜红包");
	  
	  //注册广播
	  hongbaoReceiver=new HongbaoReceiver();
	  IntentFilter filterHongbao=new IntentFilter();
	  filterHongbao.addAction(AppConfig.HONGBAO_BROADCAST_ACTION);
      this.registerReceiver(hongbaoReceiver, filterHongbao);

	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
        m_hongbao_empty_layout.setOnLayoutClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				refresh();
			}
		});
        m_hongbao_swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        m_hongbao_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() 
		{
	        @Override
	        public void onRefresh() 
	        {
	            refresh2();
	        }
	    });
        
        refresh();
    }
    
    private void startTimer()
    {
    	timer=new Timer();
        timer.schedule(new TimerTask()
		{
			@Override
			public void run() 
			{
			  updateTime();
			}
	
		}, 1000,1000);
    }
    
    @SuppressLint("SimpleDateFormat")
	private void updateTime()
    {
    	 Date now=new Date();
    	 Calendar cal = Calendar.getInstance();
         cal.setTime(now);    
         cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         cal.add(Calendar.DATE, 7);
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
         String strDateTime = formatter.format(cal.getTime());
         try 
         {
             Date d= formatter.parse(strDateTime);
             long remain=d.getTime()-now.getTime();
             long day=remain/86400000;
             long hour=(remain%86400000)/3600000;
             long minute=(remain%86400000)%3600000/60000;
             long second=(remain%86400000)%3600000%60000/1000;
             
             String str=String.format("<font color='#FFDD99'>%d</font>天<font color='#FFDD99'>%d</font>时<font color='#FFDD99'>%d</font>分<font color='#FFDD99'>%d</font>秒",day,hour,minute,second);
             this.handler.sendMessage(this.handler.obtainMessage(UPDATETIME, str));
         
         } 
         catch (Exception e) 
         {
        	 e.printStackTrace();
         }
         
    }
  

    private void fillWeek()
    {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int id=0;
        
        switch(day)
        {
            case Calendar.MONDAY:
          	 id=R.mipmap.week_01;
          	 break;
            case Calendar.TUESDAY:
           	 id=R.mipmap.week_02;
           	 break;
           case Calendar.WEDNESDAY:
          	 id=R.mipmap.week_03;
          	 break;
           case Calendar.THURSDAY:
           	 id=R.mipmap.week_04;
           	 break;
           case Calendar.FRIDAY:
       	     id=R.mipmap.week_05;
       	     break;
           case Calendar.SATURDAY:
        	 id=R.mipmap.week_06;
         	  break;
         case Calendar.SUNDAY:
        	 id=R.mipmap.week_07;
        	 break;
          default:
        	  break;
        }
        
        if(id>0)
        {
        	this.m_hongbao_iv_week.setImageResource(id);
        }
        
    }
    
    private void refresh() 
    {
    	m_hongbao_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
    	
    	HttpUtils.getHongbaoInfo(this,AppContext.m_CurrentAccount.getUserpkid(),HongbaoInfo.class,new HttpBaseCallBack<HongbaoInfo>() 
    	{
			@Override
			public void onSuccess(HongbaoInfo response) 
			{
				hongbaoInfo=response;
				fillUI(response);
				m_hongbao_empty_layout.dismiss();
				if((hongbaoInfo.getIssetting()==0) ||((hongbaoInfo.getIssetting()==1) && hongbaoInfo.getSetamountrate().equals("0")))
				{
					showSettingDialog();
				}
				if(timer==null)
				{
				    startTimer();
				}
			}


			@Override
			public void onFailure(int errorNo, String strMsg)
			{
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				m_hongbao_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
			}

			@Override
			public void onFinish() {

			}
    		
    	});
	}
    
    private void refresh2() 
    {
    	m_hongbao_swipeRefreshLayout.setRefreshing(true);
    	
    	HttpUtils.getHongbaoInfo(this,AppContext.m_CurrentAccount.getUserpkid(),HongbaoInfo.class,new HttpBaseCallBack<HongbaoInfo>() 
    	{

			@Override
			public void onSuccess(HongbaoInfo response) 
			{
				hongbaoInfo=response;
				fillUI(response);
				ViewInject.toast("刷新成功");
			}

			@Override
			public void onFailure(int errorNo, String strMsg)
			{
				ViewInject.toast("刷新失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
			}

			@Override
			public void onFinish() 
			{
				m_hongbao_swipeRefreshLayout.setRefreshing(false);
			}
    		
    	});
	}
    

	private void showSettingDialog() 
	{
		new Handler().postDelayed(new Runnable()
        {
        	@Override
			public void run() 
        	{
        		CustomHongbaoDialog dialog=new CustomHongbaoDialog(AppHongbao.this);
        		dialog.setCanceledOnTouchOutside(false);
        		dialog.show();
        	}

        },1000);
	}
    
	private void fillUI(HongbaoInfo hongbaoInfo) 
	{
		if(hongbaoInfo!=null)
		{
			this.m_hongbao_tv_balance.setText(hongbaoInfo.getPacketsquality()+"");
			this.m_hongbao_tv_income.setText(hongbaoInfo.getTotalharvest()+"");
			this.m_hongbao_tv_waitpackets.setText(hongbaoInfo.getWaitpackets()+"");
			this.m_hongbao_tv_totalpackets.setText(hongbaoInfo.getTotalpackets()+"");
		}
		fillWeek();
	}

	@Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        case R.id.hongbao_btn_shake:
        {
        	onShake();
        }
        break;
        case R.id.hongbao_btn_setting:
        {
        	onSetting();
        }
       break;
       case R.id.hongbao_btn_rollout:
       {
    	   onRollout();
       }
        break;
        default:
            break;
        }
    }


	@Override
	public void onMenuClick()
	{
		this.showActivity(this, AppHongbaoRecord.class);
	}
    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}
    
	private void onShake() 
	{
		if(hongbaoInfo==null)return;
		List<HongbaoItem> hongbaoList=hongbaoInfo.getReddetails();
		if(hongbaoList==null || hongbaoList.size()==0)
		{
			ViewInject.toast("亲,无红包可摇了");
			return;
		}
		Bundle bundle=new Bundle();
		bundle.putSerializable("hongbaoInfo", hongbaoInfo);
		try
		{
		  this.showActivity(AppHongbao.this, AppHongbaoShake.class,bundle);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
    private void onSetting()
    {   
    	if(hongbaoInfo!=null)
        {
    		Bundle bundle=new Bundle();
    		bundle.putInt("issetting", hongbaoInfo.getIssetting());
    		bundle.putString("setamountrate", hongbaoInfo.getSetamountrate());
    		this.showActivity(AppHongbao.this, AppHongbaoSetting.class,bundle);
        }
    	
    }
	private void onRollout() 
	{
		 String strBalance=this.m_hongbao_tv_balance.getText().toString().trim();
	     Double balance=StringUtils.toDouble(strBalance);
         if(balance<=0)
         {
   	        ViewInject.toast("红包储备为0,不能转出");
   	        return;
         }   
	     Bundle bundle=new Bundle();
	     bundle.putString("balance",strBalance);
		 this.showActivity(AppHongbao.this, AppHongbaoRollout.class,bundle);
	}
    
	private class HongbaoReceiver extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			if(intent.getAction().equals(AppConfig.HONGBAO_BROADCAST_ACTION))
			{
				refresh2();
			}
		}
	}
}