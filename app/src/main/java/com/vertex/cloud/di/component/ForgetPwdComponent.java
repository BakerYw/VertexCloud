package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.ForgetPwdModule;
import com.vertex.cloud.mvp.contract.ForgetPwdContract;

import com.jess.arms.di.scope.ActivityScope;
import com.vertex.cloud.mvp.ui.login.ForgetPwdActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2021 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ForgetPwdModule.class, dependencies = AppComponent.class)
public interface ForgetPwdComponent {
    void inject(ForgetPwdActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ForgetPwdComponent.Builder view(ForgetPwdContract.View view);

        ForgetPwdComponent.Builder appComponent(AppComponent appComponent);

        ForgetPwdComponent build();
    }
}