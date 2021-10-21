package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.WorkModule;
import com.vertex.cloud.mvp.contract.WorkContract;

import com.jess.arms.di.scope.FragmentScope;
import com.vertex.cloud.mvp.ui.work.WorkFragment;;

@FragmentScope
@Component(modules = WorkModule.class, dependencies = AppComponent.class)
public interface WorkComponent {
    void inject(WorkFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WorkComponent.Builder view(WorkContract.View view);

        WorkComponent.Builder appComponent(AppComponent appComponent);

        WorkComponent build();
    }
}