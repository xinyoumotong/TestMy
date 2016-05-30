
package hotP2B.WageGainTools.android.ui.fragment;

import java.io.File;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.utils.SystemTool;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.AppStart;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.UpdatePortraitResponse;
import hotP2B.WageGainTools.android.dialog.ActionSheetDialog;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.dialog.CustomSelectDialog;
import hotP2B.WageGainTools.android.dialog.CustomSelectDialog.IDialogSelectCallBack;
import hotP2B.WageGainTools.android.ui.widget.CircleImageView;
import hotP2B.WageGainTools.android.utils.CommonUtils;
import hotP2B.WageGainTools.android.utils.DialogUtils;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.ImageUtils;
import hotP2B.WageGainTools.android.utils.UIHelper;
import hotP2B.WageGainTools.android.utils.UpdateManager;
import hotP2B.WageGainTools.android.utils.UserUtils;


public class Welcome4Fragment extends TitleBarFragment 
{
	//个人头像
	@BindView(id = R.id.my_iv_avatar,click = true)
	private CircleImageView m_my_iv_avatar;
	
	 //用户名称
	@BindView(id = R.id.my_tv_realname)
	private TextView m_my_tv_realname;
	//公司名称
	@BindView(id = R.id.my_tv_companyName)
	private TextView m_my_tv_companyName;

	//是否实名认证
	@BindView(id = R.id.my_btn_delegateAccount2,click=true)
	private ImageView my_btn_delegateAccount2;

	@BindView(id = R.id.my_tv_delegateAccount2,click=true)
	private TextView my_tv_delegateAccount2;

	@BindView(id = R.id.my_btnrl_delegateAccount2,click=true)
	private RelativeLayout my_btnrl_delegateAccount2;


	//是否已经开通托管账户
	@BindView(id = R.id.my_btn_delegateAccount,click=true)
	private ImageView m_my_btn_delegateAccount;

	@BindView(id = R.id.my_tv_delegateAccount,click=true)
	private TextView my_tv_delegateAccount;

	@BindView(id = R.id.my_btnrl_delegateAccount,click=true)
	private RelativeLayout my_btnrl_delegateAccount;

	@BindView(id = R.id.my_ll_accountSafe, click = true)
	private LinearLayout m_my_ll_accountSafe;
	@BindView(id = R.id.my_ll_help, click = true)
	private LinearLayout m_my_ll_help;
	@BindView(id = R.id.my_ll_about, click = true)
	private LinearLayout m_my_ll_about;

	
	 @BindView(id=R.id.my_btn_logout,click=true)
	 private Button m_my_btn_logout;
	 

    
	 private final static int CROP = 200;
	 private final static String PORTRAIT_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+AppConfig.PACKAGENAME+"/portrait/";
	 private final static String CAMERA_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/"+AppConfig.PACKAGENAME+"/camera/";
		    
	 private Uri origUri;
	 private Uri cropUri;
	 private File protraitFile;
	 private Bitmap protraitBitmap;
	 private String protraitPath;
	 
	 @Override
	 protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) 
	 {

	    View view = View.inflate(outsideAty, R.layout.frag_my, null);
        return view;
     }
     @Override
     protected void setActionBarRes(ActionBarRes actionBarRes) 
     {
        actionBarRes.titleId =R.string.welcome4;
     }
     
     @Override
     protected void initWidget(View parentView) 
     {
         super.initWidget(parentView);

		 m_my_btn_delegateAccount.setClickable(false);
		 my_btn_delegateAccount2.setClickable(false);

		 my_tv_delegateAccount.setClickable(false);
		 my_tv_delegateAccount2.setClickable(false);

		 my_btnrl_delegateAccount.setClickable(true);
		 my_btnrl_delegateAccount2.setClickable(true);

		 my_btnrl_delegateAccount.setOnClickListener(this);
		 my_btnrl_delegateAccount2.setOnClickListener(this);

     }
     

     @Override
     public void onResume() 
	 {
	   super.onResume();
	
	   this.m_my_tv_realname.setText(AppContext.m_CurrentAccount.getReallyname());
	   this.m_my_tv_companyName.setText(AppContext.m_CurrentAccount.getBizname());
	   
	   if(AppContext.m_CurrentAccount.getAuthentcustid().equals("1"))
	   {
		   this.m_my_btn_delegateAccount.setImageResource(R.mipmap.icon_delegate);
		   my_tv_delegateAccount.setText("已托管账户");
		   my_tv_delegateAccount.setTextColor(0xff1be194);
		   my_btnrl_delegateAccount.setBackgroundResource(R.drawable.shape_border_all_blue);
	   }
	   else
	   {
		   this.m_my_btn_delegateAccount.setImageResource(R.drawable.selector_img_nodelegate);
		   my_tv_delegateAccount.setText("未托管账户");
		   my_tv_delegateAccount.setTextColor(0xff999999);
		   my_btnrl_delegateAccount.setBackgroundResource(R.drawable.shape_border_all_99);
	   }

		 if (!AppContext.m_CurrentAccount.getReallyidentity().equals("0"))
		 {
			 this.my_btn_delegateAccount2.setImageResource(R.mipmap.icon_reallyidentity);
			 my_tv_delegateAccount2.setText("已实名认证");
			 my_tv_delegateAccount2.setTextColor(0xff1be194);
			 my_btnrl_delegateAccount2.setBackgroundResource(R.drawable.shape_border_all_blue);
		 }
		 else
		 {
			 this.my_btn_delegateAccount2.setImageResource(R.mipmap.icon_noreallyidentity);
			 my_tv_delegateAccount2.setText("未实名认证");
			 my_tv_delegateAccount2.setTextColor(0xff999999);
			 my_btnrl_delegateAccount.setBackgroundResource(R.drawable.shape_border_all_99);
		 }
	
	   if(!StringUtils.isEmpty(AppContext.m_CurrentAccount.getImagename()))
	   {
		   ImageUtils.displayImage(this.m_my_iv_avatar, AppContext.m_CurrentAccount.getImagename(),R.mipmap.icon_avatar_default);
	   }
	   
 
     }
     
     @Override
     public void onDestroy() 
	 {
	   super.onDestroy();
	  
	 }

     @Override
     protected void widgetClick(View v) 
     {
	      super.widgetClick(v);
	      switch (v.getId()) 
	      {
		      case R.id.my_ll_accountSafe:
		      {
				  if (AppContext.m_CurrentAccount.getReallyidentity().equals("0")) {
					  gotoRealAuth();
					  return;
				  } else {
					  AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.ACCOUNTSAFE);
				  }
		      }
		      break;
		      case R.id.my_ll_help:
		      {
//		    	  UIHelper.toBrowser(this.outsideAty, AppConfig.URL_HELP, "帮助中心");

				  AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.HELP);

		      }
		      break;
		      case R.id.my_ll_about:
		      {
		    	  AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.ABOUT);
		      }
		      break;
		      case R.id.my_btnrl_delegateAccount:
		      {
		    	  if(!AppContext.m_CurrentAccount.getAuthentcustid().equals("1"))
		    	  {
		    		 if(AppContext.m_CurrentAccount.getReallyidentity().equals("0"))
		    		 {
		    			 gotoRealAuth();
		    			 return;
		    		 }
		    	     CommonUtils.openHuifuAccount(this.outsideAty,AppContext.m_CurrentAccount.getUserpkid());
		    	  }
		      }
		      break;

			  case R.id.my_btnrl_delegateAccount2:
			  {
					  if(AppContext.m_CurrentAccount.getReallyidentity().equals("0"))
					  {
						  gotoRealAuth();
						  return;
					  }
			  }
			  break;
		      case R.id.my_btn_logout:
		    	  logoutCurrentUser();
		    	  break;
		    
		      case R.id.my_iv_avatar:
		    	  showClickAvatar();
		      default:
		          break;
         }
     }
     
 	 //更改头像
     private void showClickAvatar() 
     {
    	 new ActionSheetDialog(this.outsideAty).Builder().setCancelable(true).setCanceledOnTouchOutside(true)
         .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() 
         {
	  		  @Override
	  		  public void onClick() 
	  		  {
	  			 startTakePhoto();
	  		  }
  		})
         .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener()
  		{
	  		  @Override
	  		  public void onClick( ) 
	  		  {
	  			 startImagePick();
	  		  }
  		}).show();
    	 
   
     }
     
  
     
     //从相册中选取图片
     private void startImagePick() 
     {
         Intent intent;
         if (Build.VERSION.SDK_INT < 19) 
         {
             intent = new Intent();
             intent.setAction(Intent.ACTION_GET_CONTENT);
             intent.setType("image/*");
             startActivityForResult(Intent.createChooser(intent, "选择图片"),ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
         } 
         else 
         {
             intent = new Intent(Intent.ACTION_PICK,Images.Media.EXTERNAL_CONTENT_URI);
             intent.setType("image/*");
             startActivityForResult(Intent.createChooser(intent, "选择图片"),ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
         }
     }
     
     //拍照
     private void startTakePhoto() 
     {
    	 try
    	 {
	         if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
	         {
	             File savedir = new File(CAMERA_SAVEPATH);
	             if (!savedir.exists()) 
	             {
	                savedir.mkdirs();
	             }
	         }
	         else
	         {
	         	  ViewInject.toast("无法保存照片，请检查SD卡是否挂载");
	              return;
	         }
	         
	         String fileName = "camera"  + ".jpg";// 照片命名
	         File out = new File(CAMERA_SAVEPATH, fileName);
	         Uri uri = Uri.fromFile(out);
	         origUri = uri;
	         
	         MPermissions.requestPermissions(this,AppConfig.REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
    	 }
    	 catch(Exception e)
    	 {
    		 ViewInject.toast(e.toString());
    	 }
     }
     
     
     @Override
     public void onRequestPermissionsResult(int requestCode, String[]permissions, int[]grantResults)
     {
    	 MPermissions.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
     }
     
     @PermissionGrant(AppConfig.REQUEST_PERMISSION_CAMERA)
     public void onRequestCameraGrant()
     {
    	 takePhoto();
     }
     
     @PermissionDenied(AppConfig.REQUEST_PERMISSION_CAMERA)
     public void onRequestCameraDenied()
     {
    	 new CustomAlertDialog(this.outsideAty).Builder()
     	.setTitle("权限申请")
        .setMsg("请在设置-应用-良薪宝-权限管理中打开相机权限,以正常使用拍照功能")
         .setPositiveButton("去设置", new View.OnClickListener() 
         {
 			@Override
 			public void onClick(View v) 
 			{
 				CommonUtils.goSystemSetting(outsideAty);
 			}
 		})
         .setNegativeButton("取消", null)
         .show();
     }
     
 
 	 
     private void takePhoto()
     {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         //启用系统默认拍照程序
 		final Intent intent_camera =this.outsideAty.getPackageManager().getLaunchIntentForPackage("com.android.camera");
 		if (intent_camera != null) 
 		{
 		   intent.setPackage("com.android.camera");
 		}
         intent.putExtra(MediaStore.EXTRA_OUTPUT, origUri);
         
         try
         {
            startActivityForResult(intent,ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
         }
         catch(Exception e)
         {
        	 ViewInject.toast("打开相机失败:"+e.getMessage());
         }
     }
     
     //裁剪头像
     private void startActionCrop(Uri data) 
     {
         Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(data, "image/*");
         intent.putExtra("output", this.getUploadTempFile(data));
         intent.putExtra("crop", "true");
         intent.putExtra("aspectX", 1);// 裁剪框比例
         intent.putExtra("aspectY", 1);
         intent.putExtra("outputX", CROP);// 输出图片大小
         intent.putExtra("outputY", CROP);
         intent.putExtra("scale", true);// 去黑边
         intent.putExtra("scaleUpIfNeeded", true);// 去黑边
         startActivityForResult(intent,ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
       
     }
     
     // 裁剪头像的绝对路径
     private Uri getUploadTempFile(Uri uri) 
     {
         String storageState = Environment.getExternalStorageState();
         if (storageState.equals(Environment.MEDIA_MOUNTED)) {
             File savedir = new File(PORTRAIT_SAVEPATH);
             if (!savedir.exists()) 
             {
                 savedir.mkdirs();
             }
         } 
         else 
         {
         	 ViewInject.toast("无法保存上传的头像，请检查SD卡是否挂载");
              return null;
         }
         String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

         // 如果是标准Uri
         if (StringUtils.isEmpty(thePath)) 
         {
             thePath = ImageUtils.getAbsoluteImagePath(this.outsideAty, uri);
         }
         String ext = getFileFormat(thePath);
         ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
         // 照片命名
         String cropFileName = "portrait_" + SystemTool.getDataTime("yyyyMMddHHmmss") + "." + ext;
         // 裁剪头像的绝对路径
         protraitPath = PORTRAIT_SAVEPATH + cropFileName;
         protraitFile = new File(protraitPath);

         cropUri = Uri.fromFile(protraitFile);
         return this.cropUri;
     }
     
     //获取文件格式
     private String getFileFormat(String fileName) 
     {
 		if (StringUtils.isEmpty(fileName))
 			return "";

 		int point = fileName.lastIndexOf('.');
 		return fileName.substring(point + 1);
 	}
     
     
     /**
      * 上传新照片
      */
     private void uploadNewPhoto() 
     {
         if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) 
         {
         	protraitBitmap =BitmapFactory.decodeFile(protraitPath);
         } 
         else 
         {
         	 ViewInject.toast("图像不存在，上传失败");
         	 return;
         }
     
         String strBmp=ImageUtils.bmpToBase64(protraitBitmap,true);
     
 		final CustomProgressDialog dialog=new CustomProgressDialog(this.outsideAty,"正在上传头像,请稍等...",false);
 		dialog.show();
     	
         HttpUtils.updatePortraitFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(),strBmp,UpdatePortraitResponse.class,new HttpBaseCallBack<UpdatePortraitResponse>()
         {
 			@Override
 			public void onSuccess(UpdatePortraitResponse response) 
 			{
 				if(response!=null)
 				{
 					UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_IMAGENAME, response.getImagename());//修改头像信息
 					ImageUtils.displayImage(m_my_iv_avatar,response.getImagename(),R.mipmap.icon_avatar_default);
 					ViewInject.toast("上传头像成功");
 				}
 			}

 			@Override
 			public void onFailure(int errorNo, String strMsg) 
 			{
 				ViewInject.toast("上传头像失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
 			}

 			@Override
 			public void onFinish() 
 			{
 				dialog.dismiss();
 			}
 			
         	
         });
   		
     }
     
     @Override
     public void onActivityResult(final int requestCode, final int resultCode,final Intent imageReturnIntent) 
     {
         if (resultCode != Activity.RESULT_OK) return;

         switch (requestCode) 
         {
         case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
             startActionCrop(origUri);// 拍照后裁剪
        
             break;
         case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
             startActionCrop(imageReturnIntent.getData());// 选图后裁剪
             break;
         case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
             uploadNewPhoto();
             break;
         default:
         		break;
         }
     }
     //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     
     private void gotoRealAuth()
 	 {
 	   DialogUtils.showAlertDialog(this.outsideAty,R.string.prompt,"你还没有实名认证,去认证？",new DialogInterface.OnClickListener() 
 	   {
 		  @Override
 		  public void onClick(DialogInterface dialog, int which) 
 		  {
 			  dialog.dismiss();
 			  Bundle bundle=new Bundle();
 	          bundle.putString("userpkid",AppContext.m_CurrentAccount.getUserpkid());
 	          AppSimpleBack.postShowWith(outsideAty,SimpleBackPage.VERIFY2,bundle,true);
 		  }
 	    },null);
 	}
     //退出当前账号
     private void logoutCurrentUser() 
     {
     	CustomSelectDialog dialog=new CustomSelectDialog(this.outsideAty,R.string.tip_logout,R.array.logout_option,new IDialogSelectCallBack()
 		{

 			@Override
 			public void onSelect(Dialog dialog, int which) 
 			{
                 dialog.dismiss();
                 switch(which)
                 {
                 case 0:
                 {
                 	AppContext.logout(outsideAty);
                 }
                 break;
                 default:
                 	break;
                 }
 			}
 		});
 		dialog.show();
     	
 	}
     
   

  
}

