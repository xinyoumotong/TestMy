package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class RegisterResponse extends BaseResponse
{
	
	private static final long serialVersionUID = 1L;
	private String userpkid;
    private int authentication;

    public int getAuthentication() {
		return authentication;
	}
	public void setAuthentication(int authentication) {
		this.authentication = authentication;
	}
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}


}
