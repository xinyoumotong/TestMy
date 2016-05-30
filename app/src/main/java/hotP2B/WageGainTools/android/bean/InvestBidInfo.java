package hotP2B.WageGainTools.android.bean;

public class InvestBidInfo extends BaseResponse
{
    private static final long serialVersionUID = 1L;
   
    private String UserTransAmt;
    private double BidTransAmt;
    private int InvestmentAmount;
    private int Chargingway;
    
    public double getBidTransAmt() {
	return BidTransAmt;
	}
	public void setBidTransAmt(double bidTransAmt) {
		BidTransAmt = bidTransAmt;
	}
	public String getUserTransAmt() {
		return UserTransAmt;
	}
	public void setUserTransAmt(String userTransAmt) {
		UserTransAmt = userTransAmt;
	}
	public int getInvestmentAmount() {
		return InvestmentAmount;
	}
	public void setInvestmentAmount(int investmentAmount) {
		InvestmentAmount = investmentAmount;
	}
	public int getChargingway() {
		return Chargingway;
	}
	public void setChargingway(int chargingway) {
		Chargingway = chargingway;
	}
  
   
   
}
	   
