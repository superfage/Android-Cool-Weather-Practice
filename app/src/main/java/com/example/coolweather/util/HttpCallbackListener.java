package com.example.coolweather.util;

public interface HttpCallbackListener {
    void onFinish(String respones);

    void onError(Exception e);
}
