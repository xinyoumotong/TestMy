
package hotP2B.WageGainTools.android.ui.fragment;


import hotP2B.WageGainTools.android.AppMain;
import hotP2B.WageGainTools.android.AppNotice;
import hotP2B.WageGainTools.android.AppSimpleBack;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.SimpleBackPage;
import hotP2B.WageGainTools.android.utils.DialogUtils;

import org.kymjs.kjframe.ui.BindView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



public class Welcome3Fragment extends TitleBarFragment 
{
	private AppMain aty;
	
	//通知中心
	@BindView(id = R.id.find_rl_noticeCenter, click = true)
	private RelativeLayout m_find_rl_noticeCenter;
	//平台资讯
	@BindView(id = R.id.find_rl_news, click = true)
	private RelativeLayout m_find_rl_news;

	//投资排行
    @BindView(id = R.id.find_rl_hot, click = true)
    private RelativeLayout m_find_rl_hot;
    
	//良薪积分
    @BindView(id = R.id.find_rl_score, click = true)
    private RelativeLayout m_find_rl_score;
	
    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,Bundle bundle) 
    {
    	aty = (AppMain) getActivity();
        View view = View.inflate(aty, R.layout.frag_welcome3, null);
        return view;
    }
    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) 
    {
        actionBarRes.titleId=R.string.welcome3;
    }
    @Override
    protected void initData() 
    {
        super.initData();
    }
    @Override
    protected void initWidget(View parentView) 
    {
        super.initWidget(parentView); 
    }
    
    @Override
    protected void widgetClick(View v) 
    {
        super.widgetClick(v);
        switch (v.getId()) 
        {
            //通知中心
	        case R.id.find_rl_noticeCenter:
	        {
	        	this.outsideAty.showActivity(this.outsideAty, AppNotice.class);
	        }
	        break;
	        //平台资讯
	        case R.id.find_rl_news:
	        {
	        	AppSimpleBack.postShowWith(this.outsideAty, SimpleBackPage.FIND_NEWS);
	        }
	        break;
	        case R.id.find_rl_hot:
	        case R.id.find_rl_score:
	        {
	        	DialogUtils.showNotOpenAlertDialog(this.outsideAty);
	        }
	        break;
	        default:
	            break;
        }
    }

     
}