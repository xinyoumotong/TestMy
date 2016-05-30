package hotP2B.WageGainTools.android.dialog;

import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;

public class ActionSheetDialog 
{
	 private TextView txt_title;
	 private LinearLayout lLayout_content;
	 private TextView txt_cancel;
	 
	 private Dialog dialog;
	 private Display display;
	 private DisplayMetrics displayMetrics;
	 private Context context;
	 private boolean bShowTitle=false;
	 private List<SheetItem>sheetItemList;
	 
	 public ActionSheetDialog(Context context)
	 {
		this.context=context;
		this.display=((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.displayMetrics=new DisplayMetrics();
		this.display.getMetrics(displayMetrics);
	 }
	 
	 public ActionSheetDialog Builder()
	 {
		 View view=LayoutInflater.from(context).inflate(R.layout.dialog_actionsheet, null);
		 view.setMinimumWidth(this.displayMetrics.widthPixels);
		 
		 txt_title=(TextView)view.findViewById(R.id.txt_title);
		 lLayout_content=(LinearLayout)view.findViewById(R.id.lLayout_content);
		 txt_cancel=(TextView)view.findViewById(R.id.txt_cancel);
		 txt_cancel.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();	
			}
		 });
		 
		 dialog=new Dialog(context,R.style.CustomDialogStyle);
		 dialog.setContentView(view);
		 
		 Window window=dialog.getWindow();
		 window.setGravity(Gravity.BOTTOM);
		 LayoutParams params= window.getAttributes();
		 params.x=0;
		 params.y=0;
	     window.setAttributes(params);
		 
		 return this;
		 
	  }
	  public ActionSheetDialog setTitle(String title) 
	  {
		  this.bShowTitle=true;
		  this.txt_title.setText(title);
		  this.txt_title.setVisibility(View.VISIBLE);
		  
		  return this; 
		  
		  
	  }
	  
	  public ActionSheetDialog setCancelable(boolean flag) 
	  {
		  this.dialog.setCancelable(flag);
		  return this;
	  }
	  
	  public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) 
	  {
		  this.dialog.setCanceledOnTouchOutside(cancel);
		  return this;
	  }
	  
	  
	  public void show()
	  {
		  setSheetItems();
		  this.dialog.show();
	  }
	
	  public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,OnSheetItemClickListener listener) 
	  {
		  if(sheetItemList==null)
		  {
			  sheetItemList=new ArrayList<SheetItem>();
		  }
		  sheetItemList.add(new SheetItem(strItem,color,listener));
		  return this;
	  }
	  private void setSheetItems() 
	  {
		  if (sheetItemList == null || sheetItemList.size() <= 0) 
		  {
			 return;
		  }
		  int size=sheetItemList.size();
		  
		  for (int i = 1; i <= size; i++) 
		  {
			  SheetItem sheetItem=sheetItemList.get(i-1);
			  String strItem = sheetItem.name;
			  SheetItemColor color = sheetItem.color;
		      final OnSheetItemClickListener listener =sheetItem.itemClickListener;
		      TextView textView = new TextView(context);
		      textView.setText(strItem);
		      textView.setTextSize(18);
		      textView.setTextColor(Color.parseColor(color.getName()));
		      textView.setGravity(Gravity.CENTER);
		      float scale = context.getResources().getDisplayMetrics().density;
			  int height = (int) (45 * scale + 0.5f);
			  textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height));
			  textView.setOnClickListener(new View.OnClickListener()
			  {
					@Override
					public void onClick(View v) 
					{
						listener.onClick();
						dialog.dismiss();
					}
			  });
			  
			  if (size == 1) 
			  {
				if (bShowTitle) 
				{
					textView.setBackgroundResource(R.drawable.selector_actionsheet_bottom);
				} 
				else 
				{
					textView.setBackgroundResource(R.drawable.selector_actionsheet_single);
				}
			  } 
			  else 
			  {
				  if (bShowTitle) 
				  {
					if (i >= 1 && i < size) 
					{
						textView.setBackgroundResource(R.drawable.selector_actionsheet_middle);
					} 
					else 
					{
						textView.setBackgroundResource(R.drawable.selector_actionsheet_bottom);
					}
				  } 
				  else 
				  {
					if (i == 1) 
					{
						textView.setBackgroundResource(R.drawable.selector_actionsheet_top);
					} 
					else if (i < size) 
					{
						textView.setBackgroundResource(R.drawable.selector_actionsheet_middle);
					} 
					else 
					{
						textView.setBackgroundResource(R.drawable.selector_actionsheet_bottom);
					}
				  }
			  }
			  
			  lLayout_content.addView(textView);
			  
		   }
	   }


	  public class SheetItem 
	  {
		  String name;
		  SheetItemColor color;
		  OnSheetItemClickListener itemClickListener;
		  
		  public SheetItem(String name, SheetItemColor color,OnSheetItemClickListener itemClickListener) 
		  {
			  this.name=name;
			  this.color=color;
			  this.itemClickListener=itemClickListener;
		  }
	  }
	  
	  public interface OnSheetItemClickListener
	  {
		  void onClick();
	  }
	  public enum SheetItemColor 
	  {
		  Blue("#037BFF"),
		  Red("#FD4A2E");
		  
		  private String name;
		 
		  private SheetItemColor(String name) 
		  {
			this.name = name;
		  }
		  
		  public String getName() 
		  {
			 return name;
		  }
		  public void setName(String name) 
		  {
			 this.name = name;
		   }
	  
	  }
	  
	
}
