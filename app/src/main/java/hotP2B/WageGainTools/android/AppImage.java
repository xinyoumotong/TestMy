package hotP2B.WageGainTools.android;

import java.io.File;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.StringUtils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import hotP2B.WageGainTools.android.utils.UIHelper;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AppImage extends KJActivity {

    public static String URL_KEY = "AppImage_url";

    @BindView(id = R.id.image_progress)
    private ProgressBar m_image_progress;
    @BindView(id = R.id.image_iv_image)
    private PhotoView m_image_iv_image;
    
    @BindView(id=R.id.image_ll_menu)
    private LinearLayout m_image_ll_menu;
    
    @BindView(id = R.id.image_ib_rotate_right,click=true)
    private ImageButton m_image_ib_rotate_right;
    @BindView(id = R.id.image_ib_rotate_left,click=true)
    private ImageButton m_image_ib_rotate_left;

    
    @BindView(id = R.id.image_ib_save,click=true)
    private Button m_image_ib_save;

    private String url;
    private KJBitmap kjb;
    
    private float rotate_right=0.0f;
    private float rotate_left=0.0f;

    @Override
    public void setRootView() 
    {
        setContentView(R.layout.aty_image);
    }

    @Override
    public void initData() 
    {
        super.initData();
        url = getIntent().getStringExtra(URL_KEY);
        kjb = new KJBitmap();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        kjb.display(m_image_iv_image, url, new BitmapCallBack() 
        {
            @Override
            public void onPreLoad() {
                super.onPreLoad();
                m_image_progress.setVisibility(View.VISIBLE);
                m_image_ll_menu.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                m_image_progress.setVisibility(View.GONE);
                m_image_ll_menu.setVisibility(View.VISIBLE);
                
            }
        });
        m_image_iv_image.setOnFinishListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
            	AppImage.this.finish();
            }
        });
        

    }
    
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        switch (v.getId()) 
        {
	        case R.id.image_ib_rotate_right:
	        {
	        	rotate_right=(360-rotate_left+rotate_right+90)%360;
	        	m_image_iv_image.setRotation(rotate_right);
	        	rotate_left=0.0f;
	        }
	        break;
	        case R.id.image_ib_rotate_left:
	        {
	        	rotate_left=(360-rotate_right+rotate_left+90)%360;
	        	m_image_iv_image.setRotation(0-rotate_left);
	        	rotate_right=0.0f;
	        }
	        break;
	        case R.id.image_ib_save:
	        {
	        	onSave();
	        }
	        break;
	        default:
	        break;
        
        }
    }

	private void onSave() 
	{
		String filename=UIHelper.getFileNameFromPath(url);
    	if(!FileUtils.checkSDcard())
    	{
    		ViewInject.toast("SD卡不可用,无法保存图片");
    		return;
    	}
    	if(StringUtils.isEmpty(filename))
    	{
    		ViewInject.toast("图片名称为空,无法保存图片");
    		return;
    	}
    	final String path=FileUtils.getSDCardPath()+File.separator+AppConfig.webImagePath+File.separator+filename;
    	kjb.saveImage(this, url,path,true,new HttpCallBack()
    	{    @Override
            public void onSuccess(byte[] t) 
    	    {
    		   super.onSuccess(t);
    		   ViewInject.longToast("图片保存成功,路径为:"+path);
    	    }
    	
    	    public void onFailure(int errorNo, String strMsg) 
    	    {
    	       super.onFailure(errorNo, strMsg);
    	       ViewInject.longToast("图片保存失败,错误信息为:"+strMsg);
    	    }

    	});
		
	}
}
