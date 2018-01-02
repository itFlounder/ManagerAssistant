package com.shsany.managerassistant.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.L;
import com.shsany.managerassistant.R;
import com.shsany.managerassistant.common.ActivityPageManager;
import com.shsany.managerassistant.utils.LogUtil;

/**
 * Created by PC on 2017/12/28.
 * [系统Application类，设置全局变量以及初始化组件]
 */

public class ShSanyApplication extends Application{
    private final String tag = ShSanyApplication.class.getSimpleName();
    private static DisplayImageOptions options;
    private static ShSanyApplication instance;
    public static Context myContext;
    //是否完成 整个项目
    public static boolean isCompleteProject = false;

    public static ImageLoader imageLoader;
    public static final boolean ISDEBUG = false;

    private ShSanyApplication(){}

    public static ShSanyApplication getInstance(){
        if (instance == null){
            synchronized (ShSanyApplication.class){
                if (instance == null){
                    instance = new ShSanyApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myContext = this;
        init();
    }

    //初始化
    private void init() {
        LogUtil.setDebug(!isCompleteProject);
        LogUtil.e(tag,"isDebug: "+ !isCompleteProject);
        initImageLoader();
//        CrashHandler.create(this);
//        JPushInterface.init(this); // 初始化JPush
//        JPushInterface.setDebugMode(true);  // 设置日志,发布时请关闭日志
    }

    private void initImageLoader() {
        //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
        int maxMemory = (int ) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory/8;//给LruCache分配1/8 4M

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        //初始化图片下载组件
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new LruMemoryCache(mCacheSize))
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        //关闭 打开log imgelog
        L.writeLogs(false);

    }

    /**
     * 退出应用
     */
    public void exit(){
        ActivityPageManager.getInstance().exit(this);
    }
}
