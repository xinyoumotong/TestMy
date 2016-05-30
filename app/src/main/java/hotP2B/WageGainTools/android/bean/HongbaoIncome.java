package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class HongbaoIncome extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	
	private String totalprofits;
    private List<HongbaoIncomeItem> data;

	public String getTotalprofits() {
		return totalprofits;
	}
	public void setTotalprofits(String totalprofits) {
		this.totalprofits = totalprofits;
	}
	
	public List<HongbaoIncomeItem> getList() 
	{
		return data;
	}

	public void setList(List<HongbaoIncomeItem> list) 
	{
		this.data = list;
	}

	public static class HongbaoIncomeItem
	{
		private int benefittype;
		private String benefitcontent;
		private String investamount;
		private String investtime;
		private int recordedstate;
	
		public int getBenefittype() {
			return benefittype;
		}
		public void setBenefittype(int benefittype) {
			this.benefittype = benefittype;
		}
		public String getBenefitcontent() {
			return benefitcontent;
		}
		public void setBenefitcontent(String benefitcontent) {
			this.benefitcontent = benefitcontent;
		}
		public String getInvestamount() {
			return investamount;
		}
		public void setInvestamount(String investamount) {
			this.investamount = investamount;
		}
		public String getInvesttime() {
			return investtime;
		}
		public void setInvesttime(String investtime) {
			this.investtime = investtime;
		}
		public int getRecordedstate() {
			return recordedstate;
		}
		public void setRecordedstate(int recordedstate) {
			this.recordedstate = recordedstate;
		}
	
	}

}
