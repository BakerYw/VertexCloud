package com.vertex.cloud.mvp.ui.main.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.vertex.cloud.app.base.CloudBaseActivity;

import com.vertex.cloud.R;
import com.vertex.cloud.mvp.ui.main.fragment.MainFragment;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends CloudBaseActivity<IPresenter>{

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.main_frame, new MainFragment());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 启动一个其他的Fragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    private Long exitTime  = 0L;

    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - this.exitTime > 2000L) {
            ToastUtils.showShort("再按一次退出程序");
            this.exitTime = System.currentTimeMillis();
            return;
        }else {
            super.onBackPressedSupport();
        }
    }
}