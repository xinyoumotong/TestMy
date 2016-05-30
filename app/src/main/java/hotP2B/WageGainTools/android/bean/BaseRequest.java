package hotP2B.WageGainTools.android.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseRequest implements Serializable 
{
	public static final long serialVersionUID = 1L;
	
	private class RequestItem
	{
		@SuppressWarnings("unused")
		public String Key;
		@SuppressWarnings("unused")
		public String Value;
		
	}
	
	private List<RequestItem> params;
	
	public BaseRequest()
	{
		this.params=new ArrayList<RequestItem>();
	}
	
	public void add(String key,String value)
	{
		RequestItem requestItem=new RequestItem();
		requestItem.Key=key;
		requestItem.Value=value;
		params.add(requestItem);
	}
	
	@Override
	public String toString()
	{
		Gson gson=new GsonBuilder().create();
		return gson.toJson(params);
	}

}
