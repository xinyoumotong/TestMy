package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.ui.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.TextView;
import hotP2B.WageGainTools.android.adapter.LockPointAdapter;
import hotP2B.WageGainTools.android.ui.widget.GestureLockView;
import hotP2B.WageGainTools.android.ui.widget.GestureLockView.OnGestureFinishListener;
import hotP2B.WageGainTools.android.utils.GestureUtils;

public class AppGestureSetting extends AppTitleBar
{
	

	protected GridView m_lock_gv;
	protected GestureLockView m_lock_gestureLockView;
	protected TextView m_lock_tv_tip;
	protected TextView m_lock_tv_forget;
	
	protected LockPointAdapter adapter;
	protected Animation animation;
	protected boolean bFirst=true;
	
	@Override
	public void setRootView() 
	{
      this.setContentView(R.layout.aty_lock);
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
       this.mTvTitle.setText("设置手势密码");
	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget(); 
        
        m_lock_gv=(GridView)this.findViewById(R.id.lock_gv);
        m_lock_gestureLockView=(GestureLockView)this.findViewById(R.id.lock_gestureLockView);
        m_lock_tv_tip=(TextView)this.findViewById(R.id.lock_tv_tip);
        m_lock_tv_forget=(TextView)this.findViewById(R.id.lock_tv_forget);
        m_lock_tv_forget.setOnClickListener(this);
        
    	adapter=new LockPointAdapter(this);
		this.m_lock_gv.setAdapter(adapter);
		
		this.m_lock_tv_tip.setText("请绘制手势密码");
		this.m_lock_tv_tip.setVisibility(View.VISIBLE);
		this.m_lock_tv_tip.setTextColor(Color.WHITE);
		
		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		m_lock_gestureLockView.setOnGestureFinishListener(new SetGestureListener());
    }
    
    @Override
   	public void onBackClick() 
   	{
   	    super.onBackClick();
   		this.finish();	
   	}
    
	public void onFinish()
	{
	  new Handler().postDelayed(new Runnable()
	  {

		@Override
		public void run() 
		{
			AppGestureSetting.this.setResult(RESULT_OK);
			AppGestureSetting.this.finish();
		}
		
	  }, 200);

	}

	public void onFinish2(final Intent data)
	{
		new Handler().postDelayed(new Runnable()
		{

			@Override
			public void run()
			{
				if (data != null) {
					AppGestureSetting.this.setResult(RESULT_OK,data);
				} else {
					AppGestureSetting.this.setResult(RESULT_OK);
				}

				AppGestureSetting.this.finish();
			}

		}, 200);

	}
	
	private class SetGestureListener implements OnGestureFinishListener
	{
		@Override
		public void OnGestureFinish(boolean success, String key) 
		{
		
			if(success)
			{
				if(bFirst)
				{
					adapter.setData(key);
					m_lock_tv_tip.setText("再次绘制手势密码");
					m_lock_gestureLockView.setKey(key);
					bFirst=false;
					return;
				}
				m_lock_tv_tip.setTextColor(Color.WHITE);
				m_lock_tv_tip.setVisibility(View.VISIBLE);
				m_lock_tv_tip.setText("密码正确");
				GestureUtils.save(AppGestureSetting.this,AppContext.m_CurrentAccount.getUserpkid(),key);
				ViewInject.toast("手势密码设置成功");
				onFinish();
	
			}
			else
			{
				if(key.length()<4)
				{
					m_lock_tv_tip.setText("绘制的点数不能低于4个");
				}
				else
				{
					m_lock_tv_tip.setText("绘制的密码与上次不一致!");
				}
				m_lock_tv_tip.setTextColor(Color.parseColor("#FF2525"));
				m_lock_tv_tip.setVisibility(View.VISIBLE);
				m_lock_tv_tip.startAnimation(animation);
			}
		}
	}//end
	
	
}
