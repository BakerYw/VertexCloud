package com.vertex.cloud.mvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseActivity;
import com.vertex.cloud.app.view.ClearEditText;
import com.vertex.cloud.di.component.DaggerLoginComponent;
import com.vertex.cloud.mvp.contract.LoginContract;
import com.vertex.cloud.mvp.presenter.LoginPresenter;
import com.vertex.cloud.mvp.ui.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 密码登录页
 */
public class PwdLoginActivity extends CloudBaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.top_iv_left_img)
    ImageView topIvLeftImg;
    @BindView(R.id.top_tv_center_text)
    TextView topTvCenterText;
    @BindView(R.id.login_ed_phone)
    ClearEditText loginEdPhone;
    @BindView(R.id.login_ed_pwd)
    ClearEditText loginEdPwd;
    @BindView(R.id.login_iv_eye)
    ImageView loginIvEye;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_tv_sms_code)
    TextView loginTvSmsCode;
    @BindView(R.id.login_tv_forget_pwd)
    TextView loginTvForgetPwd;

    //判断密码显示或隐藏
    private boolean isEye = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pwd_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topTvCenterText.setText("登录/注册");
        topIvLeftImg.setVisibility(View.GONE);
    }

    @OnClick({R.id.top_iv_left_img, R.id.login_iv_eye, R.id.login_btn_login, R.id.login_tv_sms_code, R.id.login_tv_forget_pwd, R.id.login_iv_we_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_iv_left_img:
                break;
            case R.id.login_iv_eye:
                //密码显示与隐藏
                if (isEye) {
                    loginEdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    loginIvEye.setImageResource(R.mipmap.ic_show_pwd);
                    isEye = false;
                } else {
                    loginEdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    loginIvEye.setImageResource(R.mipmap.ic_eye);
                    isEye = true;
                }
                break;
            case R.id.login_btn_login:
                //登录
//                if (TextUtils.isEmpty(loginEdPhone.getText().toString().trim())) {
//                    ToastUtils.showShort("手机号不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(loginEdPwd.getText().toString().trim())) {
//                    ToastUtils.showShort("密码不能为空");
//                    return;
//                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.login_tv_sms_code:
                //验证码登录
                startActivity(new Intent(this, CodeLoginActivity.class));
                break;
            case R.id.login_tv_forget_pwd:
                //忘记密码
                startActivity(new Intent(this, ForgetPwdActivity.class));
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
