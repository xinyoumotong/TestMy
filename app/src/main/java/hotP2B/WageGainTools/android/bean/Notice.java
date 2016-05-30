package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class Notice extends BaseResponse
{
	private static final long serialVersionUID = 1L;
	private List<NoticeItem> data;

	public List<NoticeItem> getList() {
		return data;
	}

	public void setList(List<NoticeItem> list) {
		this.data = list;
	}
    
	public static class NoticeItem
	{
		private String noticeId;
		private String noticetitle;
		private String noticecontent;
		private String effectivetime;
		private String noticeurl;
		private int clientaction;
		
		public String getNoticeId() {
			return noticeId;
		}
		public void setNoticeId(String noticeId) {
			this.noticeId = noticeId;
		}
		public String getNoticetitle() {
			return noticetitle;
		}
		public void setNoticetitle(String noticetitle) {
			this.noticetitle = noticetitle;
		}
		public String getNoticecontent() {
			return noticecontent;
		}
		public void setNoticecontent(String noticecontent) {
			this.noticecontent = noticecontent;
		}
		public String getEffectivetime() {
			return effectivetime;
		}
		public void setEffectivetime(String effectivetime) {
			this.effectivetime = effectivetime;
		}
		public String getNoticeurl() {
			return noticeurl;
		}
		public void setNoticeurl(String noticeurl) {
			this.noticeurl = noticeurl;
		}
		public int getClientaction() {
			return clientaction;
		}
		public void setClientaction(int clientaction) {
			this.clientaction = clientaction;
		}
		
	}
	
}
