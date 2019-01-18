package com.example.niki.pokeapi;

import android.util.Log;
import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener{

    private int visibleBorder = 15;

    private int currentOffset = 0;

    private int previousTotalItemCount = 0;

    private boolean loading = true;

    private int startingIndex = 0;

    public EndlessScrollListener() {

    }

    public EndlessScrollListener(int visibleBorder) {
        this.visibleBorder = visibleBorder;
    }

    public EndlessScrollListener(int visibleBorder, int startingIndex){
        this.visibleBorder = visibleBorder;
        this.startingIndex = startingIndex;
        this.currentOffset = startingIndex;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(totalItemCount < previousTotalItemCount){
            currentOffset = startingIndex;
            previousTotalItemCount = totalItemCount;
            if(totalItemCount == 0)
                this.loading = true;
        }

        if(loading && (totalItemCount > previousTotalItemCount)) {
            Log.d("loadingFalse", loading+"");
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentOffset+=30;
        }

        if(!loading && (firstVisibleItem + visibleItemCount + visibleBorder) >= totalItemCount){
            Log.d("loadMore", currentOffset + " " +totalItemCount);
            loading = onLoadMore(currentOffset+30, totalItemCount);
        }
    }

    public abstract boolean onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }
}
