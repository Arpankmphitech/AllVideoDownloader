# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#-keep class com.startapp.** {
#      *;
#}
#
#-keep class com.truenet.** {
#      *;
#}
#
#-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
#-dontwarn android.webkit.JavascriptInterface
#-dontwarn com.startapp.**
#
#-dontwarn org.jetbrains.annotations.**
#
#
##- InMobi
#  -keep class com.myProject.package.** { *; }
#
#-keepattributes SourceFile,LineNumberTable
#-keep class com.inmobi.** { *; }
#-dontwarn com.inmobi.**
#-keep public class com.google.android.gms.**
#-dontwarn com.google.android.gms.**
#-dontwarn com.squareup.picasso.**
#-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
#     public *;
#}
#-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
#     public *;
#}
## skip the Picasso library classes
#-keep class com.squareup.picasso.** {*;}
#-dontwarn com.squareup.picasso.**
#-dontwarn com.squareup.okhttp.**
## skip Moat classes
#-keep class com.moat.** {*;}
#-dontwarn com.moat.**
## skip AVID classes
#-keep class com.integralads.avid.library.* {*;}
#
#
## AppNext:=
#
#-keep class com.appnext.** { *; }
#-dontwarn com.appnext.**
#
#
##Facebook
#
#-keepclassmembers class * implements java.io.Serializable {
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
#-keepnames class com.facebook.FacebookActivity
#-keepnames class com.facebook.CustomTabActivity
#
#-keep class com.facebook.all.All
#
#-keep public class com.android.vending.billing.IInAppBillingService {
#    public static com.android.vending.billing.IInAppBillingService asInterface(android.os.IBinder);
#    public android.os.Bundle getSkuDetails(int, java.lang.String, java.lang.String, android.os.Bundle);
#}
#
#
#
#-keep public class com.androidads.adsdemo extends android.app.Activity
#-keep public class com.androidads.adsdemo extends android.app.MapActivity
#-keep public class com.androidads.adsdemo extends android.app.Service
#-keep public class com.androidads.adsdemo extends android.content.BroadcastReceiver
#-keep public class com.androidads.adsdemo extends android.content.ContentProvider
#-keep public class com.androidads.adsdemo extends android.app.Application
#
#-keepclassmembers class com.androidads.adsdemo.AllBannerAds.** {
#  *;
#}
#-keepclassmembers class com.androidads.adsdemo.AllInterstitialAd.** {
#  *;
#}
#-keepclassmembers class com.androidads.adsdemo.AllNativeAds.** {
#  *;
#}
#-keepclassmembers class com.androidads.adsdemo.AllRewardedAd.** {
#  *;
#}
#
#-keepclassmembers class com.androidads.adsdemo.Constants.** {
#  *;
#}
#
## Google
#
#-keep public class com.google.android.gms.ads.**{
#   public *;
#}
#
## For old ads classes
#-keep public class com.google.ads.**{
#   public *;
#}
#
## For mediation
#-keepattributes *Annotation*
#
## Other required classes for Google Play Services
## Read more at http://developer.android.com/google/play-services/setup.html
#-keep class * extends java.util.ListResourceBundle {
#   protected Object[][] getContents();
#}
#
#-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
#   public static final *** NULL;
#}
#
#-keepnames @com.google.android.gms.common.annotation.KeepName class *
#-keepclassmembernames class * {
#   @com.google.android.gms.common.annotation.KeepName *;
#}
#
#-keepnames class * implements android.os.Parcelable {
#   public static final ** CREATOR;
#}
#
#-dontwarn org.apache.commons.**
#-keep class org.apache.http.** { *; }
#-dontwarn org.apache.http.**
#-dontwarn com.ap.**
#-keep class com.ap.** { *; }
#
#-keep class [mypackagename].model.** { *; }
#-keep class [mypackagename].datamodel.** { *; }
#
#-keepclassmembers,allowobfuscation class * {
#  @com.google.gson.annotations.SerializedName <fields>;
#}
#-keep,allowobfuscation @interface com.google.gson.annotations.SerializedName

#-keepclassmembers,allowobfuscation class * {
#  @com.google.gson.annotations.SerializedName <fields>;
#}
#-keep,allowobfuscation @interface com.google.gson.annotations.SerializedName
#
#-dontwarn org.apache.commons.**
#-keep class org.apache.http.** { *; }
#-dontwarn org.apache.http.**
#-dontwarn com.ap.**
#-keep class com.ap.** { *; }
#
#-keepnames @com.google.android.gms.common.annotation.KeepName class *
#-keepclassmembernames class * {
#   @com.google.android.gms.common.annotation.KeepName *;
#}
#-keepnames class * implements android.os.Parcelable {
#   public static final ** CREATOR;
#}
#
#-keep class * extends java.util.ListResourceBundle {
#   protected Object[][] getContents();
#}
#-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
#   public static final *** NULL;
#}
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.activities.* {
#  public *** get*();
#  public void set*(***);
#}
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.activities.MainActivity.* {
#  public *** get*();
#  public void set*(***);
#}
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.adapters.* {
#  public *** get*();
#  public void set*(***);
#}
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.common.* {
#  public *** get*();
#  public void set*(***);
#}
#
##-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.firebase.* {
##  public *** get*();
##  public void set*(***);
##}
#
#-keep class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.model.** { *; }
#
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.model.* {
#  public *** get*();
#  public void set*(***);
#}
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.retrofitApi.* {
#  public *** get*();
#  public void set*(***);
#}
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.view.* {
#  public *** get*();
#  public void set*(***);
#}
#
#-keep public class com.dreamteam11.live.cricket.match.score.tips.predictions.dream.app.download.original.D11Application.* {
#  public *** get*();
#  public void set*(***);
#}
#
#-keep class com.google.** { *; }
#-keep class com.github.** { *; }
#-keep class org.apache.** { *; }
#-keep class com.android.** { *; }
#-keep class junit.** { *; }
#-keep class android.support.v7.widget.SearchView { *; }
#
#-keep interface android.support.** { *; }
#-keep class android.support.** { *; }
#
#-repackageclasses 'odbafrusshcaskantierdli'
#-printmapping out.map
#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable


-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**


#- InMobi
  -keep class com.myProject.package.** { *; }

-keepattributes SourceFile,LineNumberTable
-keep class com.inmobi.** { *; }
-dontwarn com.inmobi.**
-keep public class com.google.android.gms.**
-dontwarn com.google.android.gms.**
-dontwarn com.squareup.picasso.**
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
     public *;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
     public *;
}
# skip the Picasso library classes
-keep class com.squareup.picasso.** {*;}
-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**
# skip Moat classes
-keep class com.moat.** {*;}
-dontwarn com.moat.**
# skip AVID classes
-keep class com.integralads.avid.library.* {*;}


# AppNext:=

-keep class com.appnext.** { *; }
-dontwarn com.appnext.**


#Facebook

-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepnames class com.facebook.FacebookActivity
-keepnames class com.facebook.CustomTabActivity

-keep class com.facebook.all.All

-keep public class com.android.vending.billing.IInAppBillingService {
    public static com.android.vending.billing.IInAppBillingService asInterface(android.os.IBinder);
    public android.os.Bundle getSkuDetails(int, java.lang.String, java.lang.String, android.os.Bundle);
}



-keep public class com.androidads.adsdemo extends android.app.Activity
-keep public class com.androidads.adsdemo extends android.app.MapActivity
-keep public class com.androidads.adsdemo extends android.app.Service
-keep public class com.androidads.adsdemo extends android.content.BroadcastReceiver
-keep public class com.androidads.adsdemo extends android.content.ContentProvider
-keep public class com.androidads.adsdemo extends android.app.Application

-keepclassmembers class com.androidads.adsdemo.AllBannerAds.** {
  *;
}
-keepclassmembers class com.androidads.adsdemo.AllInterstitialAd.** {
  *;
}
-keepclassmembers class com.androidads.adsdemo.AllNativeAds.** {
  *;
}
-keepclassmembers class com.androidads.adsdemo.AllRewardedAd.** {
  *;
}
#-keep class com.androidads.adsdemo.model.AdPriority.** {
#  *;
#}
#-keep class com.androidads.adsdemo.model.F.** {
#  *;
#}
#-keep class com.androidads.adsdemo.model.G.** {
#  *;
#}

# Google

-keep public class com.google.android.gms.ads.**{
   public *;
}

# For old ads classes
-keep public class com.google.ads.**{
   public *;
}

# For mediation
-keepattributes *Annotation*

# Other required classes for Google Play Services
# Read more at http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
   protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
   public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
   @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
   public static final ** CREATOR;
}

-dontwarn org.apache.commons.**
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn com.ap.**
-keep class com.ap.** { *; }

-keep class [mypackagename].model.** { *; }
-keep class [mypackagename].datamodel.** { *; }

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keep,allowobfuscation @interface com.google.gson.annotations.SerializedName