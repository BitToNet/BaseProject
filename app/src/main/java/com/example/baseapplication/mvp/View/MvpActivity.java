package com.example.baseapplication.mvp.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.baseapplication.R;
import com.example.baseapplication.mvp.Presenter.Presenter;
import com.example.library.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2020-08-24   09:52
 * desc   : mvc中c从activity抽出来，抽成p，activity里只留v层
 */
public class MvpActivity extends BaseActivity implements Presenter.IView {
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.content)
    LinearLayout content;
    private Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {
        presenter = new Presenter(this);
        presenter.load();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View injectTarget() {
        return content;
    }

    @Override
    public void showData(String data1, String data2) {
        et1.setText(data1);
        et2.setText(data2);
    }


    @OnClick({R.id.et1, R.id.et2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et1:
                presenter.etClick("et1："+et1.getText().toString());
                break;
            case R.id.et2:
                presenter.etClick();
                break;
        }
    }
}
