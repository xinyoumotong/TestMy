package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class UnBankCardResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private String userpkid;
	private int cardcount;
	
	
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public int getCardcount() {
		return cardcount;
	}
	public void setCardcount(int cardcount) {
		this.cardcount = cardcount;
	}

}
