# Android proguard rules
-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }
-keep class android.support.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }

# Kotlin
-keep class kotlin.Metadata { *; }
-keep class kotlin.** { *; }

# ViewModel
-keep class androidx.lifecycle.** { *; }

# Keep all Activities, Services, BroadcastReceivers
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
