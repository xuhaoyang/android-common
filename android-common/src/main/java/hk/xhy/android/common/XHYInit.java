package hk.xhy.android.common;

import android.content.Context;

import hk.xhy.android.common.utils.PreferenceUtils;
import hk.xhy.android.common.utils.Utils;
import android.app.Application;

/**
 * Created by xuhaoyang on 16/9/7.
 */
public class XHYInit {

    public static final boolean DEBUG = true;

    public static void initialize(Application application) {
        Utils.init(application);
    }
}
