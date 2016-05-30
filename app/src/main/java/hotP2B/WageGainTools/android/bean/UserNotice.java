package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class UserNotice extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	
    private List<UserNoticeItem> data;

	public List<UserNoticeItem> getList() 
	{
		return data;
	}

	public void setList(List<UserNoticeItem> list) 
	{
		this.data = list;
	}

	
	public static class UserNoticeItem
	{
		private String usernoticeid;
		private String userpkid;
		private String usernoticetitle;
		private String usernoticecontent;
		private String clientaction;
		private String createtime;
		
	
		public String getUsernoticeid() {
			return usernoticeid;
		}
		public void setUsernoticeid(String usernoticeid) {
			this.usernoticeid = usernoticeid;
		}
		public String getUserpkid() {
			return userpkid;
		}
		public void setUserpkid(String userpkid) {
			this.userpkid = userpkid;
		}
		public String getUsernoticetitle() {
			return usernoticetitle;
		}
		public void setUsernoticetitle(String usernoticetitle) {
			this.usernoticetitle = usernoticetitle;
		}
		public String getUsernoticecontent() {
			return usernoticecontent;
		}
		public void setUsernoticecontent(String usernoticecontent) {
			this.usernoticecontent = usernoticecontent;
		}
		public String getClientaction() {
			return clientaction;
		}
		public void setClientaction(String clientaction) {
			this.clientaction = clientaction;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
	}
	
	
}
