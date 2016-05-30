package hotP2B.WageGainTools.android.bean;

public class BankWaterResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private String userpkid;
	private double wages;
	private double transamt;
	private double fee;
	private double arrivalamt;
	

	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public double getWages() {
		return wages;
	}
	public void setWages(double wages) {
		this.wages = wages;
	}
	public double getTransamt() {
		return transamt;
	}
	public void setTransamt(double transamt) {
		this.transamt = transamt;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getArrivalamt() {
		return arrivalamt;
	}
	public void setArrivalamt(double arrivalamt) {
		this.arrivalamt = arrivalamt;
	}

}
