package hotP2B.WageGainTools.android.bean.response;

import java.util.List;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class BalanceResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private List<BalanceItem> data;
	
	public List<BalanceItem> getData() {
		return data;
	}

	public void setData(List<BalanceItem> data) {
		this.data = data;
	}

	public static class BalanceItem
	{
	    public String getUserpkid() {
			return userpkid;
		}
		public void setUserpkid(String userpkid) {
			this.userpkid = userpkid;
		}
		public String getCashDate() {
			return cashDate;
		}
		public void setCashDate(String cashDate) {
			this.cashDate = cashDate;
		}
		public String getDebittype() {
			return debittype;
		}
		public void setDebittype(String debittype) {
			this.debittype = debittype;
		}
		public String getCashAmt() {
			return cashAmt;
		}
		public void setCashAmt(String cashAmt) {
			this.cashAmt = cashAmt;
		}
		public String getUseinfo() {
			return useinfo;
		}
		public void setUseinfo(String useinfo) {
			this.useinfo = useinfo;
		}
		public String getCurrentbalance() {
			return currentbalance;
		}
		public void setCurrentbalance(String currentbalance) {
			this.currentbalance = currentbalance;
		}

		
		private String userpkid;
		private String cashDate;
		private String debittype;
		private String cashAmt;
		private String useinfo;
		private String currentbalance;
	}
  
	
}
