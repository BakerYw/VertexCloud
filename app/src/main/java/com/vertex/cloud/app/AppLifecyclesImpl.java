package com.vertex.cloud.app;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mmkv.MMKV;
import com.vertex.cloud.BuildConfig;
import com.vertex.cloud.app.widget.loadsir.EmptyCallback;
import com.vertex.cloud.app.widget.loadsir.ErrorCallback;
import com.vertex.cloud.app.widget.loadsir.LoadingCallback;
import com.vertex.cloud.mvp.ui.activity.MainActivity;

import butterknife.ButterKnife;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import leakcanary.LeakCanary;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;
import timber.log.Timber;


/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by MVPArmsTemplate on 08/18/2020 09:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(@NonNull Context base) {
        // 必须安装multiDex
        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        configUnits();
        Utils.init(application);
        //初始化MMKV
        MMKV.initialize(application.getFilesDir().getAbsolutePath() + "/cloud");
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
//                    Logger.addLogAdapter(new AndroidLogAdapter());
//                    Timber.plant(new Timber.DebugTree() {
//                        @Override
//                        protected void log(int priority, String tag, String message, Throwable t) {
//                            Logger.log(priority, tag, message, t);
//                        }
//                    });
            ButterKnife.setDebug(true);
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            ClassicsHeader classicsHeader = new ClassicsHeader(context);
            return classicsHeader;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            ClassicsFooter classicsFooter = new ClassicsFooter(context);
            classicsFooter.setFinishDuration(0);
            return classicsFooter;
        });
        //界面加载管理 初始化
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(SuccessCallback.class)//设置默认状态页
                .commit();

        //防止项目崩溃，崩溃后打开错误界面
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
                .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
                .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
                .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
                .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
                .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
                .restartActivity(MainActivity.class) // 重启的activity
//                .errorActivity(ErrorActivity.class) //发生错误跳转的activity
                .eventListener(null) //允许你指定事件侦听器，以便在库显示错误活动 default: null
                .apply();
    }

    private void configUnits() {
        //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
        //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
        //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
        //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
        //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp
        AutoSizeConfig.getInstance().getUnitsManager()
                .setSupportDP(false)
                //当使用者想将旧项目从主单位过渡到副单位, 或从副单位过渡到主单位时
                //因为在使用主单位时, 建议在 AndroidManifest 中填写设计图的 dp 尺寸, 比如 360 * 640
                //而副单位有一个特性是可以直接在 AndroidManifest 中填写设计图的 px 尺寸, 比如 1080 * 1920
                //但在 AndroidManifest 中却只能填写一套设计图尺寸, 并且已经填写了主单位的设计图尺寸
                //所以当项目中同时存在副单位和主单位, 并且副单位的设计图尺寸与主单位的设计图尺寸不同时, 可以通过 UnitsManager#setDesignSize() 方法配置
                //如果副单位的设计图尺寸与主单位的设计图尺寸相同, 则不需要调用 UnitsManager#setDesignSize(), 框架会自动使用 AndroidManifest 中填写的设计图尺寸
//                .setDesignSize(2160, 3840)
                //AndroidAutoSize 默认开启对 sp 的支持, 调用 UnitsManager.setSupportSP(false); 可以关闭对 sp 的支持
                //如果关闭对 sp 的支持, 在布局时就应该使用副单位填写字体的尺寸
                //如果开启 sp, 对其他三方库控件影响不大, 也可以不关闭对 sp 的支持, 这里我就继续开启 sp, 请自行斟酌自己的项目是否需要关闭对 sp 的支持
                .setSupportSP(false)
                //AndroidAutoSize 默认不支持副单位, 调用 UnitsManager#setSupportSubunits() 可选择一个自己心仪的副单位, 并开启对副单位的支持
                //只能在 pt、in、mm 这三个冷门单位中选择一个作为副单位, 三个单位的适配效果其实都是一样的, 您觉的哪个单位看起顺眼就用哪个
                //您选择什么单位就在 layout 文件中用什么单位进行布局, 我选择用 mm 为单位进行布局, 因为 mm 翻译为中文是妹妹的意思
                //如果大家生活中没有妹妹, 那我们就让项目中最不缺的就是妹妹!
                .setSupportSubunits(Subunits.MM);
    }



    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
