package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class VerifyCodeResponse extends BaseResponse
{
	
	private static final long serialVersionUID = 1L;
	
	private String userpkid;
    private String authorityid;

	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public String getAuthorityid() {
		return authorityid;
	}
	public void setAuthorityid(String authorityid) {
		this.authorityid = authorityid;
	}

	
}
