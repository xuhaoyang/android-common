package hk.xhy.android_common_sample.ui.Base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import hk.xhy.android.common.utils.ActivityUtils;
import hk.xhy.android.common.utils.LogUtils;
import hk.xhy.android.common.utils.Logger;
import hk.xhy.android.common.utils.ToastUtils;
import hk.xhy.android_common_sample.AppConfig;
import hk.xhy.android_common_sample.R;

/**
 * Created by xuhaoyang on 2017/7/4.
 */

public abstract class BaseActivity extends hk.xhy.android.common.ui.BaseActivity {
    protected String TAG = getClass().getSimpleName();
    protected BaseApplication application;
    protected SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUtils.addActivity(this);

        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        application = (BaseApplication) getApplication();

    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        ActivityUtils.startActivity(this, tarActivity);
    }

    protected void showLog(String msg) {
        LogUtils.d(msg);
    }

    protected void showToast(String msg) {
        ToastUtils.showShort(msg);
    }
}
