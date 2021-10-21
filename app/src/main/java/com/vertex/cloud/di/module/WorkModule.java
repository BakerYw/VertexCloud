package com.vertex.cloud.di.module;

import dagger.Binds;
import dagger.Module;

import com.vertex.cloud.mvp.contract.WorkContract;
import com.vertex.cloud.mvp.model.WorkModel;

@Module
public abstract class WorkModule {

    @Binds
    abstract WorkContract.Model bindWorkModel(WorkModel model);
}