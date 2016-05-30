package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class HongbaoInfo extends BaseResponse
{
    private static final long serialVersionUID = 1L;
    
	private double packetsquality;
    private double totalharvest;
    private int totalpackets;
    private int waitpackets;
	private int issetting;
    private String deadline;
    private String setamountrate;

	private List<HongbaoItem>reddetails;
    
    public double getPacketsquality() {
		return packetsquality;
	}

	public void setPacketsquality(double packetsquality) {
		this.packetsquality = packetsquality;
	}

	public double getTotalharvest() {
		return totalharvest;
	}

	public void setTotalharvest(double totalharvest) {
		this.totalharvest = totalharvest;
	}

	public int getTotalpackets() {
		return totalpackets;
	}

	public void setTotalpackets(int totalpackets) {
		this.totalpackets = totalpackets;
	}
	
    public int getWaitpackets() {
		return waitpackets;
	}

	public void setWaitpackets(int waitpackets) {
		this.waitpackets = waitpackets;
	}


	public int getIssetting() {
		return issetting;
	}

	public void setIssetting(int issetting) {
		this.issetting = issetting;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
    public String getSetamountrate() {
		return setamountrate;
	}

	public void setSetamountrate(String setamountrate) {
		this.setamountrate = setamountrate;
	}


	public List<HongbaoItem> getReddetails() {
		return reddetails;
	}

	public void setReddetails(List<HongbaoItem> reddetails) {
		this.reddetails = reddetails;
	}

    
    public static class HongbaoItem extends BaseResponse
	{
		private static final long serialVersionUID = 1L;
		
		private String ubrealtionid;
		private String redtitle;
    	private String redcontents;
    	private double redamount;
    	
    	public String getUbrealtionid() {
			return ubrealtionid;
		}
		public void setUbrealtionid(String ubrealtionid) {
			this.ubrealtionid = ubrealtionid;
		}
		public String getRedtitle() {
			return redtitle;
		}
		public void setRedtitle(String redtitle) {
			this.redtitle = redtitle;
		}
		public String getRedcontents() {
			return redcontents;
		}
		public void setRedcontents(String redcontents) {
			this.redcontents = redcontents;
		}
		public double getRedamount() {
			return redamount;
		}
		public void setRedamount(double redamount) {
			this.redamount = redamount;
		}

	}
		   
    
}
   