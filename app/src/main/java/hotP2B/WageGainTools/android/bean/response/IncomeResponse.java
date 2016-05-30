package hotP2B.WageGainTools.android.bean.response;

import java.util.List;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class IncomeResponse extends BaseResponse 
{
    private static final long serialVersionUID = 1L;
	
    private String TotalEarnings;
   
	private List<IncomeItem> data;

	public String getTotalEarnings() {
		return TotalEarnings;
	}

	public void setTotalEarnings(String totalEarnings) {
		TotalEarnings = totalEarnings;
	}

	public List<IncomeItem> getData() {
		return data;
	 }

	 public void setData(List<IncomeItem> data) {
		this.data = data;
	 }
	
     public static class IncomeItem
     {
    	private String ProfitType;
    	private String ProfitTime;
    	private String ProfitAmount;
    	private String ProfitReMark;
    	
    	public String getProfitType() {
    		return ProfitType;
    	}
    	public void setProfitType(String profitType) {
    		ProfitType = profitType;
    	}
    	public String getProfitTime() {
    		return ProfitTime;
    	}
    	public void setProfitTime(String profitTime) {
    		ProfitTime = profitTime;
    	}
    	public String getProfitAmount() {
    		return ProfitAmount;
    	}
    	public void setProfitAmount(String profitAmount) {
    		ProfitAmount = profitAmount;
    	}
    	public String getProfitReMark() {
    		return ProfitReMark;
    	}
    	public void setProfitReMark(String profitReMark) {
    		ProfitReMark = profitReMark;
    	}
    }
	
	
	
}
