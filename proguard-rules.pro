# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/andy/Documents/tools/sdk_for_androidStudio/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-dontwarn
-optimizationpasses 5                   # 指定代码的压缩级别
-dontusemixedcaseclassnames            # 是否使用大小写混合
-dontskipnonpubliclibraryclasses      # 指定不去忽略非公共的库类
-dontpreverify                           # 混淆时是否做预校验
-verbose                                  # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepattributes *Annotation* #保护代码中的Annotation不被混淆
-keepattributes Signature #避免混淆泛型

#生成映射文件
-verbose
-printmapping proguardMapping.txt
#抛出出异常的行号
-keepattributes SourceFile,LineNumberTable



-keep class android.support.** { *; }
-keep public class * extends android.support.**
-keep class android.view.** { *; }
-keep public class * extends android.view.**
-keep class android.Manifest.** { *; }


-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
-keepnames class * implements java.io.Serializable
-keep public class * implements java.io.Serializable {
    public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#回调方法不混淆
-keepclassmembers class ** {
    public void onEvent*(**);
    public void onEvent*(**);
    void *(**On*Event);
}

# ProGuard configurations for NetworkBench Lens
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keepattributes Exceptions, Signature, InnerClasses
-keepattributes SourceFile,LineNumberTable
# End NetworkBench Lens

-dontwarn com.tencent.**
-keep class com.tencent.** { *;}
-keep class com.sina.** { *;}
-keep class cn.aberic.avast.** {*;}

# 微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}

#环信客服
-keep class cn.hx.** {*;}
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.superrtc.** {*;}

#gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.* { *; }
-keep class com.google.gson.examples.android.model.* { *; }
-keep class com.google.gson.* { *;}

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#### -- Picasso --
-dontwarn com.squareup.picasso.**
#### -- OkHttp --
-dontwarn com.squareup.okhttp.internal.**
#### -- Apache Commons --
-dontwarn org.apache.commons.logging.**
-ignorewarnings
-keep class * {
public private *;
}

#okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

#okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**


#permissiongen.jar
-dontwarn kr.co.namee.permissiongen.**
-keep class kr.co.namee.**{*;}
-keep public class * extends android.support.v4.app.Fragment {
    private void permission*();
}

#表情键盘
#-keep class sj.keyboard.**{ *;}
#-dontwarn sj.keyboard.**
#表情键盘 只需要保持一个文件
-keep class sj.keyboard.adapter.EmoticonsAdapter{ *;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class cn.tk.service.R$*{
public static final int *;
}

-keep public class me.iwf.photopicker.**{*;}
-keep public class com.github.angads25.**{*;}


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

# 极光推送
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

# talkingdata统计
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**

#通过混淆来禁用log 需要proguard-android-optimize 禁用-dontoptimize
#-assumenosideeffects class android.util.Log { public static *** v(...); public static *** i(...); public static *** d(...);public static *** w(...);public static *** e(...);}

