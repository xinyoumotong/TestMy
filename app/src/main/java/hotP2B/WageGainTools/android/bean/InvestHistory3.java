package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class InvestHistory3 extends BaseResponse
{
	   private static final long serialVersionUID = 1L;
	   
	   private List<InvestHistory3Item> data;

	   public List<InvestHistory3Item> getData() 
	   {
		   return data;
	   }

	   public void setData(List<InvestHistory3Item> data) 
	   {
		   this.data = data;
	   }
	   
	   public static class InvestHistory3Item
	   {
		   
	       public String getBidName() {
			return BidName;
		}
		public void setBidName(String bidName) {
			BidName = bidName;
		}
		public String getCreateTime() {
			return CreateTime;
		}
		public void setCreateTime(String createTime) {
			CreateTime = createTime;
		}
		public String getTransAmt() {
			return TransAmt;
		}
		public void setTransAmt(String transAmt) {
			TransAmt = transAmt;
		}
		public String getInvestmentMoney() {
			return InvestmentMoney;
		}
		public void setInvestmentMoney(String investmentMoney) {
			InvestmentMoney = investmentMoney;
		}
		public String getOrdId() {
			return OrdId;
		}
		public void setOrdId(String ordId) {
			OrdId = ordId;
		}
		public String getFreezeId() {
			return FreezeId;
		}
		public void setFreezeId(String freezeId) {
			FreezeId = freezeId;
		}
		public int getState() {
			return State;
		}
		public void setState(int state) {
			State = state;
		}
		public String getBidproid() {
			return bidproid;
		}
		public void setBidproid(String bidproid) {
			this.bidproid = bidproid;
		}
		private String BidName;
		private String CreateTime;
		private String TransAmt;
		private String InvestmentMoney;
		private String OrdId;
		private String FreezeId;
		private int State;
		private String bidproid;
	   }
}
