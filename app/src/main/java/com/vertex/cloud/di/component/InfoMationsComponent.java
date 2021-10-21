package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.InfoMationsModule;
import com.vertex.cloud.mvp.contract.InfoMationsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.vertex.cloud.mvp.ui.infomation.InfoMationFragment;

@ActivityScope
@Component(modules = InfoMationsModule.class, dependencies = AppComponent.class)
public interface InfoMationsComponent {
    void inject(InfoMationFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InfoMationsComponent.Builder view(InfoMationsContract.View view);

        InfoMationsComponent.Builder appComponent(AppComponent appComponent);

        InfoMationsComponent build();
    }
}