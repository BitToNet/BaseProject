package com.example.baseapplication.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.example.baseapplication.Constant.SUCCESS_CODE;

/**
 * author : 邱珑
 * e-mail : 502578360@qq.com
 * date   : 2019/6/18  16:20
 * desc   :
 */
public class BaseBean<T> implements Serializable {

    @SerializedName(value = "code", alternate = {"error", "resultCode"})
    private int code;

    @SerializedName(value = "message", alternate = {"error_message", "resultMessage"})
    private String message;

    @SerializedName(value = "data", alternate = {"doctors", "problems", "clinic_info"})
    private T data;

    @SerializedName(value = "shopcart_number", alternate = {"bound"})
    private int number;

    public BaseBean(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return SUCCESS_CODE == getCode() || 0 == getCode();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "{code="
                + code
                + ", message='"
                + message
                + '\''
                + ", data="
                + data
                + ", number="
                + number
                + '}';
    }
}