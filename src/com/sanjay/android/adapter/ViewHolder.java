package com.sanjay.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.*;

/**
 * 用于adapter缓存
 * Created by zsj_09@hotmail.com on 2015/3/6.
 */
public class ViewHolder {

    private SparseArray<View> views;
    private View convertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.views = new SparseArray<View>();

        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getView() {
        return convertView;
    }


    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /*-------------------------------------------*/
    /**
     * View的各种方法这边提供便捷入口,
     * 适合单一功能如果
     */

    public ViewHolder setText(int viewId, CharSequence text) {
        TextView view = retrieveView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        TextView view = retrieveView(viewId);
        view.setTextColor(color);
        return this;
    }

    public ViewHolder linkfy(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setAdapter(int viewId, Adapter adapter) {
        AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundResource(int viewId, int resId) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = retrieveView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public ViewHolder setAlpha(int viewId, float alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            retrieveView(viewId).setAlpha(alpha);
        } else {
            AlphaAnimation alphAn = new AlphaAnimation(alpha, alpha);
            alphAn.setDuration(0);
            alphAn.setFillAfter(true);
            retrieveView(viewId).startAnimation(alphAn);
        }
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener l) {
        View view = retrieveView(viewId);
        view.setOnClickListener(l);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener l) {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(l);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener l) {
        View view = retrieveView(viewId);
        view.setOnTouchListener(l);
        return this;
    }

}
