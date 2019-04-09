# Database Debugger

Database Debugger is a debug bridge for Android applications. When enabled, developers have access to database of application within android studio. Without any support of other third party tools.

# Set-up

#### In Android Studio
1. File-> Settings-> Plugins
2. Click *Browse repositories*
3. Search **Database Debugger**
4. Install and restart Android Studio

Or you can find the file from https://plugins.jetbrains.com/plugin/10766-database-debugger

Now you will see a tool window on right tool panel called **Database Debugger**.

#### In application

1. Download the latest JARs or grab via Gradle:

`implementation 'com.github.godwinjoseph-wisilica:debugger:1.0.1'`
or Maven:

```
<dependency>
  <groupId>com.github.godwinjoseph-wisilica</groupId>
  <artifactId>debugger</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

# Using

Integration of Database Debugger is pretty straight forwart. There is a simple initialization step which occurs in your Application class:

```
public class MyApplication extends Application {
  public void onCreate() {
    super.onCreate();
    Debugger.initialize(this); 
    }
}
```

Also ensure that your MyApplication Java class is registered in your AndroidManifest.xml file

```
<manifest
        xmlns:android="http://schemas.android.com/apk/res/android"
        ...>
        <application
                android:name="MyApplication"
                ...>
         </application>
</manifest>
```
# Debugger-plugin
