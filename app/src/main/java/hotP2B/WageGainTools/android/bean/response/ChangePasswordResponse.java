package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class ChangePasswordResponse extends BaseResponse
{
	private static final long serialVersionUID = 1L;
	
	private String userpkid;
	
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}

}
