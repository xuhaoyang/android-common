package hk.xhy.android_common_sample.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android_common_sample.ui.activity.ListSampleActivity;
import hk.xhy.android_common_sample.utils.ActivityUtils;
import hk.xhy.android.common.utils.RegexUtils;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.ui.Base.BaseActivity;
import hk.xhy.android_common_sample.ui.activity.BrowserActivity;
import hk.xhy.android_common_sample.ui.activity.Html5Activity;
import hk.xhy.android_common_sample.utils.Constants;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewById(R.id.ed_url)
    EditText edUrl;
    @ViewById(R.id.btn_webview)
    AppCompatButton btn_webview;
    @ViewById(R.id.btn_list)
    AppCompatButton btn_list;

    private String url = "";
    @ViewById(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        btn_webview.setOnClickListener(this);
        btn_list.setOnClickListener(this);

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

//                ActivityUtils.startActivity(this, Html5Activity.class, new HashMap<String, Object>() {{
//                    put("url", url);
//                }});
                break;
            case R.id.btn_list:
                ActivityUtils.startActivity(this, ListSampleActivity.class);
                break;

        }

    }
}
