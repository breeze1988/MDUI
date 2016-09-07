package com.breeze.myapplication.data;

/**
 * Created by xiaohong.wang@dmall.com on 2016/4/26.
 * description:
 */
public class HttpResult<T> {
    private String code;
    private String result;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
