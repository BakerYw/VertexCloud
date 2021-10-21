package com.vertex.cloud.di.module;

import dagger.Binds;
import dagger.Module;

import com.vertex.cloud.mvp.contract.MineContract;
import com.vertex.cloud.mvp.model.MineModel;

@Module
public abstract class MineModule {

    @Binds
    abstract MineContract.Model bindMineModel(MineModel model);
}