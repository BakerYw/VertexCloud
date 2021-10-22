package com.vertex.cloud.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.vertex.cloud.mvp.contract.CodeLoginContract;
import com.vertex.cloud.mvp.model.entity.CloudApiResult;
import com.vertex.cloud.mvp.model.entity.CodeEntity;
import com.vertex.cloud.mvp.model.entity.LoginEntity;
import com.vertex.cloud.mvp.model.net.ApiService;

import io.reactivex.Observable;
import okhttp3.RequestBody;


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
public class CodeLoginModel extends BaseModel implements CodeLoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CodeLoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CloudApiResult<CodeEntity>> getCode() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCode();
    }

    @Override
    public Observable<CloudApiResult<CodeEntity>> getSMSCode(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getSMSCode(requestBody);
    }

    @Override
    public Observable<CloudApiResult<LoginEntity>> smsLogin(RequestBody requestBody) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).smsLogin(requestBody);
    }

}