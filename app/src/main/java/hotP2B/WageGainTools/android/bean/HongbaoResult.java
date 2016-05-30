package hotP2B.WageGainTools.android.bean;

import java.util.List;

import hotP2B.WageGainTools.android.bean.HongbaoInfo.HongbaoItem;

public class HongbaoResult extends BaseResponse 
{
	 private static final long serialVersionUID = 1L;
	 
	 private String userpkid;
	 private String sendubrealtionid;
	 private int openresult;
	 private String msg;

	private List<HongbaoItem>reddetails;
	 
	 public List<HongbaoItem> getReddetails() {
		return reddetails;
	}
	public void setReddetails(List<HongbaoItem> reddetails) {
		this.reddetails = reddetails;
	}
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public String getSendubrealtionid() {
		return sendubrealtionid;
	}
	public void setSendubrealtionid(String sendubrealtionid) {
		this.sendubrealtionid = sendubrealtionid;
	}
	public int getOpenresult() {
		return openresult;
	}
	public void setOpenresult(int openresult) {
		this.openresult = openresult;
	}

	 public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
