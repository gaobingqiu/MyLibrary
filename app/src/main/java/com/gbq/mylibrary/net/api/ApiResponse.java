package com.gbq.mylibrary.net.api;

@SuppressWarnings("ALL")
public class ApiResponse<T> {
    private int error_code;
    private String reason;
    private T result;

    public int getCode() {
        return error_code;
    }

    public void setCode(int code) {
        this.error_code = code;
    }

    public String getMsg() {
        return reason;
    }

    public void setMsg(String msg) {
        this.reason = msg;
    }

    public T getDatas() {
        return result;
    }

    public void setDatas(T datas) {
        this.result = datas;
    }

    public boolean isSuccess(){
        return error_code == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error_code=").append(error_code).append(" reason=").append(reason);
        if (null != result) {
            sb.append(" result:").append(result.toString());
        }
        return sb.toString();
    }
}