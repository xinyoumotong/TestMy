package hotP2B.WageGainTools.android;

import java.util.List;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.bean.HongbaoInfo;
import hotP2B.WageGainTools.android.bean.HongbaoResult;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.HongbaoInfo.HongbaoItem;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.KJAnimations;

public class AppHongbaoShake extends AppTitleBar 
{
    @BindView(id=R.id.hongbao_shake_tv_num)
	private TextView m_hongbao_shake_tv_num;

    @BindView(id=R.id.hongbao_shake_ll_progress)
    private LinearLayout m_hongbao_shake_ll_progress;
    @BindView(id=R.id.hongbao_shake_ll_result)
    private LinearLayout m_hongbao_shake_ll_result;
    @BindView(id=R.id.hongbao_shake_iv_bg)
    private ImageView m_hongbao_shake_iv_bg;
    @BindView(id=R.id.hongbao_shake_iv_drop)
    private ImageView m_hongbao_shake_iv_drop;
    @BindView(id=R.id.hongbao_result_title)
    private TextView m_hongbao_result_title;
    @BindView(id=R.id.hongbao_result_content)
    private TextView m_hongbao_result_content;

    
    private  Vibrator mVibrator;
	private  ShakeListener mShakeListener = null;
	private HongbaoInfo hongbaoInfo=null; 
	private List<HongbaoItem>hongbaoList=null;
	
	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_hongbao_shake);
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
	  this.mTvTitle.setText("摇一摇");

	}
    
    @Override
    public void onPause() 
    {
        super.onPause();
        if(mShakeListener!=null)
        {
        	mShakeListener.stop();
        }
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        if(mShakeListener!=null)
        {
          mShakeListener.start();
        }
    }
    
    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
        
        Intent intent=new Intent(AppConfig.HONGBAO_BROADCAST_ACTION);
		this.sendBroadcast(intent);
    }
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
        
        Intent intent=this.getIntent();
        if(intent!=null)
        {
        	hongbaoInfo=(HongbaoInfo)intent.getSerializableExtra("hongbaoInfo");
        	if(hongbaoInfo!=null)
        	{
        		hongbaoList=hongbaoInfo.getReddetails();
        	}
        }
        
        if(hongbaoList==null || hongbaoList.size()==0)
        {
        	ViewInject.toast("亲,无红包可摇了");
        	this.finish();
        	return;
        }
        this.m_hongbao_shake_tv_num.setText(hongbaoList.size()+"");
        this.mVibrator = ((Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE));
	    this.mShakeListener = new ShakeListener(this);
	    this.mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener()
	    {
			@Override
			public void onShake() 
			{
			     doShake();
			}
		     
	    });
	     
    }
    
    private void doShake()
    {
        mShakeListener.stop();
        this.startVibrator();
        this.m_hongbao_shake_ll_progress.setVisibility(View.VISIBLE);
        this.m_hongbao_shake_ll_result.setVisibility(View.GONE);
        this.m_hongbao_shake_iv_drop.setVisibility(View.GONE);
        
        Animation anim = getAnimation();
        anim.setAnimationListener(new Animation.AnimationListener() 
        {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) 
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) 
            {
               doRequest();
            }

		
        });
        m_hongbao_shake_iv_bg.startAnimation(anim);
    }
    
	private void doRequest() 
	{
      if(hongbaoList==null || hongbaoList.size()==0)
      {
      	 return;
      }
      
	  final HongbaoItem item=hongbaoList.get(0);
      HttpUtils.openHongbao(AppHongbaoShake.this,AppContext.m_CurrentAccount.getUserpkid(),item.getUbrealtionid(),HongbaoResult.class,new HttpBaseCallBack<HongbaoResult>()
      {

			@Override
			public void onSuccess(HongbaoResult response) 
			{
				AppHongbaoShake.this.hongbaoList=response.getReddetails();
				if(response.getOpenresult()==1)
				{
					AppHongbaoShake.this.m_hongbao_shake_ll_result.setVisibility(View.VISIBLE);
					AppHongbaoShake.this.m_hongbao_shake_iv_drop.setVisibility(View.VISIBLE);
					AppHongbaoShake.this.m_hongbao_result_title.setText(item.getRedtitle());
					AppHongbaoShake.this.m_hongbao_result_content.setText(item.getRedcontents()+":"+item.getRedamount()+"元");
				}
				else
				{
					ViewInject.toast("领取红包失败:"+response.getMsg());
				}
				AppHongbaoShake.this.m_hongbao_shake_tv_num.setText(AppHongbaoShake.this.hongbaoList.size()+"");
				if(AppHongbaoShake.this.hongbaoList.size()>0)
				{
					AppHongbaoShake.this.mShakeListener.start();
				}
			}
	
			@Override
			public void onFailure(int errorNo, String strMsg) 
			{
				ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
				
			}
	
			@Override
			public void onFinish() 
			{
				m_hongbao_shake_ll_progress.setVisibility(View.GONE);
			}
      });
	}
    

    
    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}
    
    
    public Animation getAnimation()
	{
        AnimationSet set = new AnimationSet(true);
        Animation anim1 = KJAnimations.getTranslateAnimation(0, -200, 0, 0, 100);
        anim1.setStartOffset(100);
        set.addAnimation(anim1);
        Animation anim2 = KJAnimations.getTranslateAnimation(-200, 400, 0, 0, 200);
        anim2.setStartOffset(300);
        set.addAnimation(anim2);
        Animation anim3 = KJAnimations.getTranslateAnimation(400, -200, 0, 0, 200);
        anim3.setStartOffset(500);
        set.addAnimation(anim3);
        Animation anim4 = KJAnimations.getTranslateAnimation(-200, 0, 0, 0, 100);
        anim4.setStartOffset(600);
        set.addAnimation(anim4);
        set.setFillAfter(true);
        set.setDuration(640);
        
        return set;
	}

	public void startVibrator()
	{
	   this.mVibrator.vibrate(500);
	}
}
