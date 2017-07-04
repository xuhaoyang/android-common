package hk.xhy.android.common;

import android.content.Context;

import hk.xhy.android.common.utils.PreferenceUtils;
import hk.xhy.android.common.utils.Utils;

/**
 * Created by xuhaoyang on 16/9/7.
 */
public class XHYInit {

    public static final boolean DEBUG = true;

    public static void initialize(Context context) {
        Utils.init(context);
    }
}
