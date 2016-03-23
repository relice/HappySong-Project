package baserecyclerviewadapter;

/**
 * 使用方法：继承此adapter，实现相应的方法就可以完成一个带下拉刷新的adapter,接收recyclerview
 * <p/>
 * Created by allen on 1/14/2016 17:25.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.relicemxd.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 0;
    private final int VIEW_PROG = 1;

    public List<T> datas;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private int defaultLoadItem = 5;
    private OnItemClickListener mListener;
    private ProgressViewHolder progressHolder;
    private Context mContext;
    private boolean isLastItem;
    private boolean isNotData;

    private boolean isLastVisibleItem;


    public BaseRecyclerViewAdapter(List<T> datas, RecyclerView recyclerView) {
        this.datas = datas;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager ||
                recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();

                            if (mListener != null) {
                                mListener.getCurrentDistance(dx, dy);
                                mListener.currentItem(totalItemCount, lastVisibleItem,
                                        linearLayoutManager.findFirstVisibleItemPosition());
                            }

                            handleLoadMoreNotData(totalItemCount, lastVisibleItem);


                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold + defaultLoadItem)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    /**
     * 处理加载更多已经没有数据的情况
     *
     * @param totalItemCount
     * @param lastVisibleItem
     */
    private boolean isFirstLoadToast = true;

    private void handleLoadMoreNotData(int totalItemCount, int lastVisibleItem) {

        if (totalItemCount <= lastVisibleItem + 2) {
            if (isNotData) {
                setRefreshCompleted();
                if (isFirstLoadToast) {
                    Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    isFirstLoadToast = false;
                }
            }
        } else {

            isFirstLoadToast = true;
        }

    }

    public void setIsNotData(boolean isNotData) {
        this.isNotData = isNotData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < datas.size()) {
            return VIEW_ITEM;
        } else {
            return VIEW_PROG;
        }
    }


    /**
     * 当刷新完成后调用以隐藏progressbar
     */
    public void setRefreshCompleted() {

        if (progressHolder != null)
            progressHolder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        mContext = parent.getContext();

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {

            View view = createView(parent, viewType);
            vh = createViewHolder(view);

        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);

            this.progressHolder = (ProgressViewHolder) vh;
        }
        return vh;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof ProgressViewHolder) {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

            if (!checkNetworkInfo(mContext)) {
                setRefreshCompleted();
                Toast.makeText(mContext, "网络异常,请检查后再试", Toast.LENGTH_SHORT).show();
                isLastItem = false;
            }

            if (!isNotData) {
                setProgressBarVisible();
            } else {
                setRefreshCompleted();
            }
            isLastItem = true;

        } else {
            showData(holder, position, datas);


            if (isLastItem)
                setProgressBarVisible();


//            if (!isNotData)
//                isLastItem = false;
            isLastItem = false;
        }


        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    /**
     * 设置progressbar显示
     */
    private void setProgressBarVisible() {
        if (progressHolder != null)
            progressHolder.progressBar.setVisibility(View.VISIBLE);
    }


    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {

        if (datas != null)
            return datas.size() + 1;
        return 0;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 当没有更多数据时调用
     */
    public void setNotData() {
        isNotData = true;

    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void getCurrentDistance(int dx, int dy);

        void currentItem(int totalItem, int lastVisItem, int firstVisItem);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mListener = listener;
    }

    /**
     * 设置从倒数第几个Item进行预加载,默认加载为5
     *
     * @param count 表示倒数第几个item
     */
    public void setPreItemLoad(int count) {
        if (count >= 0) {
            defaultLoadItem = count;
        }
    }

    /**
     * 获取当前网络连接状态
     *
     * @param context
     * @return boolean
     */
    public static boolean checkNetworkInfo(Context context) {
        try {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);

            NetworkInfo mobileNetInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo.State mobile = null, wifi = null;
            if (mobileNetInfo != null)
                // 获取3G网络状态
                mobile = mobileNetInfo.getState();
            if (wifiNetInfo != null)
                // 获取wifi网络状态
                wifi = wifiNetInfo.getState();

            // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
            if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
                return true;
            if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * 判断是否是底部，实现带下拉刷新的gridview时有用
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        return datas.size() == position;
    }


    /**
     * 添加数据
     *
     * @param list
     */
    public void setData(List<T> list) {

        datas.addAll(list);

        notifyDataSetChanged();
    }

    public void clearData() {
        datas = new ArrayList<>();
        datas.clear();
    }

    /**
     * 通过holder，绑定数据与view
     *
     * @param holder
     * @param position
     * @param datas
     */
    protected abstract void showData(RecyclerView.ViewHolder holder, int position, List datas);


    /**
     * 通过holder对象
     *
     * @param view
     * @return
     */
    protected abstract RecyclerView.ViewHolder createViewHolder(View view);

    /**
     * 创建item的view
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View createView(ViewGroup parent, int viewType);
}


