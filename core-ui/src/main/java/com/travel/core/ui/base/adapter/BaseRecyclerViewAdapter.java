package com.travel.core.ui.base.adapter;

import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 *         16/4/15
 */
public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    protected void onItemHolderClick(RecyclerView.ViewHolder itemHolder) {
        if (onItemClickListener != null) {
            int adapterPosition = itemHolder.getBindingAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return;
            }
            onItemClickListener.onItemClick(null, itemHolder.itemView,
                    adapterPosition, itemHolder.getItemId());
        } else {
            throw new IllegalStateException("Please call setOnItemClickListener method set the click event listeners");
        }
    }

}
