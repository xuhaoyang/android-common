package hk.xhy.android_common_sample.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android.common.utils.ToastUtils;
import hk.xhy.android_common_sample.AppConfig;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.ui.Base.BaseActivity;
import hk.xhy.android_common_sample.ui.activity.BrowserActivity;
import hk.xhy.android_common_sample.ui.activity.EchatWebViewActivity;
import hk.xhy.android_common_sample.ui.activity.ListSampleActivity;
import hk.xhy.android_common_sample.ui.activity.OkhttpActivity;
import hk.xhy.android_common_sample.ui.activity.WebviewJavaScriptDemoActivity;
import hk.xhy.android_common_sample.utils.ActivityUtils;
import hk.xhy.android_common_sample.utils.Constants;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewById(R.id.ed_url)
    EditText edUrl;
    @ViewById(R.id.btn_webview)
    AppCompatButton btn_webview;
    @ViewById(R.id.btn_list)
    AppCompatButton btn_list;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.load)
    AppCompatButton btn_load;
    @ViewById(R.id.save)
    AppCompatButton btn_save;
    @ViewById(R.id.btn_webviewdemo1)
    AppCompatButton btn_webviewdemo1;
    @ViewById(R.id.btn_echat)
    AppCompatButton btn_echat;
    @ViewById(R.id.btn_okhttp)
    AppCompatButton btn_okhttp;


    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        btn_webview.setOnClickListener(this);
        btn_list.setOnClickListener(this);

        btn_load.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_webviewdemo1.setOnClickListener(this);
        btn_echat.setOnClickListener(this);
        btn_okhttp.setOnClickListener(this);
//        edUrl.setText();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_webview:
//                if (RegexUtils.isURL(edUrl.getText())) {
//                    showToast("不是url");
//                    return;
//                } else {
//                    url = edUrl.getText().toString();
//                }
                url = edUrl.getText().toString();


                ActivityUtils.startActivity(this, BrowserActivity.class, new HashMap<String, Object>() {{
                    put(Constants.EXTRA_URL, url);
                }});


                break;
            case R.id.load:
                edUrl.setText(AppConfig.loadUrl());
                break;
            case R.id.save:
                AppConfig.saveUrl(edUrl.getText().toString());
                break;
            case R.id.btn_webviewdemo1:
                ActivityUtils.startActivity(this, WebviewJavaScriptDemoActivity.class);
                break;

            case R.id.btn_list:
                ActivityUtils.startActivity(this, ListSampleActivity.class);
                break;

            case R.id.btn_echat:
                ActivityUtils.startActivity(this, EchatWebViewActivity.class);
                break;
            case R.id.btn_okhttp:
                ActivityUtils.startActivity(this, OkhttpActivity.class);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notifications:
                ToastUtils.showShort("action_notifications");
                ActivityUtils.startActivity(this, EchatWebViewActivity.class);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
