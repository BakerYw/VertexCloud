package com.vertex.cloud.di.module;

import dagger.Binds;
import dagger.Module;

import com.vertex.cloud.mvp.contract.MessageContract;
import com.vertex.cloud.mvp.model.MessageModel;

@Module
public abstract class MessageModule {

    @Binds
    abstract MessageContract.Model bindMessageModel(MessageModel model);
}