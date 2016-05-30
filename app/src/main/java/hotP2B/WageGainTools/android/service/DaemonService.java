package hotP2B.WageGainTools.android.service;

import hotP2B.WageGainTools.android.AppBrowser;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.BroadcastNotice;
import hotP2B.WageGainTools.android.bean.BroadcastNotice.BroadcastNoticeItem;
import hotP2B.WageGainTools.android.bean.Notice;
import hotP2B.WageGainTools.android.bean.Notice.NoticeItem;
import hotP2B.WageGainTools.android.bean.UserNotice;
import hotP2B.WageGainTools.android.bean.UserNotice.UserNoticeItem;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.LoginResponse;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;
import hotP2B.WageGainTools.android.utils.UserUtils.User;


import org.kymjs.kjframe.ui.ViewInject;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class DaemonService extends Service 
{

	private  int m_NotificationID = 1000;
	private  int m_Step=0;
	private NotificationCompat.Builder mBuilder;
	private NotificationCompat.Builder mBuilder2;
	private NotificationManager mNotificationManager;
	private static boolean bRunning=false;


	public DaemonService() 
	{

	}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		stopForeground(true);
		
		// 退出时发送一次定时器广播，用于重启服务
		Intent intent = new Intent(AppConfig.ALARM_BROADCAST_ACTION);
		sendBroadcast(intent);

	}

	@Override
	public void onCreate() 
	{
		super.onCreate();
		startForeground(0, new Notification());
		initNotification();

	}
	
	private void initNotification() 
	{
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setDefaults(Notification.DEFAULT_ALL);
		mBuilder.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL));
		mBuilder.setSmallIcon(R.mipmap.ic_launcher);
		
		mBuilder2 = new NotificationCompat.Builder(this);
		mBuilder2.setDefaults(Notification.DEFAULT_ALL);
		mBuilder2.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL));
		mBuilder2.setSmallIcon(R.mipmap.ic_launcher);
	}
	
	/**
	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT 点击去除：
	 * Notification.FLAG_AUTO_CANCEL
	 */
	public PendingIntent getDefalutIntent(int flags) 
	{
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		onStart(intent, startId);
		return START_NOT_STICKY;
	}
	
	@Override
	public void onStart(Intent intent, int startId) 
	{
		//ViewInject.toast("DaemonService.onStart()" );
		doRequestNotice();
	}

	private void doRequestNotice() 
	{
		if(bRunning)return;
		int i=this.m_Step%4;
		try
		{
		   switch(i)
		   {
			case 0:
			case 2:
				//ViewInject.toast("正在获取用户通知,时间:" + SystemTool.getDataTime());
				doGetUserNotice();
				break;
			case 1:
				//ViewInject.toast("正在获取全局广播通知,时间:" + SystemTool.getDataTime());
				doGetGlobalNotice();
				break;
			case 3:
				//ViewInject.toast("正在获取广播通知,时间:" + SystemTool.getDataTime());
				doGetBroadcastNotice();
				break;
			default:
				break;
				
			}

		}
		catch(Exception e)
		{
			ViewInject.toast("获取推送信息失败,错误信息:" + e.getMessage());
		}
		finally
		{
			this.m_Step++;
			if(m_Step>=10000)
			{
				m_Step=0;
			}
			bRunning=false;
		}
			
	}

	//获取全局通知
	private void doGetGlobalNotice()
	{
		 HttpUtils.getGlobalNoticeFromServer(this.getApplicationContext(),Notice.class, new HttpBaseCallBack<Notice>()
		 {
		    @Override
		    public void onSuccess(Notice notice)
			{
			   if(notice!=null)
			   {
				   for(int i=0;i<notice.getList().size();i++)
				   {				   
					   showNotice(notice.getList().get(i));
				   }
				
			   }
			}
		    @Override
			public void onFailure(int errorNo, String strMsg) 
			{
				//ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
			}
		    @Override
		    public void onFinish()
		    {
		
		    }
		    
		});
	}
	
	//获取广播通知
	private void doGetBroadcastNotice() 
	{
		HttpUtils.getBroadcastNoticeFromServer(this.getApplicationContext(), BroadcastNotice.class, new HttpBaseCallBack<BroadcastNotice>() 
		{

			@Override
			public void onSuccess(BroadcastNotice broadcastNotice) 
			{
				 if(broadcastNotice!=null)
				 {
					 for(int i=0;i<broadcastNotice.getList().size();i++)
					 {				   
						 showBroadcastNotice(broadcastNotice.getList().get(i));
					 }
					
				 }
			}
			@Override
			public void onFailure(int errorNo, String strMsg) {

				//ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
			}

			@Override
			public void onFinish() {

				
			}
			
		});
		
	}

	
	//获取用户通知
	private void doGetUserNotice()
	{
		
		User user=UserUtils.getLocalUser(this.getApplicationContext());
		if(user!=null && user.getCanautologin().equals("1"))
		{
			//先自动登录，成功后获取用户通知
			HttpUtils.loginFromServer(this.getApplicationContext(),user.getUsername(),"","2",user.getLoginkeyid(),LoginResponse.class,new HttpBaseCallBack<LoginResponse>()
			{
				@Override
				public void onSuccess(LoginResponse loginResponse) 
				{
					if(loginResponse!=null && !loginResponse.getUserpkid().equals(""))
					{
						requestUserNotice(loginResponse.getUserpkid());
					}
				}
				@Override
				public void onFailure(int errorNo, String strMsg) 
				{
					//ViewInject.toast("自动登录失败,错误代码:" + errorNo + "错误信息:" + strMsg);
				
				}
				@Override
				public void onFinish() {
	
					
				}
			});
		}
	}
	
	private void requestUserNotice(String userpkid) 
	{
		HttpUtils.getUserNoticeFromServer(this.getApplicationContext(),userpkid,UserNotice.class, new HttpBaseCallBack<UserNotice>()
		{
		   @Override
		   public void onSuccess(UserNotice userNotice)
		   {
			   if(userNotice!=null)
			   {
				   for(int i=0;i<userNotice.getList().size();i++)
				   {				   
					   showUserNotice(userNotice.getList().get(i));
				   }
				
			   }
			
			}
			
		    @Override
			public void onFailure(int errorNo, String strMsg) 
			{
				//ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
			}

			@Override
			public void onFinish() {
				
			}
		});
	}
	
	private void showNotice(NoticeItem noticeItem)
	{
	   switch(noticeItem.getClientaction())
	   {
		case AppConfig.ACTION_NOTICE_URL:
			gotoURL(noticeItem.getNoticetitle(),noticeItem.getNoticecontent(),noticeItem.getNoticeurl());
			break;
		default:
			break;
		}

	}
	
	private void showBroadcastNotice(BroadcastNoticeItem broadcastNoticeItem) 
	{
	  if(broadcastNoticeItem.getType().equals("1"))
	  {
	    gotoURL(broadcastNoticeItem.getTitle(),broadcastNoticeItem.getContent(),broadcastNoticeItem.getUrl());
	  }	
	}
	
	private void showUserNotice(UserNoticeItem noticeItem)
	{

		mBuilder2.setAutoCancel(true);
		mBuilder2.setContentTitle(noticeItem.getUsernoticetitle());
		mBuilder2.setContentText(noticeItem.getUsernoticecontent());
		mBuilder2.setWhen(System.currentTimeMillis());
		m_NotificationID++;
		mNotificationManager.notify(m_NotificationID, mBuilder2.build());

	}

	
	private void gotoURL(String title,String content,String url)
	{
		mBuilder.setAutoCancel(true);
		mBuilder.setContentTitle(title);
		mBuilder.setContentText(content);
		mBuilder.setWhen(System.currentTimeMillis());
		Intent intent=new Intent(this,AppBrowser.class);
		intent.putExtra(AppBrowser.BROWSER_KEY,url);
		intent.putExtra(AppBrowser.BROWSER_TITLE_KEY,title);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this,m_NotificationID, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		m_NotificationID++;
		mNotificationManager.notify(m_NotificationID, mBuilder.build());
	}
	
	
}