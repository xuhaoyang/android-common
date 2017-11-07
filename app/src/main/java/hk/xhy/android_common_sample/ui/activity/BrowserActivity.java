package hk.xhy.android_common_sample.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebView;

import java.util.HashMap;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android.common.utils.ActivityUtils;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.ui.Base.WebViewActivity;
import hk.xhy.android_common_sample.utils.Constants;

/**
 * Created by xuhaoyang on 2017/9/14.
 */

public class BrowserActivity extends WebViewActivity {

    private String title = "";
    private String url;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_nav_close);
        url = getIntent().getStringExtra(Constants.EXTRA_URL);
        title = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        toolbar.setTitle(title);
        loadUrl(url, new HashMap<String, String>() {{
            put("mdzz", "sic");
        }});

    }


    @Override
    protected void onStart() {
        super.onStart();

        //默认开启JavaScript支持
        setJavaScriptEnabled(true);
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        if (s.startsWith("sample://update_password/done")) {
            setResult(RESULT_OK);
            finish();
        }
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


}
