package com.xingye.netzoo.xylib.utils.baseRecyclerAdatper.loadmore;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by BlingBling on 2016/11/11.
 */

public abstract class LoadMoreView {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    private View loadingView;
    private View loadingFailedView;
    private View loadingEndView;
    private boolean isLoadingViewNew = false;

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(RecyclerView.ViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_END:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
            case STATUS_DEFAULT:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
        }
        isLoadingViewNew = false;
    }

    private void visibleLoading(RecyclerView.ViewHolder holder, boolean visible) {
        if(loadingView == null || isLoadingViewNew){
            loadingView = holder.itemView.findViewById(getLoadingViewId());
        }
        if(loadingView!=null){
            loadingView.setVisibility(visible? View.VISIBLE: View.GONE);
        }
    }

    private void visibleLoadFail(RecyclerView.ViewHolder holder, boolean visible) {
        if(loadingFailedView == null || isLoadingViewNew){
            loadingFailedView = holder.itemView.findViewById(getLoadFailViewId());
        }
        if(loadingFailedView != null){
            loadingFailedView.setVisibility(visible? View.VISIBLE: View.GONE);
        }
    }

    private void visibleLoadEnd(RecyclerView.ViewHolder holder, boolean visible) {
        if(loadingEndView == null || isLoadingViewNew ){
            loadingEndView = holder.itemView.findViewById(getLoadEndViewId());
        }
        if(loadingEndView !=null){
            loadingEndView.setVisibility(visible? View.VISIBLE: View.GONE);
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone(){
        if(getLoadEndViewId()==0){
            return true;
        }
        return mLoadMoreEndGone;}

    public final void setLoadingViewNew(){
        this.isLoadingViewNew = true;
    }

    /**
     * No more data is hidden
     * @return true for no more data hidden load more
     * @deprecated Use {@link BaseQuickAdapter#loadMoreEnd(boolean)} instead.
     */
    @Deprecated
    public boolean isLoadEndGone(){return mLoadMoreEndGone;}

    /**
     * load more layout
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * loading view
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadingViewId();

    /**
     * load fail view
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadFailViewId();

    /**
     * load end view, you can return 0
     *
     * @return
     */
    protected abstract @IdRes
    int getLoadEndViewId();
}
