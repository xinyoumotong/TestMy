package hotP2B.WageGainTools.android.bean;

import java.util.List;

public class News extends BaseResponse
{
   private static final long serialVersionUID = 1L;
   
   private List<NewsItem> data;

   public List<NewsItem> getData() 
   {
	   return data;
   }

   public void setData(List<NewsItem> data) 
   {
	   this.data = data;
   }
   
   public static class NewsItem
   {
	   public String Title;
	   public String Subtitle;
	   public String Content;
	   public String Createtime;
	   public String Url;
	   public String Newstype;
	   public String ImageUrl;
   }
   
}
