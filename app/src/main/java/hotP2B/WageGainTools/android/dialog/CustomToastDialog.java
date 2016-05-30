package hotP2B.WageGainTools.android.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import hotP2B.WageGainTools.android.R;


public class CustomToastDialog 
{

   public static Toast makeText(Context context, int iconId,int textId, int duration) 
   {
       return makeText(context,iconId,context.getResources().getString(textId),duration);
   }
   
   public static Toast makeText(Context context, int iconId,String message, int duration) 
   {
       Toast result = new Toast(context);
       LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View v = inflate.inflate(R.layout.dialog_toast, null);
       ImageView iv=(ImageView)v.findViewById(R.id.toast_image);
       TextView tv = (TextView)v.findViewById(R.id.toast_message);
       iv.setImageResource(iconId);
       tv.setText(message);
       result.setView(v);
       result.setDuration(duration);
       
       return result;
   }
   
   public static Toast makeSuccess(Context context,String message,int duration)
   {
	   return makeText(context,R.mipmap.icon_toast_success,message,duration);
   }
   
   public static Toast makeError(Context context,String message,int duration)
   {
	   return makeText(context,R.mipmap.icon_toast_error,message,duration);
   }
}
