package com.vertex.cloud.mvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseActivity;
import com.vertex.cloud.app.view.ClearEditText;
import com.vertex.cloud.di.component.DaggerRegisterComponent;
import com.vertex.cloud.mvp.contract.RegisterContract;
import com.vertex.cloud.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @Author CHEESE
 * @Version 1.0
 * @Describe 注册页
 **/
public class RegisterActivity extends CloudBaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.top_iv_left_img)
    ImageView topIvLeftImg;
    @BindView(R.id.top_tv_center_text)
    TextView topTvCenterText;
    @BindView(R.id.register_ed_phone)
    ClearEditText registerEdPhone;
    @BindView(R.id.register_ed_sms_code)
    ClearEditText registerEdSmsCode;
    @BindView(R.id.register_tv_get_code)
    TextView registerTvGetCode;
    @BindView(R.id.register_ed_pwd)
    ClearEditText registerEdPwd;
    @BindView(R.id.register_iv_eye)
    ImageView registerIvEye;
    @BindView(R.id.register_ed_again_pwd)
    ClearEditText registerEdAgainPwd;
    @BindView(R.id.register_btn_register)
    Button registerBtnRegister;

    //判断密码显示或隐藏
    private boolean isEye = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.top_iv_left_img, R.id.register_btn_register, R.id.register_iv_eye})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_iv_left_img:
                finish();
                break;
            case R.id.register_iv_eye:
                //密码显示与隐藏
                if (isEye) {
                    registerEdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    registerIvEye.setImageResource(R.mipmap.ic_show_pwd);
                    isEye = false;
                } else {
                    registerEdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    registerIvEye.setImageResource(R.mipmap.ic_eye);
                    isEye = true;
                }
                break;
            case R.id.register_btn_register:
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
