package hotP2B.WageGainTools.android.bean;

public class UpdateResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;
	
	private String App_Id;
	private String App_Name;
	private String Client_Id;
	private String Downloadurl;
	private String Status_Forced_Update;
	private String IntroductInfo;
	
	public String getApp_Id() {
		return App_Id;
	}
	public void setApp_Id(String app_Id) {
		App_Id = app_Id;
	}
	public String getApp_Name() {
		return App_Name;
	}
	public void setApp_Name(String app_Name) {
		App_Name = app_Name;
	}
	public String getClient_Id() {
		return Client_Id;
	}
	public void setClient_Id(String client_Id) {
		Client_Id = client_Id;
	}
	public String getDownloadurl() {
		return Downloadurl;
	}
	public void setDownloadurl(String downloadurl) {
		Downloadurl = downloadurl;
	}
	public String getStatus_Forced_Update() {
		return Status_Forced_Update;
	}
	public void setStatus_Forced_Update(String status_Forced_Update) {
		Status_Forced_Update = status_Forced_Update;
	}
	public String getIntroductInfo() {
		return IntroductInfo;
	}
	public void setIntroductInfo(String introductInfo) {
		IntroductInfo = introductInfo;
	}
	

}
