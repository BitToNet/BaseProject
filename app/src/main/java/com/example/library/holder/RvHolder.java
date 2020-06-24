package com.example.library.holder;

import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.library.utils.ScreenUtils;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2019/6/18  14:31
 * desc   :
 */
public class RvHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private final View mConvertView;

    private RvHolder(View v) {
        super(v);
        mConvertView = v;
        mViews = new SparseArray<>();
    }

    public static RvHolder get(ViewGroup parent, @LayoutRes int layoutId) {
        View convertView = LayoutInflater.from(parent.getContext())
                                         .inflate(layoutId, parent, false);
        return new RvHolder(convertView);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T getView(int id) {
        View v = mViews.get(id);
        if (v == null) {
            v = mConvertView.findViewById(id);
            mViews.put(id, v);
        }
        return (T) v;
    }

    public RvHolder setText(@IdRes int id, String value) {
        TextView view = getView(id);
        view.setText(value);
        return this;
    }

    public RvHolder setTextColor(@IdRes int id, @ColorRes int color) {
        TextView view = getView(id);
        view.setTextColor(ContextCompat.getColor(view.getContext(), color));
        return this;
    }

    public RvHolder setBackgroundRes(@IdRes int id, @DrawableRes int resId) {
        View view = getView(id);
        view.setBackgroundResource(resId);
        return this;
    }

    public RvHolder setVisibility(@IdRes int id, boolean isShow) {
        View view = getView(id);
        view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public RvHolder setVisibility(@IdRes int id, int visibile) {
        View view = getView(id);
        view.setVisibility(visibile);
        return this;
    }

    public RvHolder setImageRes(@IdRes int id, @DrawableRes int resourceId) {
        ImageView imageView = getView(id);
        imageView.setImageResource(resourceId);
        return this;
    }


    public RvHolder setEnabled(@IdRes int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    public RvHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RvHolder setImageUrl(@IdRes int viewId, String imageUrl) {
        ImageView drawableView = getView(viewId);
        RequestOptions options = new RequestOptions().centerCrop()
                                                     .priority(Priority.HIGH)
                                                     .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(drawableView.getContext()).load(imageUrl).apply(options).into(drawableView);
        return this;
    }

    /**
     * 设置View高度(高度占屏幕宽度的比例)
     *
     * @param type
     * @param screen
     */
    public RvHolder setViewHeight(@IdRes int viewId, float type, float screen) {
        View view = getView(viewId);

        int width = ScreenUtils.screenWidthPixel(view.getContext());

        int height = (int) ((width * 1.0f - screen) * type);

        /** 取控件View当前的布局参数 */
        ViewGroup.LayoutParams params = view.getLayoutParams();
        /** 控件的高强制设成height */
        params.height = height;
        /** 使设置好的布局参数应用到控件 */
        view.setLayoutParams(params);

        return this;
    }

    public RvHolder setBackgroundColor(@IdRes int viewId, String color) {
        View view = getView(viewId);
        view.setBackgroundColor(Color.parseColor(color));
        return this;
    }
}
