package com.godwin;

import com.godwin.command.receiver.GenericReceiver;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import com.intellij.openapi.project.Project;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.android.sdk.AndroidSdkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AdbUtil {

    public static String getAdbFilePath(Project project) {
        File adbPath = AndroidSdkUtils.getAdb(project);
        if (adbPath != null) {
            return adbPath.getAbsolutePath();
        }
        return null;
    }

    public static boolean isAppInstalled(IDevice device, String packageName) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        GenericReceiver receiver = new GenericReceiver();
        // "pm list packages com.my.package" will return one line per package installed that corresponds to this package.
        // if this list is empty, we know for sure that the app is not installed
        device.executeShellCommand("pm list packages " + packageName, receiver, 15L, TimeUnit.SECONDS);

        //TODO make sure that it is the exact package name and not a subset.
        // e.g. if our app is called com.example but there is another app called com.example.another.app, it will match and return a false positive
        return !receiver.getAdbOutputLines().isEmpty();
    }


    /**
     * Computes the project's package while preserving backward compatibility between android studio 0.4.3 and 0.4.4
     */
    public static String computePackageName(AndroidFacet facet) {
        try {
            Object androidModuleInfo = facet.getClass().getMethod("getAndroidModuleInfo").invoke(facet);
            return (String) androidModuleInfo.getClass().getMethod("getPackage").invoke(androidModuleInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
