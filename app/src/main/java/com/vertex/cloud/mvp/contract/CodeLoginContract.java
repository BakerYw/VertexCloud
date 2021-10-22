package com.vertex.cloud.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.vertex.cloud.mvp.model.entity.CloudApiResult;
import com.vertex.cloud.mvp.model.entity.CodeEntity;
import com.vertex.cloud.mvp.model.entity.LoginEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;


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
public interface CodeLoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void getCodeSuccess(CodeEntity data);

        void getSMSCodeSuccess(CodeEntity data);

        void smsLoginSuccess(LoginEntity data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CloudApiResult<CodeEntity>> getCode();

        Observable<CloudApiResult<CodeEntity>> getSMSCode(RequestBody requestBody);

        Observable<CloudApiResult<LoginEntity>> smsLogin(RequestBody requestBody);
    }
}
