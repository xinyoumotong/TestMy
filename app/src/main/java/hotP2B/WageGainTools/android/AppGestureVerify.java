package hotP2B.WageGainTools.android;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import hotP2B.WageGainTools.android.ui.widget.GestureLockView.OnGestureFinishListener;

public class AppGestureVerify extends AppGestureModify 
{
	 @Override
	 public void initWidget() 
	 {
	    super.initWidget(); 
	    this.mTvTitle.setText("验证手势密码");
	    this.m_lock_tv_tip.setText("请绘制手势密码");
	    this.m_lock_tv_forget.setVisibility(View.GONE);
	    this.m_lock_gestureLockView.setOnGestureFinishListener(new VerifyGestureListener());
	 }
	 
	 private class VerifyGestureListener implements OnGestureFinishListener
	 {

			@Override
			public void OnGestureFinish(boolean success, String key) 
			{
				if(success)
				{
				    if (key.equals(strKey))
				    {
				       m_lock_tv_tip.setTextColor(Color.WHITE);
				       m_lock_tv_tip.setVisibility(View.VISIBLE);
				       m_lock_tv_tip.setText("密码正确");

						Intent intent = AppGestureVerify.this.getIntent();
						if (intent != null) {
							onFinish2(intent);
						} else {
							onFinish();
						}

				    }
				    else
				    {
				    	 m_lock_tv_tip.setTextColor(Color.parseColor("#FF2525"));
					     m_lock_tv_tip.setVisibility(View.VISIBLE);
					     m_lock_tv_tip.setText("密码输入错误");
					     m_lock_tv_tip.startAnimation(animation);
				    }
				}
				else
				{
					if(key.length()<4)
					{
						m_lock_tv_tip.setText("绘制的点数不能低于4个");
						m_lock_tv_tip.setTextColor(Color.parseColor("#FF2525"));
						m_lock_tv_tip.setVisibility(View.VISIBLE);
						m_lock_tv_tip.startAnimation(animation);
					}
				
				}
		     }//end OnGestureFinish
	    	
	   }//end method
}
