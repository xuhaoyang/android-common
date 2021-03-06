package hk.xhy.android_common_sample.ui.Base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import hk.xhy.android.common.Application;
import hk.xhy.android.common.XHYInit;
import hk.xhy.android.common.utils.CrashUtils;
import hk.xhy.android.common.utils.FileUtils;
import hk.xhy.android.common.utils.LogUtils;
import hk.xhy.android_common_sample.BuildConfig;
import hk.xhy.android_common_sample.utils.ActivityUtils;

/**
 * Created by xuhaoyang on 2017/7/4.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        initCrash();
    }

    public static void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V);// log文件过滤器，和logcat过滤器同理，默认Verbose
        LogUtils.d(config.toString());
    }

    private void initCrash() {
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(Throwable e) {
                e.printStackTrace();
                restartApp();
            }
        });
    }


    private void restartApp() {
        Intent intent = new Intent();
        intent.setClassName("hk.xhy.android_common_sample", "hk.xhy.android_common_sample.ui.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (manager == null) return;
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 1, restartIntent);
        ActivityUtils.finishActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
