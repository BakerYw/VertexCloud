package com.vertex.cloud.app.base;

/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.vertex.cloud.R;
import com.vertex.cloud.app.utils.DispUtility;
import com.vertex.cloud.app.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.yokeyword.fragmentation.SupportActivity;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

/**
 * ================================================
 * 因为 Java 只能单继承, 所以如果要用到需要继承特定 {@link Activity} 的三方库, 那你就需要自己自定义 {@link Activity}
 * 继承于这个特定的 {@link Activity}, 然后再按照 {@link com.jess.arms.base.BaseActivity} 的格式, 将代码复制过去, 记住一定要实现{@link IActivity}
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">请配合官方 Wiki 文档学习本框架</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog">更新日志, 升级必看!</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/Issues">常见 Issues, 踩坑必看!</a>
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">MVPArms 官方组件化方案 ArmsComponent, 进阶指南!</a>
 * Created by JessYan on 22/03/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public abstract class CloudBaseActivity<P extends IPresenter> extends SupportActivity implements IActivity, ActivityLifecycleable, IView {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    private Dialog mLoadingDialog;//loading

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DispUtility.disabledDisplayDpiChange(this.getResources());
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        initLoadingDialog();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    protected void initLoadingDialog() {
        View loadingView = LayoutInflater.from(this).inflate(getLayoutLoading(),
                findViewById(android.R.id.content), false);
        mLoadingDialog = new Dialog(this, getLoadingStyle());
        mLoadingDialog.setContentView(loadingView);
        if (mLoadingDialog.getWindow() != null) {
            mLoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mLoadingDialog.setCanceledOnTouchOutside(false);
    }


    private int getLayoutLoading() {
        return R.layout.loading_common_layout;
    }

    protected int getLoadingStyle() {
        return R.style.CustomDialogStyle;
    }
    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个 {@link Activity} 是否会使用 {@link }Fragment, 框架会根据这个属性判断是否注册 {@link} ->FragmentLifecycleCallbacks
     * 如果返回 {@code false}, 那意味着这个 {@link Activity} 不需要绑定 {@link }Fragment, 那你再在这个 {@link Activity} 中绑定继承于 {@link BaseFragment} 的 {@link } -> Fragment将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link }Fragment
     */
    @Override
    public boolean useFragment() {
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initStatusBar();
    }

    protected void initStatusBar(){
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable((Drawable)(new ColorDrawable(Color.parseColor("#ffffff"))));
        }
        StatusBarUtil.setColor(this, Color.parseColor("#ffffff"), 0);
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog != null && !mLoadingDialog.isShowing() && !isFinishing()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed()) {
                return;
            }
            runOnUiThread(() -> {
                mLoadingDialog.show();
            });
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && !isFinishing()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed()) {
                return;
            }
            runOnUiThread(() -> {
                mLoadingDialog.dismiss();
            });
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }



}

