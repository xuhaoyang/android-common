package hk.xhy.android.common.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Map;

import hk.xhy.android.common.R;

/**
 * Created by xuhaoyang on 2/24/16.
 */
public abstract class WebViewActivity extends BaseActivity {
    private ProgressBar mProgress;
    private WebView mWebView;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mProgress = (ProgressBar) findViewById(android.R.id.progress);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        mWebView.loadUrl(url, additionalHttpHeaders);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
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

    @SuppressLint("NewApi")
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    /**
     * >= Android 3.0
     * <  Android 5.0
     *
     * @param valueCallback
     * @param acceptType
     * @param capture
     */
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
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

        //>= Android 5.0
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return WebViewActivity.this.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return WebViewActivity.this.shouldOverrideUrlLoading(view, url);
        }


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

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {
            WebViewActivity.this.openFileChooser(valueCallback, acceptType, null);
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            WebViewActivity.this.openFileChooser(valueCallback, acceptType, capture);
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            return WebViewActivity.this.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

    };
}