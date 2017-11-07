package hk.xhy.android_common_sample.ui.Base;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.webkit.WebView;

import hk.xhy.android.common.utils.ActivityUtils;
import hk.xhy.android_common_sample.R;

/**
 * Created by xuhaoyang on 2017/9/14.
 */

public class WebViewActivity extends hk.xhy.android.common.ui.WebViewActivity {


    private final String TAG = this.getClass().getSimpleName();
    private final boolean isImmersion = true;

    /**
     * 是否沉浸
     *
     * @return
     */
    @Override
    protected boolean getImmersionStatus() {
        return isImmersion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //沉浸默认设置颜色
        if (getImmersionStatus()) {
            setImmersiveStatusBar(false,
                    ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //开启调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {

    }

    @Override
    public void onPageFinished(WebView webView, String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebView mWebView = getWebView();
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
        }
    }
}
