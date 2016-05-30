package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class ErrorResponse extends BaseResponse
{
	public static final long serialVersionUID = 1L;

	private String wgt_err_code="";
	private String errmsg="";
	
	public String getWgt_err_code() {
		return wgt_err_code;
	}
	public void setWgt_err_code(String wgt_err_code) {
		this.wgt_err_code = wgt_err_code;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}


}
