package com.vertex.cloud.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.vertex.cloud.mvp.contract.CodeLoginContract;
import com.vertex.cloud.mvp.model.entity.CloudApiResult;
import com.vertex.cloud.mvp.model.entity.CodeEntity;
import com.vertex.cloud.mvp.model.entity.LoginEntity;

import java.util.HashMap;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2021 23:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CodeLoginPresenter extends BasePresenter<CodeLoginContract.Model, CodeLoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CodeLoginPresenter(CodeLoginContract.Model model, CodeLoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取图形验证码
     */
    public void getCode() {
        mModel.getCode().subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new ErrorHandleSubscriber<CloudApiResult<CodeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(CloudApiResult<CodeEntity> result) {
                        if (result.isSuccess()) {
                            mRootView.getCodeSuccess(result.getData());
                        } else {
                            ToastUtils.showShort(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    /**
     * 获取登录短信验证码
     */
    public void getSMSCode(String mobile, String port, String code, String uuid) {
        MediaType type = MediaType.parse("application/json; charset=UTF-8");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("code", code);
        jsonObject.addProperty("port", port);
        jsonObject.addProperty("uuid", uuid);
        RequestBody requestBody = RequestBody.create(type, jsonObject.toString());
        mModel.getSMSCode(requestBody).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new ErrorHandleSubscriber<CloudApiResult<CodeEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(CloudApiResult<CodeEntity> result) {
                        if (result.isSuccess()) {
                            mRootView.getSMSCodeSuccess(result.getData());
                        } else {
                            ToastUtils.showShort(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    /**
     * 短信快捷登录
     */
    public void smsLogin(String mobile, String code, String port) {
        MediaType type = MediaType.parse("application/json; charset=UTF-8");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("code", code);
        jsonObject.addProperty("port", port);
        RequestBody requestBody = RequestBody.create(type, jsonObject.toString());
        mModel.smsLogin(requestBody).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new ErrorHandleSubscriber<CloudApiResult<LoginEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(CloudApiResult<LoginEntity> result) {
                        if (result.isSuccess()) {
                            mRootView.smsLoginSuccess(result.getData());
                        } else {
                            ToastUtils.showShort(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}
