package com.vertex.cloud.mvp.ui.work;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.vertex.cloud.app.base.CloudBaseFragment;
import com.vertex.cloud.di.component.DaggerWorkComponent;
import com.vertex.cloud.mvp.contract.WorkContract;
import com.vertex.cloud.mvp.presenter.WorkPresenter;

import com.vertex.cloud.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class WorkFragment extends CloudBaseFragment<WorkPresenter> implements WorkContract.View {

    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWorkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

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

    }
}