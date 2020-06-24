package com.example.library.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baseapplication.R;
import com.example.library.application.BaseApplication;
import com.example.library.utils.TimeUtils;
import com.example.library.widget.StateView;
import com.gyf.barlibrary.ImmersionBar;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2019/6/18  14:41
 * desc   : 基类
 */
@SuppressWarnings("ConstantConditions")
public abstract class BaseActivity extends AppCompatActivity
        implements BGASwipeBackHelper.Delegate {

    protected BGASwipeBackHelper mSwipeBackHelper;

    protected Context context;

    protected Activity activity;

    protected StateView mStateView;

    private long exitTime = 0L;
    private Unbinder unbinder;

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;


    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

        BaseApplication.activities.add(this);

        setContentView(getLayoutId());

        init();
        initView();
        loadData();
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    /**
     * 一些初始化准备
     */
    public void init() {
        unbinder = ButterKnife.bind(this);
        context = this;
        activity = this;
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        mStateView = StateView.inject(injectTarget());
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true)//解决软键盘与底部输入框冲突问题，默认为false
                .init();
    }

    protected void initImmersionBar(Toolbar toolbar, boolean isDark) {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(toolbar).keyboardEnable(true);//解决软键盘与底部输入框冲突问题，默认为false
        if (isDark) {
            mImmersionBar.statusBarDarkFont(true, 0.2f)// 解决白色状态栏问题
                    .init();
        } else {
            mImmersionBar.init();
        }
    }

    protected void initImmersionBar(View statusBarView, boolean isDark) {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarView(statusBarView).keyboardEnable(true);//解决软键盘与底部输入框冲突问题，默认为false
        if (isDark) {
            mImmersionBar.statusBarDarkFont(true, 0.2f)// 解决白色状态栏问题
                    .init();
        } else {
            mImmersionBar.init();
        }
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {

        toolbar.setTitle(title);
        toolbar.setContentInsetStartWithNavigation(0);
        toolbar.setContentInsetEndWithActions(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param tvTitle
     * @param homeAsUpEnabled
     * @param title
     */
    public void initToolBar(Toolbar toolbar, TextView tvTitle, boolean homeAsUpEnabled,
                            String title) {

        toolbar.setTitle("");
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    /**
     * 设置布局资源
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view操作
     *
     * @return
     */
    protected abstract void initView();

    /**
     * 初始化数据
     *
     * @return
     */
    protected abstract void loadData();

    protected abstract View injectTarget();

    /**
     * 接收前一个页面传递的String值
     *
     * @param key
     * @return
     */
    public String getStringExtra(String key) {
        Intent receive = getIntent();
        return receive.getStringExtra(key);
    }

    /**
     * 接收前一个页面传递的Integer值
     *
     * @param key
     * @return
     */
    public Integer getIntExtra(String key) {
        Intent receive = getIntent();
        return receive.getIntExtra(key, 0);
    }

    /**
     * 接收前一个页面传递的Long值
     *
     * @param key
     * @return
     */
    public Long getLongExtra(String key) {
        Intent receive = getIntent();
        return receive.getLongExtra(key, 0L);
    }

    /**
     * 接收前一个页面传递的Boolean值
     *
     * @param key
     * @return
     */
    protected Boolean getBooleanExtra(String key) {
        Intent receive = getIntent();
        return receive.getBooleanExtra(key, false);
    }

    public Object getSerializableExtra(String key) {
        Intent receive = getIntent();
        return receive.getSerializableExtra(key);
    }

    /**
     * 通过类名启动Activity，是否结束本页面
     *
     * @param pClass
     * @param tag
     */
    protected void openActivity(Class<?> pClass, int tag) {
        showActivity(pClass, null, null, null, false, tag, -1);
    }

    /**
     * 通过类名启动Activity，是否结束本页面
     *
     * @param pClass
     * @param isfinish
     */
    public void openActivity(Class<?> pClass, boolean isfinish) {
        showActivity(pClass, null, null, null, isfinish, -1, -1);
    }

    /**
     * 通过类名启动Activity，并且携带数据
     *
     * @param pClass
     * @param key
     * @param value
     */
    public void openActivity(Class<?> pClass, String key, Serializable value) {
        showActivity(pClass, null, key, value, false, -1, -1);
    }

    /**
     * 通过类名启动Activity，并且携带单个数据
     *
     * @param pClass
     * @param key
     * @param value
     * @param isfinish
     */
    public void openActivity(Class<?> pClass, String key, Serializable value, boolean isfinish) {
        showActivity(pClass, null, key, value, isfinish, -1, -1);
    }

    /**
     * 通过类名启动Activity，并且携带单个数据
     *
     * @param pClass
     * @param key
     * @param value
     * @param tag
     */
    public void openActivity(Class<?> pClass, String key, Serializable value, int tag) {
        showActivity(pClass, null, key, value, false, tag, -1);
    }

    /**
     * 通过类名启动Activity，并且含有Flags数据
     *
     * @param pClass
     * @param flags
     * @param isfinish
     */
    public void openActivity(Class<?> pClass, int flags, boolean isfinish) {
        showActivity(pClass, null, null, null, isfinish, -1, flags);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param bundle
     */
    public void openActivity(Class<?> pClass, Bundle bundle) {
        showActivity(pClass, bundle, null, null, false, -1, -1);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param bundle
     * @param isfinish
     */
    public void openActivity(Class<?> pClass, Bundle bundle, boolean isfinish) {
        showActivity(pClass, bundle, null, null, isfinish, -1, -1);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     * @param tag
     */
    public void openActivity(Class<?> pClass, Bundle pBundle, int tag) {
        showActivity(pClass, pBundle, null, null, false, tag, -1);
    }

    /**
     * @param pClass
     * @param bundle
     * @param key
     * @param value
     * @param isfinish
     * @param tag -1：startActivity 其他：startActivityForResult
     * @param flags
     */
    private void showActivity(Class<?> pClass, Bundle bundle, String key, Serializable value,
                              boolean isfinish, int tag, int flags) {

        Intent intent = new Intent(activity, pClass);

        if (null != key) {
            intent.putExtra(key, value);
        }

        if (null != bundle) {
            intent.putExtras(bundle);
        }

        if (flags != -1) {
            intent.setFlags(flags);
        }

        if (tag == -1) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, tag);
        }

        hideSoftKeyBoard();
        if (isfinish) {
            this.finish();
        }
    }

    private static Toast mToast;
    private static long time = 0;

    /**
     * 显示Toast
     * 点一次显示内容并且为短时间显示，若在两秒内点击两次，则更改为长时间显示
     * 不会重复创建Toast
     *
     * @param content
     */
    @SuppressLint("ShowToast")
    public void showToast(String content) {
        long temp = System.currentTimeMillis();
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        if (temp - time < TimeUtils.ONE_SECOND) {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        time = temp;
        mToast.show();
    }

    /**
     * 显示Toast
     * 点一次显示内容并且为短时间显示，若在两秒内点击两次，则更改为长时间显示
     * 不会重复创建Toast
     *
     * @param resId
     */
    @SuppressLint("ShowToast")
    public void showToast(@StringRes int resId) {
        long temp = System.currentTimeMillis();
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_SHORT);
        }
        mToast.setText(getString(resId));
        if (temp - time < TimeUtils.ONE_SECOND) {
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        time = temp;
        mToast.show();
    }

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > TimeUtils.ONE_SECOND * 2) {
            showToast("再按一次退出！");
            exitTime = System.currentTimeMillis();
        } else {
            BaseApplication.exit();
        }
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        this.imm = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
    }

    @Override
    public void onBackPressed() {
        hideSoftKeyBoard();
        ActivityCompat.finishAfterTransition(this);
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }
}


