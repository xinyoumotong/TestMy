package hotP2B.WageGainTools.android.utils;

import java.io.File;
import java.text.DecimalFormat;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.DownloadTaskQueue;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.SystemTool;

import com.zhy.m.permission.MPermissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.UpdateResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;

@SuppressLint("HandlerLeak")
public class UpdateManager {

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    public static UpdateManager instance=null;
    
    private Object object;
    private Context mContext;
    //通知对话框
    private Dialog noticeDialog;
    //下载对话框
    private Dialog downloadDialog;
 
    
    //显示下载进度
    private TextView mProgressPercent;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    
    private DownloadTaskQueue task = null;


    //apk保存完整路径
    private String apkFilePath = "";
    //临时下载文件路径
    private String tmpFilePath = "";
    //下载文件大小
    private String apkFileSize;
    //已下载文件大小
    private String tmpFileSize;

    private int curVersionCode;
    
    private static UpdateResponse mUpdate=null;
    private CustomProgressDialog checkDialog=null;
    private DecimalFormat df = new DecimalFormat("0.00");
    
    private CheckAppCallBack checkAppCallBack=null;
    
    public CheckAppCallBack getCheckAppCallBack() {
		return checkAppCallBack;
	}
	public void setCheckAppCallBack(CheckAppCallBack checkAppCallBack) {
		this.checkAppCallBack = checkAppCallBack;
	}

	public interface CheckAppCallBack
    {
    	void onFinish();
    }
    public UpdateManager()
    {

    }

    public static UpdateManager getInstance()
    {
    	if(instance==null)
    	{
    		instance=new UpdateManager();
    	}
       return instance;
    }
   
    private Handler mHandler = new Handler() 
    {
    	@Override
        public void handleMessage(Message msg) 
        {
            switch (msg.what) 
            {
                case DOWN_UPDATE:
                	int percent=msg.arg2*100/(int)msg.arg1;
                	apkFileSize = df.format((float)msg.arg1 / 1024 / 1024) + "MB";
                	tmpFileSize = df.format((float)msg.arg2 / 1024 / 1024) + "MB";
                	mProgress.setMax(msg.arg1);
                	mProgress.setProgress(msg.arg2);
                    mProgressPercent.setText(percent+"%");
                    mProgressText.setText(tmpFileSize + "/" + apkFileSize);
                    break;
                case DOWN_OVER:
                    downloadDialog.dismiss();
                    installApk(apkFilePath);
                    break;
                case DOWN_NOSDCARD:
                    downloadDialog.dismiss();
                    Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
                    break;
                 default:
                	 break;
            }
        }

    };



     /**
     * 检查App更新
     * @param isShowMsg 是否显示提示消息
     */
    public void checkAppUpdate(Fragment fragment,final boolean isShowMsg)
    {
    	this.object=fragment;
    	this.mContext=fragment.getActivity();
    	checkAppUpdate(isShowMsg);
    }
    public void checkAppUpdate(Activity activity,final boolean isShowMsg)
    {
    	this.object=activity;
    	this.mContext=activity;
    	checkAppUpdate(isShowMsg);
    }
    
    public void checkAppUpdate(final boolean isShowMsg) 
    {
         this.curVersionCode=SystemTool.getAppVersionCode(this.mContext);
   
         if(isShowMsg)
         {
        	checkDialog=new CustomProgressDialog(this.mContext,"正在检查版本,请稍等...",false);
        	checkDialog.show();
         }

         HttpUtils.getUpdateFromServer(this.mContext,UpdateResponse.class, new HttpBaseCallBack<UpdateResponse>()      
         {                                                                                
                                                                                          
            @Override                                                                           
            public void onSuccess(UpdateResponse response)                                      
            {    
            	
                 mUpdate = response;                                                    
                 if (curVersionCode < Integer.parseInt(mUpdate.getApp_Id()))            
                 {   
                	 AppContext.bHasNewVersion=true;
                     showNoticeDialog();                                               
                 }                                                                      
                 else                                                                   
                 { 
                	 AppContext.bHasNewVersion=false;
                     if (isShowMsg)                                                    
                     {   
                    	 ViewInject.toast("当前已经是最新版本"); 
                     } 
                     if(UpdateManager.this.checkAppCallBack!=null)
                	 {
                		UpdateManager.this.checkAppCallBack.onFinish();
                	 }
                 }                                                                      
            }                                                                                   
                                                                                          
            @Override                                                                           
            public void onFailure(int errorNo, String strMsg)                                   
            {   
            	 AppContext.bHasNewVersion=false;
            	 if(isShowMsg)
            	 {
            	    ViewInject.toast("检查版本信息失败,错误代码:" + errorNo + "错误信息:" + strMsg);  
            	 }
        		 if(UpdateManager.this.checkAppCallBack!=null)
              	 {
              		UpdateManager.this.checkAppCallBack.onFinish();
              	 }
            	                                                                            
     	    }                                                                                   
                                                                                          
		     @Override                                                                           
		     public void onFinish()                                                              
		     {                                                                                   
		     	if(isShowMsg && checkDialog!=null)                                                                     
		     	{                                                                                 
		     	  checkDialog.dismiss();                                                          
		     	}                                                                                 
		     }                                                                                   
		        	                                                                                
        });                                                                               
      }

    
    /**
     * 显示版本更新通知对话框
     */
    private void showNoticeDialog() 
    {
    	if(mUpdate==null)return;
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(mUpdate.getIntroductInfo());
        builder.setPositiveButton("立即更新", new OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int which) 
            {
                dialog.dismiss();
                checkPermission();
            }
        });
        if(!mUpdate.getStatus_Forced_Update().equals("1"))//不需要强制更新
        {
        	AppContext.bForceUpdate=false;
	        builder.setNegativeButton("以后再说", new OnClickListener() 
	        {
	            @Override
	            public void onClick(DialogInterface dialog, int which) 
	            {
	                dialog.dismiss();
	                if(checkAppCallBack!=null)
	                {
	                	checkAppCallBack.onFinish();
	                }
	            }
	        });
	        
        }
        else
        {
        	AppContext.bForceUpdate=true;
        	builder.setCancelable(false);
        }
        noticeDialog = builder.create();
        noticeDialog.show();
    }


    private void checkPermission() 
    {
       if(this.object instanceof  Activity)
       {
	      MPermissions.requestPermissions((Activity)this.object, AppConfig.REQUEST_PERMISSION_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
       }
       else if(this.object instanceof  Fragment)
       {
    	  MPermissions.requestPermissions((Fragment)this.object, AppConfig.REQUEST_PERMISSION_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
       }
	}

    public void downloadApk()
    {
    	if(mUpdate==null)return;
    	String url=mUpdate.getDownloadurl();
    	if(url==null || url.length()==0)return;
    	
        Builder builder = new Builder(mContext);
        builder.setTitle("正在下载新版本");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_update, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
        mProgressPercent=(TextView)v.findViewById(R.id.update_progress_percent);

        builder.setView(v);
        if(!mUpdate.getStatus_Forced_Update().equals("1"))//不需要强制更新
        {
	        builder.setNegativeButton("取消", new OnClickListener() 
	        {
	            @Override
	            public void onClick(DialogInterface dialog, int which) 
	            {
	                dialog.dismiss();
	                cancelDownload();
	                if(checkAppCallBack!=null)
	                {
	                	checkAppCallBack.onFinish();
	                }
	            }
	        });
	        builder.setOnCancelListener(new OnCancelListener()
	        {
	            @Override
	            public void onCancel(DialogInterface dialog) {
	                dialog.dismiss();
	                cancelDownload();
	            }			
	        });
        }
        
        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.setCancelable(false);
        downloadDialog.show();
      
    	final File folder = FileUtils.getSaveFolder(AppConfig.saveFolder);
    	
    	this.tmpFilePath=folder+"/hotP2B_"+mUpdate.getApp_Id()+".apk.tmp";
    	this.apkFilePath=folder+"/hotP2B_"+mUpdate.getApp_Id()+".apk";
    	
        
        HttpConfig config = new HttpConfig();
        config.cacheTime = 0;
        KJHttp kjh = new KJHttp(config);
    			
        File tempFile = new File(tmpFilePath);
        if (tempFile.exists()) 
        {
           tempFile.delete();
        }
        File apkFile=new File(apkFilePath);
        if(apkFile.exists())
        {
        	apkFile.delete();
        }

        task= kjh.download(apkFilePath, url, new HttpCallBack() 
        {

            @Override
            public void onLoading(long count, long current) 
            {
            	
            	Message msg=mHandler.obtainMessage(DOWN_UPDATE,(int)count, (int)current);
            	mHandler.sendMessage(msg);
            	
                super.onLoading(count, current);

            }
            @Override
            public void onSuccess(byte[] t) 
            {
                super.onSuccess(t);
                mHandler.sendEmptyMessage(DOWN_OVER);
            }
        });

      }
    
      /**
       * 取消下载
       */
      private void cancelDownload() 
      {
		 if(task!=null)
         {
           task.clearAll();
         }
	  }
    
	 /**
	 * 安装apk
	 */
	 private void installApk(String path) 
	 {
	    File apkFile = new File(path);
	    if (!apkFile.exists()) 
	    {
	        return;
	    }
	    SystemTool.installApk(this.mContext,apkFile);
	    AppContext.exitUI();
	 }
}


