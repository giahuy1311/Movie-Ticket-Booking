package com.example.midtermproject.adapter;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by tasneem on 18/11/16.
 */

public class ItemDecorator extends RecyclerView.ItemDecoration {
    private final static int vertOverlap = -40;

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == 0) {
            return; }
        outRect.set(0, vertOverlap, 0, 0);


    }
}