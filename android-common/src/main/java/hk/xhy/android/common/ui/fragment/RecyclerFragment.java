package hk.xhy.android.common.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import hk.xhy.android.common.R;
import hk.xhy.android.common.widget.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhaoyang on 5/26/16.
 */
public abstract class RecyclerFragment<VH extends RecyclerView.ViewHolder, Item, Result> extends LoaderFragment<Result> {

    protected PullToRefreshLayout mPullToRefreshLayout;
    protected RecyclerView mRecyclerView;

    //加载更多
    private int mCurrentScrollState;
    private OnLoadMoreListener onLoadMoreListener;

    protected PullToRefreshLayout.OnRefreshListener mOnRefreshListener;


    protected RecyclerView.Adapter<VH> mAdapter = new RecyclerView.Adapter<VH>() {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return RecyclerFragment.this.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            RecyclerFragment.this.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return RecyclerFragment.this.getItemCount();
        }

        @Override
        public long getItemId(int position) {
            return RecyclerFragment.this.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return RecyclerFragment.this.getItemViewType(position);
        }
    };


    private List<Item> mItemsSource;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        }
        mRecyclerView = (RecyclerView) view.findViewById(this.getRecyclerViewId());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView.invalidate();
                }
                mCurrentScrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == getAdapter().getItemCount() &&  mCurrentScrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    //To call OnRefresh when the RecyclerView scroll to the end
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    // PullToRefresh
    protected PullToRefreshLayout getPullToRefreshLayout() {
        return mPullToRefreshLayout;
    }

    public abstract void onRefresh();

    public void onRefreshComplete() {
        if (mPullToRefreshLayout == null) return;
        mPullToRefreshLayout.setRefreshing(false);
    }

    // RecyclerView
    protected abstract int getRecyclerViewId();

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setmOnRefreshListener(PullToRefreshLayout.OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    // Adapter && ViewHolder

    public RecyclerView.Adapter<VH> getAdapter() {
        return mAdapter;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    public int getItemCount() {
        if (mItemsSource != null) {
            return mItemsSource.size();
        }
        return 0;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public boolean isEmpty() {
        return mItemsSource == null || mItemsSource.size() == 0;
    }

    // ItemsSource
    public List<Item> getItemsSource() {
        if (mItemsSource == null) {
            mItemsSource = new ArrayList<>();
        }
        return mItemsSource;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}