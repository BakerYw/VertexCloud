package com.vertex.cloud.base;



import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AheadBaseTabAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public AheadBaseTabAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public AheadBaseTabAdapter(@Nullable List<T> data) {
        super(data);
    }

    public AheadBaseTabAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }


    private OnCallBackData<T> onCallBackData;

    @Override
    protected void convert(BaseViewHolder holder, T item) {
        if (onCallBackData != null) {
            onCallBackData.convertView(holder, item);
        }
    }

    public void setOnCallBackData(OnCallBackData<T> onCallBackData) {
        this.onCallBackData = onCallBackData;
    }

    public interface OnCallBackData<T> {
        void convertView(BaseViewHolder holder, T item);
    }

}