package hk.xhy.android_common_sample.ui.Base;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;

import hk.xhy.android.common.utils.ActivityUtils;

/**
 * Created by xuhaoyang on 2017/9/14.
 */

public class WebViewActivity extends hk.xhy.android.common.ui.WebViewActivity {


    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 默认启用JavaScript
        getWebView().getSettings().setJavaScriptEnabled(true);

        //开启调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
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
        if (mWebView!=null){
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