package hk.xhy.android.common.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import hk.xhy.android.common.R;
import hk.xhy.android.common.utils.ActivityUtils;
import hk.xhy.android.common.utils.EmptyUtils;

import java.util.Map;

/**
 * Created by xuhaoyang on 2/24/16.
 */
public abstract class WebViewActivity extends BaseActivity {
    private ProgressBar mProgress;
    private WebView mWebView;

    private String url;
    private Map<String, String> headers;

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ActivityUtils.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /**
         * 开启沉浸之后 会在原有layout外包一层做StatusBar padding处理
         * 会导致没法再onContextChanged当中或得到mWebView等对象
         * 故延后处理
         */
        mProgress = (ProgressBar) findViewById(android.R.id.progress);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);

        if (!TextUtils.isEmpty(this.url)) {
            if (!EmptyUtils.isEmpty(headers)) {
                loadUrl(url, headers);
            } else {
                loadUrl(url);
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (mWebView != null) {
            mWebView.loadUrl(url, additionalHttpHeaders);
        } else {
            this.url = url;
            this.headers = additionalHttpHeaders;
        }
    }


    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl(url);
        } else {
            this.url = url;
        }
    }

    public abstract void onPageStarted(WebView view, String url, Bitmap favicon);


    public abstract void onPageFinished(WebView view, String url);

    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
    }

    public void onProgressChanged(WebView view, int newProgress) {
        if (mProgress != null) {
            mProgress.setMax(100);
            mProgress.setProgress(newProgress);
            // 如果进度大于或者等于100，则隐藏进度条
            if (newProgress >= 100) {
                mProgress.setVisibility(View.GONE);
            }
        }
    }

    public void onReceivedTitle(WebView view, String title) {
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setJavaScriptEnabled(boolean b) {
        getWebView().getSettings().setJavaScriptEnabled(b);
    }

    public WebSettings getSettings() {
        return mWebView.getSettings();
    }

    public WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            WebViewActivity.this.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            WebViewActivity.this.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            WebViewActivity.this.onReceivedError(view, errorCode, description, failingUrl);
        }
    };

    public WebChromeClient mWebChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int newProgress) {
            WebViewActivity.this.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            WebViewActivity.this.onReceivedTitle(view, title);
        }


    };


}