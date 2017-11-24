package hk.xhy.android_common_sample.ui.activity;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android.common.utils.ActivityUtils;
import hk.xhy.android.common.utils.LogUtils;
import hk.xhy.android.common.utils.ToastUtils;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.ui.Base.WebViewActivity;

/**
 * Created by xuhaoyang on 2017/11/22.
 */

public class WebviewJavaScriptDemoActivity extends WebViewActivity {


    private String title;
    private final static String LOADURL = "file:///android_asset/test.html";

    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.webview_btn)
    AppCompatButton webview_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_webwei_js_demo);
        setSupportActionBar(toolbar);

        loadUrl(LOADURL);

        getWebView().addJavascriptInterface(this, "test");
        webview_btn.setText("Native调用js函数");

        webview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 无参数调用
                getWebView().loadUrl("javascript:actionFromNative()");
                // 传递参数调用
                getWebView().loadUrl("javascript:actionFromNativeWithParam(" + "'come from Native'" + ")");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPageFinished(WebView view, String url) {

    }

    public void onReceivedTitle(WebView view, String title) {
        if (TextUtils.isEmpty(title)) {
            this.setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (getWebView().canGoBack()) {
            getWebView().goBack();
            return;
        }
//        super.onBackPressed();
        ActivityUtils.finishActivity();
    }


    @JavascriptInterface
    public void actionFromJs() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("js调用了Native函数");
                LogUtils.d("js调用了Native函数");
            }
        });
    }

    @JavascriptInterface
    public void actionFromJsWithParam(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort("js调用了Native函数传递参数：" + str);
                LogUtils.d("js调用了Native函数传递参数：" + str);

            }
        });

    }

    @JavascriptInterface
    public void actionFromJsShowWebViewVersion() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PackageInfo webViewPackageInfo = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    //TODO 找个android8.0的测试下
                    webViewPackageInfo = WebView.getCurrentWebViewPackage();
                    if (webViewPackageInfo == null) {
                        ToastUtils.showShort("webview==null");

                    } else {
                        ToastUtils.showShort(webViewPackageInfo.versionName);
                    }

                } else {
                    ToastUtils.showShort("没有这个方法");

                }
            }
        });

    }
}