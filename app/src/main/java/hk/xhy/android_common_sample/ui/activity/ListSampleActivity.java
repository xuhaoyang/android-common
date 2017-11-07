package hk.xhy.android_common_sample.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android.common.ui.vh.OnListItemClickListener;
import hk.xhy.android.common.utils.AppUtils;
import hk.xhy.android.common.utils.BarUtils;
import hk.xhy.android.common.utils.GsonUtil;
import hk.xhy.android.common.widget.PullToRefreshMode;
import hk.xhy.android_common_sample.R;
import hk.xhy.android_common_sample.model.Item;
import hk.xhy.android_common_sample.ui.Base.ListActivity;
import hk.xhy.android_common_sample.ui.vh.SimpleStringViewHolder;
import hk.xhy.android_common_sample.utils.RecycleViewDivider;

public class ListSampleActivity extends ListActivity<SimpleStringViewHolder, Item, ArrayList<Item>>
        implements OnListItemClickListener {

    private int currentPage = 1;
    private static final String TAG = ListSampleActivity.class.getSimpleName();


    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sample);
        setSupportActionBar(toolbar);
        //设置item间间隔样式
        getRecyclerView().addItemDecoration(new RecycleViewDivider(this,
                LinearLayoutManager.VERTICAL));
        //设置下拉刷新颜色
        getPullToRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        /* 解决刷新动画出不来的问题 */
        getPullToRefreshLayout().setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        setMode(PullToRefreshMode.DISABLED);
        initLoader();

        //开启自定义底部加载item
        setFooterShowEnable(false);

        BarUtils.setStatusBarAlpha(this, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);// 其实这个只需要调用一次即可
    }

    @Override
    public SimpleStringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_string, parent, false);
        return new SimpleStringViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(SimpleStringViewHolder holder, int position) {
        holder.bind(getItemsSource().get(position).getShowname(), this);

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public ArrayList<Item> onLoadInBackground() throws Exception {
        int page = 0;
        if (isLoadMore()) {
            page = currentPage + 1;
        } else {
            page = 1;
        }

        ArrayList<Item> items = new ArrayList<>();


        items.add(new Item(0, "one", "第一个item", 0));
        items.add(new Item(1, "two", "第2个item", 0));
        items.add(new Item(2, "three", "第三个item", 0));


        return items;
    }

    @Override
    public void onLoadComplete(ArrayList<Item> data) {
        if (data != null) {
            getItemsSource().addAll(data);
        }
        getAdapter().notifyDataSetChanged();
        onRefreshComplete();
    }

    @Override
    public void OnListItemClick(int postion) {

    }

    @Override
    public void OnItemOtherClick(int postion, int type) {

    }
}
