package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.MainModule;
import com.vertex.cloud.mvp.contract.MainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.vertex.cloud.mvp.ui.activity.MainActivity;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();
    }
}