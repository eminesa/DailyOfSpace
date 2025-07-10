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

# ProGuard rules for app
-dontwarn com.eminesa.photoofspace.**
-keep class com.eminesa.photoofspace.base.AppName { *; }

# Hilt rules
-dontwarn javax.annotation.**
-keep class androidx.lifecycle.** { *; }
-keep class androidx.lifecycle.ViewModel { *; }
#Hilt classes
-keep class com.eminesa.photoofspace.di.UseCaseModule{ *; }
-keep class com.eminesa.photoofspace.di.ServiceModule{ *; }
-keep class com.eminesa.photoofspace.di.RepositoryModule{ *; }
-keep class com.eminesa.photoofspace.di.AppModule{ *; }
-keep class com.eminesa.photoofspace.di.NetworkModule{ *; }
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# ViewModel rules
-keep class com.eminesa.photoofspace.presenters.dailyPhoto.DailyImageFragmentViewModel{ *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# All app class rules
-keep class com.eminesa.photoofspace.adapter.IntroAdapter { *; }
-keep class com.eminesa.photoofspace.adapter.PhotoAdapter { *; }
-keep class com.eminesa.photoofspace.model.DailyImage { *; }
-keep class com.eminesa.photoofspace.model.ErrorDto { *; }
-keep class com.eminesa.photoofspace.model.ErrorDtoKt { *; }
-keep class com.eminesa.photoofspace.model.ErrorModel { *; }
-keep class com.eminesa.photoofspace.model.IntroModel { *; }
-keep class com.eminesa.photoofspace.local_storage.LocalStorageService { *; }
-keep interface com.eminesa.photoofspace.local_storage.KeyValueStore { *; }
-keep class com.eminesa.photoofspace.local_storage.SharedPreferencesKeyValueStore { *; }
-keep interface com.eminesa.photoofspace.network.ImageRepository { *; }
-keep interface com.eminesa.photoofspace.network.ApiService { *; }
-keep class com.eminesa.photoofspace.network.ImageRepositoryImpl { *; }
-keep class com.eminesa.photoofspace.presenters.main.MainActivity { *; }
-keep class com.eminesa.photoofspace.presenters.splash.SplashFragment { *; }
-keep class com.eminesa.photoofspace.presenters.dailyPhoto.DailyPhotoFragment { *; }
-keep class com.eminesa.photoofspace.presenters.dailyPhoto.DailyImageViewState { *; }
-keep class com.eminesa.photoofspace.presenters.intro.IntroFragment { *; }
-keep class com.eminesa.photoofspace.presenters.intro.SnapOnScrollListener { *; }
-keep interface com.eminesa.photoofspace.presenters.intro.OnSnapPositionChangeListener { *; }
-keep interface com.eminesa.photoofspace.use_case.GetDailyImageUseCase { *; }
-keep class com.eminesa.photoofspace.use_case.GetDailyImageUseCaseImpl { *; }

#Retrofit roles
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions

# Retrofit does reflection on runtime and uses RxJava dynamically
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp does reflection on okhttp3.internal platform and uses Kotlin dynamically
-dontwarn okhttp3.internal.platform.**
-dontwarn okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keepattributes Signature
-keepattributes Exceptions
-keepclassmembers class okhttp3.internal.platform.ConscryptPlatform { *; }
-keep class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Gson uses generic type information stored in a class file when working with fields.
-keepattributes Signature

# Prevent obfuscation of types which use Retrofit annotations since the simple name
# is used to reflectively look up the annotations.
-keep class com.yourpackage.** { *; }

#Coil roles
-dontwarn okio.**
-dontwarn androidx.**
-keepattributes Signature
-keep class coil.** { *; }
-keep class com.bumptech.glide.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keepclassmembers class * {
    @coil.annotation.*
    <methods>;
}
-keepclasseswithmembers class * {
    @coil.annotation.*
    <init>(...);
}
-keepclasseswithmembers class * {
    @coil.annotation.*
    <fields>;
}
-keepclasseswithmembers class * {
    @coil.annotation.*
    <methods>;
}


# Navigation Component rules
-keepattributes *Annotation*,InnerClasses
-keepclassmembers class ** {
    *** Companion;
}

-dontwarn androidx.lifecycle.**
-keep class androidx.lifecycle.Lifecycle$State { <fields>; }
-keep class androidx.navigation.** {
    *;
}

-dontwarn androidx.navigation.fragment.**
-dontwarn androidx.navigation.ui.**

## Keep Destination and Navigator fields and methods
#-keepclassmembers class androidx.navigation.fragment.DialogFragmentNavigator {
#    *** showFragment(Landroidx/navigation/fragment/FragmentNavigator$Destination;Landroid/os/Bundle;Landroidx/navigation/fragment/FragmentNavigator$Extras;Landroidx/navigation/fragment/FragmentNavigator$Options;)V;
#}
#
## Keep Destination fields and constructor
#-keepclassmembers class androidx.navigation.fragment.DialogFragmentNavigator$Destination {
#    <init>(Landroidx/navigation/NavGraph;)V;
#}

# Keep NavGraph class and its fields and methods
-keep class androidx.navigation.NavGraph {
    *;
}

# Keep FragmentNavigator class and its fields and methods
-keep class androidx.navigation.fragment.FragmentNavigator {
    *;
}

# Keep NavHostFragment class and its fields and methods
-keep class androidx.navigation.fragment.NavHostFragment {
    *;
}

# Keep NavigationUI class and its fields and methods
-keep class androidx.navigation.ui.NavigationUI {
    *;
}

# Chucker rules
-keep class com.readystatesoftware.chucker.** { *; }
-keepclassmembers class com.readystatesoftware.chucker.** {
    *;
}

# Keep all public and protected methods that could be used in native stack traces.
-keepclassmembers class * {
    public protected *();
}

# Keep all classes that could be used in native stack traces.
-keep public class * extends java.lang.Exception
-keep public class * extends java.lang.Error


# Keep all classes in LeakCanary.
-keep class com.squareup.leakcanary.** {*;}

# Don't warn about missing support annotations.
-dontwarn android.support.**
-dontwarn androidx.**

# Don't warn about mismatched Kotlin versions (if applicable)
-dontwarn kotlin.Unit

# Retrofit uses reflection.
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature

# OkHttp uses reflection.
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keepattributes Signature

# Gson uses reflection.
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keepattributes Signature

# LeakCanary uses reflection.
-dontwarn org.jetbrains.kotlin.**
-keep class org.jetbrains.kotlin.** { *; }
-keepattributes Signature

# Coroutines Rules
-dontwarn kotlinx.coroutines.**
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
    public <methods>;
}