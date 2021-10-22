package com.vertex.cloud.mvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vertex.cloud.R;
import com.vertex.cloud.app.utils.CountDownTimer;
import com.vertex.cloud.app.view.ClearEditText;
import com.vertex.cloud.di.component.DaggerForgetPwdComponent;
import com.vertex.cloud.mvp.contract.ForgetPwdContract;
import com.vertex.cloud.mvp.presenter.ForgetPwdPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 忘记密码页
 */
public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.View {

    @BindView(R.id.top_iv_left_img)
    ImageView topIvLeftImg;
    @BindView(R.id.top_tv_center_text)
    TextView topTvCenterText;
    @BindView(R.id.forget_ed_phone)
    ClearEditText forgetEdPhone;
    @BindView(R.id.forget_ed_pwd)
    ClearEditText forgetEdPwd;
    @BindView(R.id.forget_iv_eye)
    ImageView forgetIvEye;
    @BindView(R.id.forget_ed_sms_code)
    ClearEditText forgetEdSmsCode;
    @BindView(R.id.forget_tv_get_code)
    TextView forgetTvGetCode;
    @BindView(R.id.forget_btn_sure)
    Button forgetBtnSure;

    //判断密码显示或隐藏
    private boolean isEye = true;

    private CountDownTimer mCountDownTimer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerForgetPwdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_forget_pwd; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topTvCenterText.setText("忘记密码");
    }

    @OnClick({R.id.top_iv_left_img, R.id.forget_iv_eye, R.id.forget_tv_get_code, R.id.forget_btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_iv_left_img:
                finish();
                break;
            case R.id.forget_iv_eye:
                //密码显示与隐藏
                if (isEye) {
                    forgetEdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    forgetIvEye.setImageResource(R.mipmap.ic_show_pwd);
                    isEye = false;
                } else {
                    forgetEdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    forgetIvEye.setImageResource(R.mipmap.ic_eye);
                    isEye = true;
                }
                break;
            case R.id.forget_tv_get_code:
                //获取验证码
                if (TextUtils.isEmpty(forgetEdPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号不能为空");
                    return;
                }

                if (CountDownTimer.iscountdown) {
                    ToastUtils.showShort("验证码发送频繁，请稍后再试");
                    break;
                }

                CountDownTimer.iscountdown = true;
                mCountDownTimer = new CountDownTimer(forgetTvGetCode, 60000, 1000);
                mCountDownTimer.start();
                break;
            case R.id.forget_btn_sure:
                //确定
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

}
