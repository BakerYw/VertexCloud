package com.vertex.cloud.app.utils;

import android.widget.Button;
import android.widget.TextView;

public class CountDownTimer extends android.os.CountDownTimer {
    public static boolean iscountdown = false;
    private TextView textView = null;

    public CountDownTimer(TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = button;
    }

    @Override
    public void onFinish() {
        iscountdown = false;
        textView.setText("获取验证码");
        cancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setText("重新获取" + millisUntilFinished / 1000);
    }

}
