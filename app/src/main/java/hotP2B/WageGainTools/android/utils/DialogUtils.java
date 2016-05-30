package hotP2B.WageGainTools.android.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.dialog.CustomAlertDialog;


public class DialogUtils 
{
	//显示确定,取消对话框
	public static void showAlertDialog(Context context, String title, String message, OnClickListener confirmListener,OnClickListener cancelListener) 
	{
		Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.confirm, confirmListener);
		if(cancelListener!=null)
		{
		  builder.setNegativeButton(R.string.cancel, cancelListener);
		}
		builder.show();

	}
	public static void showAlertDialog(Context context, int titleId, String message, OnClickListener confirmListener,OnClickListener cancelListener) 
	{
		Builder builder = new Builder(context);
		builder.setTitle(titleId);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.confirm, confirmListener);
		builder.setNegativeButton(R.string.cancel, cancelListener);
		builder.show();
	}
	
	//显示确定,取消对话框,自定义视图
	public static void showAlertDialog(Context context, String title,int layoutId, OnClickListener confirmListener,OnClickListener cancelListener) 
	{
		View view=LayoutInflater.from(context).inflate(layoutId, null);
		showAlertDialog(context,title,view,confirmListener,cancelListener);
	}
	//显示确定,取消对话框,自定义视图
	public static void showAlertDialog(Context context, String title,View view, OnClickListener confirmListener,OnClickListener cancelListener) 
	{
		Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setView(view);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.confirm, confirmListener);
		builder.setNegativeButton(R.string.cancel, cancelListener);
		builder.show();
	}
	
	//显示单选列表对话框
	public static void showAlertDialog(Context context,String title,int itemsId,OnClickListener listener)
	{
		Builder builder=new Builder(context);
		builder.setTitle(title);
		builder.setItems(itemsId,listener);
		builder.create().show();
	}
	
	//显示进度对话框
	public static ProgressDialog create(Context context,String message,boolean bCancel)
	{
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setMessage(message);
		pd.setCancelable(bCancel);
		return pd;   
	}
	
	//显示功能暂未开放对话框
    public static void showNotOpenAlertDialog(Context context)
    {
    	new CustomAlertDialog(context).Builder()
    	.setTitle(R.string.prompt)
        .setMsg(R.string.tip_notopen)
        .show();
    }
}
