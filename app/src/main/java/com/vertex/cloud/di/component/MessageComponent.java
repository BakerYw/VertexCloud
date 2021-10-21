package com.vertex.cloud.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.vertex.cloud.di.module.MessageModule;
import com.vertex.cloud.mvp.contract.MessageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.vertex.cloud.mvp.ui.message.MessageFragment;;

@FragmentScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    void inject(MessageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageComponent.Builder view(MessageContract.View view);

        MessageComponent.Builder appComponent(AppComponent appComponent);

        MessageComponent build();
    }
}