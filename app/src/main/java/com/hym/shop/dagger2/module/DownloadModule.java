package com.hym.shop.dagger2.module;

import dagger.Module;

@Module
public class DownloadModule {

  /*  @Provides
    @Singleton
    //因为AppModule中提供的是Application所以这里要和它类型保持一致
    public RxDownload provideRxDownload(Application application, Retrofit retrofit,File downDir){
        Log.d("provideRxDownload", "provideRxDownload: "+downDir.getPath());
        ACache.get(application).put(Constant.APK_DOWNLOAD_DIR,downDir.getPath());
        return RxDownload.getInstance(application)
                .defaultSavePath(downDir.getPath())
                .retrofit(retrofit)
                .maxDownloadNumber(10)
                .maxThread(10)
                .maxRetryCount(2);//下载失败重试次数;
    }

    @Provides
    @Singleton
    public File provideDownloadDir(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }*/

}
