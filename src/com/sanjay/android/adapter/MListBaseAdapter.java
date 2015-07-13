package com.sanjay.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zsj_09@hotmail.com on 2015/4/7.
 */
public abstract class MListBaseAdapter<T> extends BaseAdapter {

    protected final Context mContext;
    protected List<T> mData = null;
    protected final int mLayoutResId;


    public MListBaseAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public MListBaseAdapter(Context context, int layoutResId, List<T> data) {
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mData = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        mViewHolder = ViewHolder.get(mContext, convertView, parent, mLayoutResId);

        setValue(mViewHolder, getItem(position), position);

        return mViewHolder.getView();
    }

    protected abstract void setValue(ViewHolder viewHolder, final T item, final int position);


    /**
     * list data
     */
    public void add(T elem) {
        mData.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        mData.addAll(elem);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T elem) {
        set(mData.indexOf(oldElem), elem);
    }

    public void set(int index, T elem) {
        mData.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mData.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mData.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mData.clear();
        mData.addAll(elem);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void clear(int start, int end) {
        mData = new ArrayList<T>(mData.subList(start, end));
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return mData.contains(elem);
    }


    public void sort(Comparator<? super T> comparator) {
        if (mData != null) {
            Collections.sort(mData, comparator);
        }
        notifyDataSetChanged();
    }

}
