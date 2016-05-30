package hotP2B.WageGainTools.android.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Salary extends BaseResponse 
{
    private static final long serialVersionUID = 1L;
    
    private List<SalaryItem>data;
 
    public List<SalaryItem> getData() {
		return data;
	}

	public void setData(List<SalaryItem> data) {
		this.data = data;
	}

	public static class SalaryItem implements Serializable
    {
    	private static final long serialVersionUID = 1L;
		private String creditedwages;
    	private String paidwages;
    	private String pluseages;
    
		private String debit;
        private String wagestime;
        
        private List<SalaryDetailItem> details;
        

        public String getCreditedwages() {
			return creditedwages;
		}
		public void setCreditedwages(String creditedwages) {
			this.creditedwages = creditedwages;
		}
		public String getPaidwages() {
			return paidwages;
		}
		public void setPaidwages(String paidwages) {
			this.paidwages = paidwages;
		}
	    public String getPluseages() {
			return pluseages;
		}
		public void setPluseages(String pluseages)
		{
			this.pluseages = pluseages;
		}
		
		public String getDebit() {
			return debit;
		}
		public void setDebit(String debit) {
			this.debit = debit;
		}
		public String getWagestime() {
			return wagestime;
		}
		public void setWagestime(String wagestime) {
			this.wagestime = wagestime;
		}
		public List<SalaryDetailItem> getDetails() {
			return details;
		}
		public void setDetails(List<SalaryDetailItem> details) {
			this.details = details;
		}

    }
    
    public static class SalaryDetailItem implements Serializable
    {
    	private static final long serialVersionUID = 1L;
    	
    	private String Singlename;
		private int singletype;
    	private String singleamount;
    	
    	public String getSinglename() {
			return Singlename;
		}
		public void setSinglename(String singlename) {
			Singlename = singlename;
		}
		
		public int getSingletype() {
			return singletype;
		}
		public void setSingletype(int singletype) {
			this.singletype = singletype;
		}
		public String getSingleamount() {
			return singleamount;
		}
		public void setSingleamount(String singleamount) {
			this.singleamount = singleamount;
		}
    	
    }
    	
}