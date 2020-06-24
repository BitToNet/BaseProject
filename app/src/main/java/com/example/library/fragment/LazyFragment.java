package com.example.library.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library.fragment.BaseFragment;
import com.example.library.widget.StateView;

import butterknife.ButterKnife;

/**
 * author : 邱珑
 * e-mail : 502578360@qq.com
 * date   : 2019/6/27  11:58
 * desc   :
 */

public abstract class LazyFragment extends BaseFragment {

    // 是否可见
    protected boolean isVisible;
    // 标志位，标志Fragment已经初始化完成
    protected boolean isPrepared = false;
    // 是否第一次加载
    protected boolean isFirst    = true;

    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser 对用户是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    protected void onVisible() {
        //加载数据
        loadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initView();
    }

    /**
     * 一些初始化准备
     */
    @Override
    public void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        mStateView = StateView.inject(injectTarget());
        //初始化view的各控件
        isPrepared = true;
    }

    /**
     * 懒加载
     *
     * @return
     */
    @Override
    protected abstract void loadData();
}

