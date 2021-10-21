package com.vertex.cloud.di.module;

import dagger.Binds;
import dagger.Module;

import com.vertex.cloud.mvp.contract.InfoMationsContract;
import com.vertex.cloud.mvp.model.InfoMationsModel;

@Module
public abstract class InfoMationsModule {

    @Binds
    abstract InfoMationsContract.Model bindInfoMationsModel(InfoMationsModel model);
}