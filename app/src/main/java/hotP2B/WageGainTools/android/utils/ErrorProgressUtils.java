package hotP2B.WageGainTools.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import org.kymjs.kjframe.ui.ViewInject;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;

/**
 * Created by hll on 2016/5/11.
 */
public class ErrorProgressUtils {

    private static int ERROR_OFFLINE = 50000;   //目前离线 Currently offline
    private static int ERROR_MIMA = 40021;   //账号或密码错误
    private static int ERROR_DELETE_BANK_DEFAULT = 40031;   //默认卡不能删除
    private static int ERROR_NO_DATA = 5;   //解密数据失败
    private static int USERS_NO_EXIST = 40009;   //用户不存在

    public static void programError(final Context context, int errorNo, String strMsg) {

        Log.e("", "--------------------" + strMsg + "-----" + errorNo);

        if (errorNo == ERROR_OFFLINE | (strMsg.contains("50000") && strMsg.contains("Currently") && strMsg.contains(""))) {
            AppContext.logout((Activity) context);
        }

        else if (errorNo == ERROR_MIMA) {
            ViewInject.toast("账号或密码错误！");
        }

        else if (errorNo == USERS_NO_EXIST) {
            ViewInject.toast("用户不存在！");
        }

        else if (errorNo == ERROR_DELETE_BANK_DEFAULT) {}

        else if (errorNo == ERROR_NO_DATA && ("Undefined offset: 0").equals(strMsg)) {
            ViewInject.toast("没有更多数据了");
        }

        else if (strMsg.startsWith("Currently")) {
            DialogUtils.showAlertDialog(context, R.string.prompt, "由于您长时间未执行动作，已离线，是否重新登陆？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AppContext.logout((Activity) context);
                }
            }, null);
        }

        else {
            ViewInject.toast("错误代码:" + errorNo + ",错误信息:" + strMsg);
        }
    }

}
