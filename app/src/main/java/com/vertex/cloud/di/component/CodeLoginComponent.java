package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.CodeLoginModule;
import com.vertex.cloud.mvp.contract.CodeLoginContract;

import com.jess.arms.di.scope.ActivityScope;
import com.vertex.cloud.mvp.ui.login.CodeLoginActivity;


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
@Component(modules = CodeLoginModule.class, dependencies = AppComponent.class)
public interface CodeLoginComponent {
    void inject(CodeLoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CodeLoginComponent.Builder view(CodeLoginContract.View view);

        CodeLoginComponent.Builder appComponent(AppComponent appComponent);

        CodeLoginComponent build();
    }
}