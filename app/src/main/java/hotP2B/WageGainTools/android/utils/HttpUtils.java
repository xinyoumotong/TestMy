package hotP2B.WageGainTools.android.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.SystemTool;

import com.google.gson.GsonBuilder;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.NotProguard;
import hotP2B.WageGainTools.android.bean.BaseRequest;
import hotP2B.WageGainTools.android.bean.BaseResponse;
import hotP2B.WageGainTools.android.bean.response.ErrorResponse;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.TokenResponse;
import hotP2B.WageGainTools.android.utils.TokenUtils.TokenInfo;

@NotProguard
public class HttpUtils {

    //向服务器请求数据,不加密,对于获取Token特殊处理
    public static <T extends BaseResponse> void requestHttp(final Context context, final String strUrl, final String jsonParams, final boolean bToken, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        if (!SystemTool.checkNet(context)) {
            callback.onFailure(AppConfig.ERROR_NONETWORK, "没有网络");
            ErrorProgressUtils.programError(context,AppConfig.ERROR_NONETWORK,"没有网络");

            callback.onFinish();
            return;
        }

        KJHttp kjh = new KJHttp(AppConfig.GlobalHttpConfig);
        HttpParams params = new HttpParams();
        params.putJsonParams(jsonParams.toString());
        kjh.jsonPost(strUrl, params, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (t != null && t.length() > 0) {
                    try {
                        ErrorResponse errorResponse = new GsonBuilder().create().fromJson(t, ErrorResponse.class);
                        if (errorResponse != null && !errorResponse.getWgt_err_code().equals("")) {
                            ErrorProgressUtils.programError(context,Integer.parseInt(errorResponse.getWgt_err_code()),errorResponse.getErrmsg());
                            callback.onFailure(Integer.parseInt(errorResponse.getWgt_err_code()), errorResponse.getErrmsg());
                            return;
                        }
                    } catch (Exception e) {

                    }

                    String result = "";
                    if (bToken) {
                        result = AppContext.DecryptData(t, SystemTool.getPhoneIMEI(context));
                    } else {
                        result = t;
                    }
                    T response = null;
                    try {
                        response = new GsonBuilder().create().fromJson(result, cls);
                    } catch (Exception e) {
                        response = null;
                    }
                    if (response != null) {
                        callback.onSuccess(response);
                    } else {
                        callback.onFailure(AppConfig.ERROR_FORMAT, "转换数据失败:" + t);
                        ErrorProgressUtils.programError(context,AppConfig.ERROR_FORMAT,t);
                    }
                } else {
                    callback.onFailure(AppConfig.ERROR_NODATA, "从服务器返回数据为空");
                    ErrorProgressUtils.programError(context,AppConfig.ERROR_NODATA,"从服务器返回数据为空");

                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ErrorProgressUtils.programError(context, errorNo, strMsg);
                callback.onFailure(errorNo, strMsg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });

    }

    //向服务器请求数据,加密
    public static <T extends BaseResponse> void reqeustHttpWithCrypt(final Context context, final String strUrl, final String jsonParams, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        if (!SystemTool.checkNet(context)) {
            callback.onFailure(AppConfig.ERROR_NONETWORK, "没有网络");
            ErrorProgressUtils.programError(context,AppConfig.ERROR_NONETWORK,"没有网络");
            callback.onFinish();
            return;
        }
        reqeustHttpWithCrypt1(context, strUrl, jsonParams, cls, callback);
    }

    public static <T extends BaseResponse> void reqeustHttpWithCrypt1(final Context context, final String strUrl, final String jsonParams, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        TokenInfo token = null;
        token = TokenUtils.getLocalToken(context);
        if (token == null || token.isInvalid()) {
            getTokenFromServer(context, TokenResponse.class, new HttpBaseCallBack<TokenResponse>() {
                @Override
                public void onSuccess(TokenResponse response) {
                    TokenInfo tokenInfo = TokenUtils.parse2TokenInfo(response);

                    if (tokenInfo != null) {
                        TokenUtils.saveLocalToken(AppContext.application, tokenInfo);
                        reqeustHttpWithCrypt2(tokenInfo, context, strUrl, jsonParams, cls, callback);
                    } else {
                        callback.onFailure(AppConfig.ERROR_TOKEN, "从服务端获取Token失败");
                        ErrorProgressUtils.programError(context,AppConfig.ERROR_TOKEN,"从服务端获取Token失败");

                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    ErrorProgressUtils.programError(context, errorNo, strMsg);
                    callback.onFailure(AppConfig.ERROR_TOKEN, "从服务端获取Token失败");
                }

                @Override
                public void onFinish() {

                }

            });
        } else {
            reqeustHttpWithCrypt2(token, context, strUrl, jsonParams, cls, callback);
        }

    }

    public static <T extends BaseResponse> void reqeustHttpWithCrypt2(final TokenInfo token, final Context context, final String strUrl, final String jsonParams, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        KJHttp kjh = new KJHttp(AppConfig.GlobalHttpConfig);
        String url = strUrl.replace("[@ACCESS_TOKEN]", token.getToken());
        JSONArray goldenParams = new JSONArray();
        final String golden = AppContext.getGolden();

        JSONObject goldenObj = new JSONObject();
        try {
            goldenObj.put("golden", AppContext.getGoldenKeyByToken(golden, token));
            goldenParams.put(goldenObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpParams params = new HttpParams();
        String requestJson = AppContext.CryptData(jsonParams.toString(), golden);//加密传参的内容
        params.put("params", requestJson);//参数的密文
        params.put("golden", goldenParams.toString());//通过公钥加密golden
        kjh.post(url, params, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                if (t != null && t.length() > 0) {
                    try {
                        ErrorResponse errorResponse = new GsonBuilder().create().fromJson(t, ErrorResponse.class);
                        if (errorResponse != null && !errorResponse.getWgt_err_code().equals("")) {
                            if (callback != null) {
                                ErrorProgressUtils.programError(context,Integer.parseInt(errorResponse.getWgt_err_code()),errorResponse.getErrmsg());
                                callback.onFailure(Integer.parseInt(errorResponse.getWgt_err_code()), errorResponse.getErrmsg());
                            }
                            return;
                        }
                    } catch (Exception e) {
                    }
                    String result = AppContext.DecryptData(t, golden);

                    if (result != null && result.length() > 0) {
                        TokenUtils.updateTokenInvalidTime(context);//更新Token的有效时间

                        T response = null;
                        try {
                            response = new GsonBuilder().create().fromJson(result, cls);
                        } catch (Exception e) {
                            response = null;
                        }
                        if (response != null) {
                            callback.onSuccess(response);
                        } else {
                            callback.onFailure(AppConfig.ERROR_FORMAT, "转换数据失败:" + result);
                            ErrorProgressUtils.programError(context,AppConfig.ERROR_FORMAT,result);

                        }
                    } else {
                        callback.onFailure(AppConfig.ERROR_DECRYPT, "解密数据失败:" + t);
                        ErrorProgressUtils.programError(context,AppConfig.ERROR_DECRYPT,t);

                    }

                } else {
                    callback.onFailure(AppConfig.ERROR_NODATA, "从服务器返回数据为空");
                    ErrorProgressUtils.programError(context,AppConfig.ERROR_NODATA,"从服务器返回数据为空");

                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ErrorProgressUtils.programError(context, errorNo, strMsg);
                callback.onFailure(errorNo, strMsg);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }

        });
    }

    //请求浏览器
    public static void requestBrowser(final Context context, final String strUrl, final String strTitle, final String jsonParams) {
        if (!SystemTool.checkNet(context)) {
            ViewInject.toast("错误代码:" + AppConfig.ERROR_NONETWORK + ",错误信息:没有网络");
            return;
        }
        requestBrowser1(context, strUrl, strTitle, jsonParams);
    }

    public static void requestBrowser1(final Context context, final String strUrl, final String strTitle, final String jsonParams) {
        TokenInfo token = null;
        token = TokenUtils.getLocalToken(context);
        if (token == null || token.isInvalid()) {
            getTokenFromServer(context, TokenResponse.class, new HttpBaseCallBack<TokenResponse>() {
                @Override
                public void onSuccess(TokenResponse response) {
                    TokenInfo tokenInfo = TokenUtils.parse2TokenInfo(response);
                    if (tokenInfo != null) {

                        requestBrowser2(tokenInfo, context, strUrl, strTitle, jsonParams);
                    } else {
                        ViewInject.toast("从服务端获取Token失败");
                    }
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
//                    ErrorProgressUtils.programError(context, errorNo, strMsg);
                    ViewInject.toast("从服务端获取Token失败");
                }

                @Override
                public void onFinish() {

                }

            });
        } else {
            requestBrowser2(token, context, strUrl, strTitle, jsonParams);
        }

    }

    public static void requestBrowser2(final TokenInfo token, final Context context, final String strUrl, final String strTitle, final String jsonParams) {
        String url = strUrl.replace("[@ACCESS_TOKEN]", token.getToken());
        JSONArray goldenParams = new JSONArray();
        final String golden = AppContext.getGolden();
        JSONObject goldenObj = new JSONObject();
        try {
            goldenObj.put("golden", AppContext.getGoldenKeyByToken(golden, token));
            goldenParams.put(goldenObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String requestJson = AppContext.CryptData(jsonParams.toString(), golden);//加密传参的内容
        url = url + "&params=" + requestJson + "&golden=" + goldenParams.toString();

        UIHelper.toHuifu(context, url, strTitle);
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++接口++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //获取Token
    public static <T extends BaseResponse> void getTokenFromServer(final Context context, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestParams.add("device_os", "android-" + Build.VERSION.RELEASE);
        requestParams.add("device_osversion", Build.VERSION.RELEASE);
        requestParams.add("network_type", SystemTool.isWiFi(context) ? "1" : "0");
        requestHttp(context, AppConfig.URL_GET_TOKEN, requestParams.toString(), true, cls, callback);
    }


    //自动登录&&登录
    public static <T extends BaseResponse> void loginFromServer(final Context context, final String username, final String userpass, final String passtype, final String loginkeyid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("passtype", passtype);
        requestParams.add("username", username);
        requestParams.add("userpass", userpass);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestParams.add("versionname", SystemTool.getAppVersionName(context));
        requestParams.add("versioncode", SystemTool.getAppVersionCode(context) + "");
        if (passtype.equals("2")) {
            requestParams.add("loginkeyid", loginkeyid);
        }

        reqeustHttpWithCrypt(context, AppConfig.URL_LOGIN, requestParams.toString(), cls, callback);

    }


    //获取验证码,两种URL
    public static <T extends BaseResponse> void getVerifyCodeFromServer(final Context context, final String mobile, final boolean bFromRegister, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("mobile", mobile);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        String url = "";
        if (bFromRegister) {
            url = AppConfig.URL_GET_VERIFYCODE;
        } else {
            url = AppConfig.URL_GET_VERIFYCODE_FORRESETPWD;
        }
        reqeustHttpWithCrypt(context, url, requestParams.toString(), cls, callback);

    }

    public static <T extends BaseResponse> void getSMSFromServer(final Context context, final String mobile, final String type, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("mobile", mobile);
        requestParams.add("type", type);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_SMS, requestParams.toString(), cls, callback);

    }

    //注册
    public static <T extends BaseResponse> void registerFromServer(final Context context, final String mobile, final String userpass, final String mobilcode, final String authorityid, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("mobile", mobile);
        requestParams.add("userpass", userpass);
        requestParams.add("mobilcode", mobilcode);
        requestParams.add("authorityid", authorityid);
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_REGISTER, requestParams.toString(), cls, callback);

    }

    //实名认证
    public static <T extends BaseResponse> void realauthFromServer(final Context context, final String mobile, final String reallyname, final String idnumber, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("mobile", mobile);
        requestParams.add("reallyname", reallyname);
        requestParams.add("idnumber", idnumber);
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_REALAUTH, requestParams.toString(), cls, callback);

    }

    //获取全局通知
    public static <T extends BaseResponse> void getGlobalNoticeFromServer(Context context, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_GET_GLOBALNOTICE, requestParams.toString(), cls, callback);
    }

    //获取广播通知
    public static <T extends BaseResponse> void getBroadcastNoticeFromServer(Context context, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestHttp(context, AppConfig.URL_GET_BROADCASTNOTICE, requestParams.toString(), false, cls, callback);
    }

    //获取用户通知
    public static <T extends BaseResponse> void getUserNoticeFromServer(final Context context, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_GET_USERNOTICE, requestParams.toString(), cls, callback);
    }

    //修改密码
    public static <T extends BaseResponse> void changePwdFromServer(final Context context, final String oldpassword, final String newpassword, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("oldpassword", oldpassword);
        requestParams.add("newpassword", newpassword);
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_CHANGEPASSWORD, requestParams.toString(), cls, callback);

    }

    //上传头像
    public static <T extends BaseResponse> void updatePortraitFromServer(final Context context, final String userpkid, String strBmp, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestParams.add("image", strBmp);
        reqeustHttpWithCrypt(context, AppConfig.URL_UPDATEPORTRAIT, requestParams.toString(), cls, callback);

    }

    //获取APP更新信息,不加密
    public static <T extends BaseResponse> void getUpdateFromServer(final Context context, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestParams.add("app_name", AppConfig.APPNAME);
        requestHttp(context, AppConfig.URL_GET_UPDATE, requestParams.toString(), false, cls, callback);
    }

    //重置密码，获取新的密码
    public static <T extends BaseResponse> void resetPasswordFromServer(final Context context, final String mobile, final String mobilcode, final String authorityid, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("mobile", mobile);
        requestParams.add("mobilcode", mobilcode);
        requestParams.add("authorityid", authorityid);
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_GETTEMPOTATYPWD, requestParams.toString(), cls, callback);
    }

    //获取用户信息
    public static <T extends BaseResponse> void getPersonalFromServer(final Context context, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_GET_PERSONAL, requestParams.toString(), cls, callback);
    }

    //修改用户信息
    public static <T extends BaseResponse> void modifyPersonalFromServer(final Context context, final String userpkid, String strRealname, String strIdNumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("usertruename", strRealname);
        requestParams.add("useridnumber", strIdNumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        reqeustHttpWithCrypt(context, AppConfig.URL_UPDATE_PERSONAL, requestParams.toString(), cls, callback);
    }

    //获取发现内容,不加密
    public static <T extends BaseResponse> void getFindInfoFromServer(final Context context, final String newstype, String startnumber, String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {

        BaseRequest requestParams = new BaseRequest();
        requestParams.add("newstype", newstype);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));

        requestHttp(context, AppConfig.URL_GET_FIND, requestParams.toString(), false, cls, callback);
    }

    //开通账户,浏览器方式打开
    public static void openAccount(final Context context, final String userpkid) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_OPEN_ACCOUNT, "开通托管账户", requestParams.toString());
    }

    //绑定银行卡,浏览器方式打开
    public static void bindCard(final Context context, final String userpkid) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_BIND_BANKCARD, "绑定银行卡", requestParams.toString());
    }

    //提现,浏览器方式打开
    public static void withdraw(final Context context, final String userpkid, final String money) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("cash", money);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_WITHDRAW, "提现", requestParams.toString());
    }

    //充值,浏览器方式打开
    public static void recharge(final Context context, final String userpkid, final String money) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("rechargeAmt", money);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_RECHARGE, "充值", requestParams.toString());
    }

    //投资理财,浏览器方式打开
    public static void invest(final Context context, final String userpkid, final String bidproid, final String transamt) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("bidproid", bidproid);
        requestParams.add("transamt", transamt);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_INVEST, "投标", requestParams.toString());
    }

    //撤标,浏览器方式打开
    public static void flowbid(final Context context, final String userpkid, final String ordid, final String transamt, final String freezeid, final String bidproid) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("ordid", ordid);
        requestParams.add("transamt", transamt);
        requestParams.add("freezeid", freezeid);
        requestParams.add("bidproid", bidproid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_FLOWBID, "撤标", requestParams.toString());
    }

    //申请银行流水
    public static void applyBankWater(final Context context, final String userpkid, final String transamt, final String bankid, final String accountid) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("transamt", transamt);
        requestParams.add("bankid", bankid);
        requestParams.add("accountid", accountid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestBrowser(context, AppConfig.URL_HUIFU_APPLY_BANKWATER, "申请银行流水", requestParams.toString());
    }

    //获取理财产品
    public static <T extends BaseResponse> void getLicaiProductFromServer(final Context context, final String startnumber, final String pagenumber, final String financialtype, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("financialtype", financialtype);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestHttp(context, AppConfig.URL_GET_LICAIPRODUCT, requestParams.toString(), false, cls, callback);
    }

    //获取绑定银行卡信息
    public static <T extends BaseResponse> void getBankCardFromServer(final Context context, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_BANKCARD, requestParams.toString(), cls, callback);
    }

    //解绑银行卡
    public static <T extends BaseResponse> void unBindBankCardFromServer(final Context context, final String userpkid, String openacctid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("openacctid", openacctid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_UNBIND_BANKCARD, requestParams.toString(), cls, callback);
    }

    //获取资产信息
    public static <T extends BaseResponse> void getAssetsFromServer(final Context context, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_ASSETS, requestParams.toString(), cls, callback);
    }

    //获取余额信息
    public static <T extends BaseResponse> void getBalanceFromServer(final Context context, final String userpkid, final String startdate, final String enddate, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startdate", startdate);
        requestParams.add("enddate", enddate);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_BALANCE, requestParams.toString(), cls, callback);
    }

    //获取全部用户通知信息
    public static <T extends BaseResponse> void getAllUserNoticeFromServer(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_USERNOTICE_ALL, requestParams.toString(), cls, callback);
    }

    //获取全部全局通知信息
    public static <T extends BaseResponse> void getAllGlobalNoticeFromServer(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_GLOBALNOTICE_ALL, requestParams.toString(), cls, callback);
    }

    //获取用户收益信息
    public static <T extends BaseResponse> void getIncomeFromServer(final Context context, final String userpkid, final String startdate, final String enddate, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startdate", startdate);
        requestParams.add("enddate", enddate);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_INCOME, requestParams.toString(), cls, callback);
    }

    //获取投资中项目
    public static <T extends BaseResponse> void getInvestHistory1(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_INVEST_HISTORY1, requestParams.toString(), cls, callback);
    }

    //获取回款中项目
    public static <T extends BaseResponse> void getInvestHistory2(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_INVEST_HISTORY2, requestParams.toString(), cls, callback);
    }

    //获取待撤标项目
    public static <T extends BaseResponse> void getInvestHistory3(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_INVEST_HISTORY3, requestParams.toString(), cls, callback);
    }

    //检测余额
    public static <T extends BaseResponse> void getInvestBidCheck(final Context context, final String userpkid, final String bidproid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("bidproid", bidproid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_INVEST_BIDCHECK, requestParams.toString(), cls, callback);
    }

    //检测银行流水
    public static <T extends BaseResponse> void getBankWaterCheck(final Context context, final String userpkid, final String bankid, final String accountid, final String amountpercent, final String authorityid, final String mobile, final String mobilcode, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("bankid", bankid);
        requestParams.add("accountid", accountid);
        requestParams.add("amountpercent", amountpercent);
        requestParams.add("authorityid", authorityid);
        requestParams.add("mobile", mobile);
        requestParams.add("mobilcode", mobilcode);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_BANKWATER_CHECK, requestParams.toString(), cls, callback);
    }

    //获取标的详细信息
    public static <T extends BaseResponse> void getInvestBidInfo(final Context context, final String bidproid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("bidproid", bidproid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        requestHttp(context, AppConfig.URL_GET_BIDINFO, requestParams.toString(), false, cls, callback);
    }

    //获取红包资产信息
    public static <T extends BaseResponse> void getHongbaoInfo(final Context context, final String userpkid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_HONGBAOINFO, requestParams.toString(), cls, callback);
    }

    //设置红包
    public static <T extends BaseResponse> void setHongbao(final Context context, final String userpkid, final String setamount, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("setamount", setamount);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_SET_HONGBAO, requestParams.toString(), cls, callback);
    }

    //获取红包余额明细
    public static <T extends BaseResponse> void getHongbaoBalanceFromServer(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_HONGBAO_BALANCE, requestParams.toString(), cls, callback);
    }

    //获取红包收益明细
    public static <T extends BaseResponse> void getHongbaoIncomeFromServer(final Context context, final String userpkid, final String startnumber, final String pagenumber, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startnumber", startnumber);
        requestParams.add("pagenumber", pagenumber);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_HONGBAO_INCOME, requestParams.toString(), cls, callback);
    }

    public static <T extends BaseResponse> void rolloutHongbao(final Context context, final String userpkid, final String transfermoney, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("transfermoney", transfermoney);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_ROLLOUT_HONGBAO, requestParams.toString(), cls, callback);
    }

    public static <T extends BaseResponse> void openHongbao(final Context context, final String userpkid, final String ubrealtionid, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("ubrealtionid", ubrealtionid);
        requestParams.add("device_id", CommonUtils.getIMEI(context));
        reqeustHttpWithCrypt(context, AppConfig.URL_OPEN_HONGBAO, requestParams.toString(), cls, callback);
    }

    //获取工资信息
    public static <T extends BaseResponse> void getSalary(final Context context, final String userpkid, int startpage, int pagenum, final Class<T> cls, final HttpBaseCallBack<T> callback) {
        BaseRequest requestParams = new BaseRequest();
        requestParams.add("userpkid", userpkid);
        requestParams.add("startpage", startpage + "");
        requestParams.add("pagenum", pagenum + "");
        reqeustHttpWithCrypt(context, AppConfig.URL_GET_SALARY, requestParams.toString(), cls, callback);
    }

    //测试专用
    public static void test(final HttpCallBack callback) {
        KJHttp kjh = new KJHttp(AppConfig.GlobalHttpConfig);
        kjh.get("http://www.baidu.com", new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                callback.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
//                ErrorProgressUtils.programError(context,errorNo,strMsg);

                callback.onFailure(errorNo, strMsg);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }
        });
    }

}
