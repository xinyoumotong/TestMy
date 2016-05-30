package hotP2B.WageGainTools.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.SystemTool;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;


/**
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的。 如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 * 实现该接口并注册为程序中的默认未捕获异常处理 这样当未捕获异常发生时，就可以做些异常处理操作 例如：收集异常信息，发送错误报告 等。
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告. <br>
 * 
 * <b>创建时间</b> 2014-7-2

 */
public class CrashHandler implements UncaughtExceptionHandler 
{
    private final Context mContext;
    
    public static final String FILE_NAME_SUFFIX = "hotp2b_crash.log";
    private static CrashHandler sInstance = null;

    private CrashHandler(Context cxt) 
    {
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = cxt;
    }

    public synchronized static CrashHandler create(Context cxt) 
    {
        if (sInstance == null) 
        {
            sInstance = new CrashHandler(cxt);
        }
        return sInstance;
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, final Throwable ex)
    {
        // 导出异常信息到SD卡中
        try 
        {
            saveToSDCard(ex);
        } catch (Exception e) 
        {
        } 
        finally 
        {
            ex.printStackTrace();// 调试时打印日志信息
            System.exit(0);
        }
    }


    private void saveToSDCard(Throwable ex) throws Exception {
        File file = FileUtils.getSaveFile(AppConfig.saveFolder,
                FILE_NAME_SUFFIX);
        boolean append = false;
        if (System.currentTimeMillis() - file.lastModified() > 5000) {
            append = true;
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                file, append)));
        // 导出发生异常的时间
        pw.println(SystemTool.getDataTime("yyyy-MM-dd HH:mm:ss"));
        // 导出手机信息
        dumpPhoneInfo(pw);
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
    }

    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        // 应用的版本名称和版本号
        pw.print("App Version: ");
        pw.print(SystemTool.getAppVersionName(mContext));
        pw.print('_');
        pw.println(SystemTool.getAppVersionCode(mContext));
        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();


    }
}
