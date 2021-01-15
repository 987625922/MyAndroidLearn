package com.wyt.common.utils;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 分页工具类
 * Created by 张坚鸿 on 2019/1/10 11:25
 */
public class PagingUtil {

    public static final int TIMES_MAX = -1;

    /**
     * 通过总数和分页量获取页数
     *
     * @param count      总数
     * @param pageOffset 分页量
     * @return 页数
     */
    public static int getPages(int count, int pageOffset) {
        return (count / pageOffset) + (count % pageOffset > 0 ? 1 : 0);
    }

    //当前页码(第一页是1，这里0指还没开始)
    private int page = 0;
    //总的总数
    private int allPages;
    //加载监听
    private LoadMoreListener loadMoreListener;
    //暂无更多的回调次数
    private int noMoreTimes;


    /**
     * 绑定RecyclerView
     */
    public PagingUtil(RecyclerView recyclerView, LoadMoreListener listener) {
        this(recyclerView, 1, listener);
    }

    /**
     * 绑定RecyclerView
     *
     * @param recyclerView
     * @param listener
     * @param times        暂无更多的回调次数 - （默认一次，传入IMES_MAX表示无限次）
     */
    public PagingUtil(RecyclerView recyclerView, int times, LoadMoreListener listener) {
        this.loadMoreListener = listener;
        this.noMoreTimes = times;
        recyclerView.addOnScrollListener(new BaseOnScrollListener() {
            @Override
            public void onLoadMore() {
                handleHasMore();
            }
        });
    }

    /**
     * 完成加载 - 成功
     *
     * @param all    总的个数（不是页数）
     * @param offset 每页的个数
     */
    public void onLoadMoreSuccess(int all, int offset) {
        this.allPages = getPages(all, offset);
        page++;
    }


    private void handleHasMore() {
        if (loadMoreListener != null) {
            int next = page + 1;
            if (next > allPages && (noMoreTimes == TIMES_MAX || noMoreTimes > 0)) {
                if (noMoreTimes != TIMES_MAX) {
                    noMoreTimes--;
                }
                loadMoreListener.onNoMore();
            } else if (noMoreTimes == TIMES_MAX || noMoreTimes > 0) {
                loadMoreListener.onLoadMore(next, allPages);
            }
        }
    }

    public void notifyDataToOriginal() {
        page = 0;
        noMoreTimes = 0;
    }

    public interface LoadMoreListener {

        /**
         * 加载更多
         *
         * @param page 从1开始
         */
        void onLoadMore(int page, int allPages);

        /**
         * 暂无更多
         */
        void onNoMore();
    }

    public abstract class BaseOnScrollListener extends RecyclerView.OnScrollListener {

        private static final int VERTICAL = 1;
        private static final int HORIZONTAL = 0;

        //用来标记是否正在向最后一个滑动
        boolean isSlidingToLast = false;
        int orientation = VERTICAL;

        @SuppressLint("WrongConstant")
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            orientation = manager.getOrientation();
            // 当不滚动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的ItemPosition
                int lastVisibleItem = manager.findLastVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                // 判断是否滚动到底部，并且是向右滚动
                if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                    onLoadMore();
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
            if (orientation == VERTICAL) {
                //大于0表示正在向右滚动,小于等于0表示停止或向左滚动
                isSlidingToLast = dy > 0;
            } else if (orientation == HORIZONTAL) {
                //大于0表示正在向右滚动,小于等于0表示停止或向左滚动
                isSlidingToLast = dx > 0;
            }
        }

        /**
         * 加载更多回调
         */
        abstract void onLoadMore();
    }


}
