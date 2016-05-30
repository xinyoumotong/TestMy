package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public abstract class HttpBaseCallBack<T extends BaseResponse>
{
	public abstract void onSuccess(T response);	
	public abstract void onFailure(int errorNo, String strMsg);
	public abstract void onFinish();
}
