package com.example.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.baseapplication.R;


public class LoadingView extends Dialog {

    private String titleTxt = "加载中...";

    public Context context;

    private TextView mTitleTextView;

    public LoadingView(Context context) {
        super(context, R.style.ProgressDialogStyle);
        this.context = context;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        View mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = mDialogView.findViewById(R.id.tv_text);
        setTitleTxt(titleTxt);
    }

    public String getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
        if (mTitleTextView != null && titleTxt != null) {
            mTitleTextView.setText(titleTxt);
        }
    }
}
