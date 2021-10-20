package com.vertex.cloud.di.module;

import dagger.Binds;
import dagger.Module;

import com.vertex.cloud.mvp.contract.MainContract;
import com.vertex.cloud.mvp.model.MainModel;

@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}