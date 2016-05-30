package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class BroadcastNotice extends BaseResponse 
{
	private static final long serialVersionUID = 1L;
	
	private List<BroadcastNoticeItem> data;

	public List<BroadcastNoticeItem> getList() {
		return data;
	}

	public void setList(List<BroadcastNoticeItem> list) {
		this.data = list;
	}
    
	public static class BroadcastNoticeItem
	{
		private String Id;
		private String Title;
		private String Subtitle;
		private String Content;
		private String Pushtime;
		private String Url;
		private String Type;
		
		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
		public String getSubtitle() {
			return Subtitle;
		}
		public void setSubtitle(String subtitle) {
			Subtitle = subtitle;
		}
		public String getContent() {
			return Content;
		}
		public void setContent(String content) {
			Content = content;
		}
		public String getPushtime() {
			return Pushtime;
		}
		public void setPushtime(String pushtime) {
			Pushtime = pushtime;
		}
		public String getUrl() {
			return Url;
		}
		public void setUrl(String url) {
			Url = url;
		}
		public String getType() {
			return Type;
		}
		public void setType(String type) {
			Type = type;
		}
		
	}
	
}
