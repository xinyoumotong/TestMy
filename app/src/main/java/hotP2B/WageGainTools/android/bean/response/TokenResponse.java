package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class TokenResponse extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	private String access_token;
    private String expires_in;
    private String rsa_client_publickey;
    
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getRsa_client_publickey() {
		return rsa_client_publickey;
	}
	public void setRsa_client_publickey(String rsa_client_publickey) {
		this.rsa_client_publickey = rsa_client_publickey;
	}

}
