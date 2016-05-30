package hotP2B.WageGainTools.android.bean;

public class UserAccount
{

	private String username;
	private String userpkid;
    private String canautologin;
	private String reallyname;
	private String bizpkid;
	private String bizname; 
	private String reallyidentity;
	private String loginkeyid;
	private String imagename;
	private String authentcustid;//是否开通托管账户
	private int BankCardCount;
	private String balance;
	

	public UserAccount()
	{
		this.BankCardCount=0;
		this.balance="0.00";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	public String getCanautologin() {
		return canautologin;
	}
	public void setCanautologin(String canautologin) {
		this.canautologin = canautologin;
	}
	public String getReallyname() {
		return reallyname;
	}
	public void setReallyname(String reallyname) {
		this.reallyname = reallyname;
	}
	public String getBizpkid() 
	{
		return bizpkid;
	}
	public void setBizpkid(String bizpkid) 
	{
		this.bizpkid = bizpkid;
	}
	public String getBizname() 
	{
		return bizname;
	}
	public void setBizname(String bizname) 
	{
		this.bizname = bizname;
	}
	public String getReallyidentity() {
		return reallyidentity;
	}
	public void setReallyidentity(String reallyidentity) {
		this.reallyidentity = reallyidentity;
	}
	public String getLoginkeyid() {
		return loginkeyid;
	}
	public void setLoginkeyid(String loginkeyid) {
		this.loginkeyid = loginkeyid;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	
	public String getAuthentcustid() {
		return authentcustid;
	}
	public void setAuthentcustid(String authentcustid) {
		this.authentcustid = authentcustid;
	}

	public int getBankCardCount() {
		return BankCardCount;
	}
	public void setBankCardCount(int bankCardCount) {
		BankCardCount = bankCardCount;
	}
	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}


	
}


