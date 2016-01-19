package ufms.br.com.ufmsapp.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class CustomLinearGridLayoutManager extends GridLayoutManager {


    public CustomLinearGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}