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
 * ?????? Java ???????????????, ??????????????????????????????????????? {@link Activity} ????????????, ?????????????????????????????? {@link Activity}
 * ???????????????????????? {@link Activity}, ??????????????? {@link com.jess.arms.base.BaseActivity} ?????????, ?????????????????????, ?????????????????????{@link IActivity}
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki">??????????????? Wiki ?????????????????????</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog">????????????, ????????????!</a>
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki/Issues">?????? Issues, ????????????!</a>
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">MVPArms ????????????????????? ArmsComponent, ????????????!</a>
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
    protected P mPresenter;//??????????????????????????????, Presenter ????????? null
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
            //??????initView??????0,?????????????????????setContentView(),??????????????? Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //?????????butterknife
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
            mPresenter.onDestroy();//????????????
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
     * ???????????? EventBus
     * Arms ???????????????????????????????????? EventBus, ???????????? EventBus, ??????????????????????????????????????? EventBus
     * ?????????????????? EventBus, greenrobot ??? EventBus ???????????? ???Android????????????????????????????????????????????? ????????? ????????? AndroidEventBus
     * ???????????????, ?????????????????? true, Arms ??????????????????????????? EventBus, ???????????????
     * ???????????????????????????????????????????????????????????????, ????????????????????? Arms ?????????
     *
     * @return ?????? {@code true} (????????? {@code true}), Arms ??????????????? EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * ?????? {@link Activity} ??????????????? {@link }Fragment, ????????????????????????????????????????????? {@link} ->FragmentLifecycleCallbacks
     * ???????????? {@code false}, ?????????????????? {@link Activity} ??????????????? {@link }Fragment, ?????????????????? {@link Activity} ?????????????????? {@link BaseFragment} ??? {@link } -> Fragment?????????????????????
     *
     * @return ?????? {@code true} (????????? {@code true}), ??????????????? {@link }Fragment
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

