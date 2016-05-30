package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class RealAuthResponse extends BaseResponse
{

	private static final long serialVersionUID = 1L;
	
	private String userpkid;
	private String result_content;
	
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public String getResult_content() {
		return result_content;
	}
	public void setResult_content(String result_content) {
		this.result_content = result_content;
	}
	
}
