package com.vertex.cloud.mvp.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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
import com.vertex.cloud.app.dialog.VerCodeDialog;
import com.vertex.cloud.app.utils.CountDownTimer;
import com.vertex.cloud.app.view.ClearEditText;
import com.vertex.cloud.di.component.DaggerCodeLoginComponent;
import com.vertex.cloud.mvp.contract.CodeLoginContract;
import com.vertex.cloud.mvp.presenter.CodeLoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 验证码登录页
 */
public class CodeLoginActivity extends BaseActivity<CodeLoginPresenter> implements CodeLoginContract.View {

    @BindView(R.id.top_iv_left_img)
    ImageView topIvLeftImg;
    @BindView(R.id.top_tv_center_text)
    TextView topTvCenterText;
    @BindView(R.id.login_ed_phone)
    ClearEditText loginEdPhone;
    @BindView(R.id.login_ed_sms_code)
    ClearEditText loginSmsCode;
    @BindView(R.id.login_iv_select)
    ImageView loginIvSelect;
    @BindView(R.id.login_tv_get_code)
    TextView loginTvGetCode;
    @BindView(R.id.login_tv_agreement_text)
    TextView loginTvAgreementText;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.login_iv_we_chat)
    ImageView loginIvWeChat;

    private CountDownTimer mCountDownTimer;

    //判断协议是否选中
    private boolean isSelect = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCodeLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_code_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topTvCenterText.setText("登录/注册");

        setAgreementPart();
    }

    /**
     * 协议部分
     */
    private void setAgreementPart() {
        String agreementText = new String("未注册的手机号验证后将自动创建顶点云平台账号，登录即代表您已阅读《注册服务协议》、《用户隐私协议》");
        // SpannableStringBuilder(可跨越的字符串生成器) 用法
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(agreementText);
        // 设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#1389ff"));
        spannableBuilder.setSpan(colorSpan, 23, agreementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置《注册服务协议》点击事件
        ClickableSpan regServiceSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了注册服务协议");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                // ds.setColor(Color.parseColor("#3072F6"));
                // 设置下划线 true显示、false不显示
                ds.setUnderlineText(false);
            }
        };
        spannableBuilder.setSpan(regServiceSpan, 32, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置《用户隐私协议》点击事件
        ClickableSpan userPrivacySpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了用户隐私协议");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannableBuilder.setSpan(userPrivacySpan, 41, agreementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 不设置点击不生效
        loginTvAgreementText.setMovementMethod(LinkMovementMethod.getInstance());
        loginTvAgreementText.setText(spannableBuilder);
        // 去掉点击后文字的背景色
        loginTvAgreementText.setHighlightColor(Color.parseColor("#00000000"));
    }

    @OnClick({R.id.top_iv_left_img, R.id.login_btn_login, R.id.login_iv_we_chat, R.id.login_tv_get_code, R.id.login_iv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_iv_left_img:
                finish();
                break;
            case R.id.login_iv_select:
                //选中
                if (!isSelect) {
                    loginIvSelect.setImageResource(R.mipmap.ic_select);
                    isSelect = true;
                } else {
                    loginIvSelect.setImageResource(R.mipmap.ic_select_not);
                    isSelect = false;
                }
                break;
            case R.id.login_tv_get_code:
                //获取验证码
                VerCodeDialog verCodeDialog = new VerCodeDialog(this);
                verCodeDialog.show();

                //获取验证码
//                if (TextUtils.isEmpty(loginEdPhone.getText().toString().trim())) {
//                    ToastUtils.showShort("手机号不能为空");
//                    return;
//                }
//
//                if (CountDownTimer.iscountdown) {
//                    ToastUtils.showShort("验证码发送频繁，请稍后再试");
//                    break;
//                }
//
//                CountDownTimer.iscountdown = true;
//                mCountDownTimer = new CountDownTimer(loginTvGetCode, 60000, 1000);
//                mCountDownTimer.start();
                break;
            case R.id.login_btn_login:
                //登录
                break;
            case R.id.login_iv_we_chat:
                //微信登录
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
