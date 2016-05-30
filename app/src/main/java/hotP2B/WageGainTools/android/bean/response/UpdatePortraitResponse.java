package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class UpdatePortraitResponse extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
    private String userpkid;
    private String imagename;
    
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}

	
}
