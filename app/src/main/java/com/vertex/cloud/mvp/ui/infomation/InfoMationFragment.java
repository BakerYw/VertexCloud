package com.vertex.cloud.mvp.ui.infomation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.vertex.cloud.R;
import com.vertex.cloud.app.base.CloudBaseFragment;
import com.vertex.cloud.di.component.DaggerInfoMationsComponent;
import com.vertex.cloud.mvp.contract.InfoMationsContract;
import com.vertex.cloud.mvp.presenter.InfoMationsPresenter;
import com.vertex.cloud.mvp.ui.message.MessageFragment;

/**
 * @Author CHEESE
 * @Date 10/21/21 11:45 AM
 * @Version 1.0
 **/
public class InfoMationFragment extends CloudBaseFragment<InfoMationsPresenter> implements InfoMationsContract.View{

    public static InfoMationFragment newInstance() {
        InfoMationFragment fragment = new InfoMationFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerInfoMationsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infomations, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
