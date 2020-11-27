package com.example.baseapplication.mvp.Presenter;


import com.example.baseapplication.mvp.DataCenter.DataCenter;

/**
 * author : Qiu Long
 * e-mail : 502578360@qq.com
 * date   : 2020-08-24   10:06
 * desc   :
 */
public class Presenter {
    IView iView;
    public Presenter(IView iView){
        this.iView = iView;
    }
    public void load(){
        String[] data = DataCenter.getData();
        iView.showData(data[0],data[1]);
    }

    public void etClick() {
        iView.showToast("被点击了");
    }

    public void etClick(String s) {
        iView.showToast(s);
    }

    public interface IView{
        void showData(String data1, String data2);
        void showToast(String s);
    }
}
