package hotP2B.WageGainTools.android.utils;

import java.util.List;

import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.database.annotate.Id;

import android.content.Context;
import android.util.Base64;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.NotProguard;

public class GestureUtils 
{
	@NotProguard
	public static class xx
	{
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getXa() {
			return xa;
		}
		public void setXa(String xa) {
			this.xa = xa;
		}
		public String getXb() {
			return xb;
		}
		public void setXb(String xb) {
			this.xb = xb;
		}
		
		@Id
	    private int id; 
		private String xa;
		private String xb;
		
		public xx()
		{
	
		}
	}
	
	public static void save(Context context, String a,String b) 
	{
	   xx x=new xx();
	   x.setId(0);
	   x.setXa(a);
	   x.setXb(Encode(b,4));
	   
	   KJDB kjdb =AppContext.getKJDB(context);
	   try
	   {
	     kjdb.deleteByWhere(xx.class, "xa='"+a+"'");
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   kjdb.save(x);
	}
	
	public static String Encode(String data, int count) 
	{
		String strResult=data;
		
		for(int i=0;i<count;i++)
		{
			strResult=Base64.encodeToString(strResult.getBytes(),Base64.DEFAULT);
		}

		return strResult;
	}
	
	public static String Decode(String data, int count) 
	{
		String strResult=data;
		
		for(int i=0;i<count;i++)
		{
			strResult=new String(Base64.decode(strResult.getBytes(),Base64.DEFAULT));
		}

		return strResult;
	}

	public static xx get(Context context,String userpkid) 
	{
		xx result=null;
    	KJDB kjdb =AppContext.getKJDB(context);
        List<xx> datas = kjdb.findAllByWhere(xx.class,"xa='"+userpkid+"'");
        if (datas != null && datas.size() > 0) 
        {
        	result = datas.get(0);
        } 
        else 
        {
        	result=null;
        }
        return result;
	 }
}
