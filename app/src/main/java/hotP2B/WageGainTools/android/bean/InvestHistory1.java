package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class InvestHistory1 extends BaseResponse
{
	   private static final long serialVersionUID = 1L;
	   
	   private List<InvestHistory1Item> data;

	   public List<InvestHistory1Item> getData() 
	   {
		   return data;
	   }

	   public void setData(List<InvestHistory1Item> data) 
	   {
		   this.data = data;
	   }
	   
	   public static class InvestHistory1Item
	   {
		   
	       private String BidName;
		   private String CreateTime;
		   private String TransAmt;
		   private String lixi2;
		   
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
			public String getLixi2() {
				return lixi2;
			}
			public void setLixi2(String lixi2) {
				this.lixi2 = lixi2;
			}
	   }
}