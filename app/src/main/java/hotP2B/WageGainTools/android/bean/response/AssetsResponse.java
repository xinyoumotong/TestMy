package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class AssetsResponse extends BaseResponse 
{
    private static final long serialVersionUID = 1L;
	
	private String UserAssets;
	private String EarningsYes;
	private String EarningsMon;
	private int UserNoticeCount;
	private int TradingCount;
	private int BankCardCount;
	private String Balance;
	private int IsNewInvestors;
	

	public String getUserAssets() {
		return UserAssets;
	}
	public void setUserAssets(String userAssets) {
		UserAssets = userAssets;
	}
	public String getEarningsYes() {
		return EarningsYes;
	}
	public void setEarningsYes(String earningsYes) {
		EarningsYes = earningsYes;
	}
	public String getEarningsMon() {
		return EarningsMon;
	}
	public void setEarningsMon(String earningsMon) {
		EarningsMon = earningsMon;
	}
	public int getUserNoticeCount() {
		return UserNoticeCount;
	}
	public void setUserNoticeCount(int userNoticeCount) {
		UserNoticeCount = userNoticeCount;
	}
	public int getTradingCount() {
		return TradingCount;
	}
	public void setTradingCount(int tradingCount) {
		TradingCount = tradingCount;
	}
	public int getBankCardCount() {
		return BankCardCount;
	}
	public void setBankCardCount(int bankCardCount) {
		BankCardCount = bankCardCount;
	}
	
	public String getBalance() {
		return Balance;
	}
	public void setBalance(String balance) {
		Balance = balance;
	}
	
	public int getIsNewInvestors() {
		return IsNewInvestors;
	}
	public void setIsNewInvestors(int isNewInvestors) {
		IsNewInvestors = isNewInvestors;
	}
	

}
