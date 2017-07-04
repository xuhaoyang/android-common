package hk.xhy.android_common_sample;

import hk.xhy.android.common.utils.PreferenceUtils;

/**
 * Created by xuhaoyang on 2017/7/4.
 */

public class AppConfig extends PreferenceUtils {

    private static final String TAG = AppConfig.class.getSimpleName();

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final boolean isShowLog = DEBUG;


}
