package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class HongbaoBalance extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	
	private String totalpacketsquality;
 
	private List<HongbaoBalanceItem> data;

    public String getTotalpacketsquality() {
		return totalpacketsquality;
	}

	public void setTotalpacketsquality(String totalpacketsquality) {
		this.totalpacketsquality = totalpacketsquality;
	}

	public List<HongbaoBalanceItem> getList() 
	{
		return data;
	}
	public void setList(List<HongbaoBalanceItem> list) 
	{
		this.data = list;
	}

	public static class HongbaoBalanceItem
	{
		private String userpkid;
		private String userdebitmoney;
		private int debittype;
		private String createtime;
		private String changedesc;
	
		
		public String getUserpkid() {
			return userpkid;
		}
		public void setUserpkid(String userpkid) {
			this.userpkid = userpkid;
		}
		public String getUserdebitmoney() {
			return userdebitmoney;
		}
		public void setUserdebitmoney(String userdebitmoney) {
			this.userdebitmoney = userdebitmoney;
		}
		public int getDebittype() {
			return debittype;
		}
		public void setDebittype(int debittype) {
			this.debittype = debittype;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getChangedesc() {
			return changedesc;
		}
		public void setChangedesc(String changedesc) {
			this.changedesc = changedesc;
		}

	}

}
