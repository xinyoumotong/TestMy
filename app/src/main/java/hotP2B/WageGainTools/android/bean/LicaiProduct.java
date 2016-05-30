package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class LicaiProduct extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	
    private List<LicaiProductItem> data;

    public List<LicaiProductItem> getData() 
    {
	   return data;
    }

    public void setData(List<LicaiProductItem> data) 
    {
	   this.data = data;
    }
	
	public static class LicaiProductItem extends BaseResponse 
	{
		private static final long serialVersionUID = 1L;
		
	    public String getFtitle() {
			return Ftitle;
		}
		public void setFtitle(String ftitle) {
			Ftitle = ftitle;
		}
		public String getFurl() {
			return Furl;
		}
		public void setFurl(String furl) {
			Furl = furl;
		}
		public String getExpected_Annual_Rate() {
			return Expected_Annual_Rate;
		}
		public void setExpected_Annual_Rate(String expected_Annual_Rate) {
			Expected_Annual_Rate = expected_Annual_Rate;
		}
		public String getProject_Duration() {
			return Project_Duration;
		}
		public void setProject_Duration(String project_Duration) {
			Project_Duration = project_Duration;
		}
		public double getProject_Total() {
			return Project_Total;
		}
		public void setProject_Total(double project_Total) {
			Project_Total = project_Total;
		}
		public String getValuedate() {
			return Valuedate;
		}
		public void setValuedate(String valuedate) {
			Valuedate = valuedate;
		}
		public String getExpiredate() {
			return Expiredate;
		}
		public void setExpiredate(String expiredate) {
			Expiredate = expiredate;
		}
		public int getChargingway() {
			return Chargingway;
		}
		public void setChargingway(int chargingway) {
			Chargingway = chargingway;
		}
		public int getStarLevel() {
			return StarLevel;
		}
		public void setStarLevel(int starLevel) {
			StarLevel = starLevel;
		}
		public int getParticipatePeople() {
			return ParticipatePeople;
		}
		public void setParticipatePeople(int participatePeople) {
			ParticipatePeople = participatePeople;
		}
		public double getFinancingRate() {
			return FinancingRate;
		}
		public void setFinancingRate(double financingRate) {
			FinancingRate = financingRate;
		}
		public String getReleaseTime() {
			return ReleaseTime;
		}
		public void setReleaseTime(String releaseTime) {
			ReleaseTime = releaseTime;
		}
		public String getBidProId() {
			return BidProId;
		}
		public void setBidProId(String bidProId) {
			BidProId = bidProId;
		}
		public int getHeartState() {
			return HeartState;
		}
		public void setHeartState(int heartState) {
			HeartState = heartState;
		}
		public int getConscienceState() {
			return ConscienceState;
		}
		public void setConscienceState(int conscienceState) {
			ConscienceState = conscienceState;
		}
		public int getRealState() {
			return RealState;
		}
		public void setRealState(int realState) {
			RealState = realState;
		}
		public int getCreditState() {
			return CreditState;
		}
		public void setCreditState(int creditState) {
			CreditState = creditState;
		}
		public int getSecurityState() {
			return SecurityState;
		}
		public void setSecurityState(int securityState) {
			SecurityState = securityState;
		}
		public int getPaidState() {
			return PaidState;
		}
		public void setPaidState(int paidState) {
			PaidState = paidState;
		}
		public double getPaidMoney() {
			return PaidMoney;
		}
		public void setPaidMoney(double paidMoney) {
			PaidMoney = paidMoney;
		}
		
		public double getRemainMoney() {
			return RemainMoney;
	    }
		public void setRemainMoney(double remainMoney) {
			RemainMoney = remainMoney;
		}

		public int getInvestmentAmount() {
			return InvestmentAmount;
		}
		public void setInvestmentAmount(int investmentAmount) {
			InvestmentAmount = investmentAmount;
		}
		public int getNewBidState() {
			return NewBidState;
		}
		public void setNewBidState(int newBidState) {
			NewBidState = newBidState;
		}
		
		private String Ftitle;//理财产品名称标题
		private String Furl;//需要跳转的url
	    private String Expected_Annual_Rate;//预期年化率  单位百分比
	    private String Project_Duration;//项目期限  单位为日
	    private double Project_Total;//项目总额 
	    private String Valuedate;//起息日
	    private String Expiredate;//到期日
	    private int Chargingway;//还款方式  1  一次性换本付息
	    private int StarLevel;//星级  等级最高为5  最低1
	    private int ParticipatePeople;//参投人数
	    private double FinancingRate;//融资百分率   百分比
	    private String ReleaseTime;
	    private String BidProId;
	    
		private int HeartState;//薪
	    private int ConscienceState;//良
	    private int RealState;//实
	    private int CreditState;//信
	    private int SecurityState;//保
	    private int PaidState;//项目状态, -2回款中 -1流标 0投标中 1已全部回款成功
	    private double PaidMoney;//已投金额
	    private double RemainMoney;
		private int InvestmentAmount;//起投金额
		private int NewBidState;//新手标


		


		
	}
}
