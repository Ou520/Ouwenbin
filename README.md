# Ouwenbin
###仿照哔哩哔哩动画安卓客户端的项目
#使用的开源框架：

    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'pub.devrel:easypermissions:0.3.0'
    implementation 'org.jetbrains:annotations-java5:15.0'
    implementation 'com.android.support:design:28.0.0'
    
    //B站媒体播放器
    implementation 'tv.danmaku.ijk.media:ijkplayer-java:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-exo:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv5:0.8.8'
    
    //图片轮播
    implementation 'com.youth.banner:banner:1.4.10'
    //noinspection GradleDependency
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    
    // ButterKnife所需要的依赖
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    
    //万能播放器的库（Vitamio）
    implementation project(':vitamio')
    
    //Android 圆角、圆形 ImageView的库
    implementation 'com.github.SheHuan:NiceImageView:1.0.5'
    
    //Xutils:JSON和图片解析框架
    implementation 'org.xutils:xutils:3.8.3'

    //Picasso图片加载框架(最新版)
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation files('libs/classes.jar')
    implementation project(':jcvideoplayer-lib')
    //ImageLoader加载网络图片框架
    implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'

    //DanmakuFlameMaster需要的库
    implementation 'com.github.ctiao:DanmakuFlameMaster:0.9.25'
    implementation 'com.github.ctiao:ndkbitmap-armv7a:0.9.21'
    implementation 'com.github.ctiao:ndkbitmap-armv5:0.9.21'
    implementation 'com.github.ctiao:ndkbitmap-x86:0.9.21'
    implementation 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'

    //SmartRefreshLayout的库
    implementation 'com.scwang.smartrefresh:SmartRefreshLayout:1.1.0'  //1.0.5及以前版本的老用户升级需谨慎，API改动过大
    implementation 'com.scwang.smartrefresh:SmartRefreshHeader:1.1.0'  //没有使用特殊Header，可以不加这行

    //图片加载框架之Glide
    //noinspection GradleDependency
    implementation 'com.github.bumptech.glide:glide:3.7.0'

    //JiaoZiVideoPlayer的库
//    implementation 'cn.jzvd:jiaozivideoplayer:7.0.5'

    implementation 'com.commit451:PhotoView:1.2.4'
### 开发平台：Android Studio 3.3 API：28
