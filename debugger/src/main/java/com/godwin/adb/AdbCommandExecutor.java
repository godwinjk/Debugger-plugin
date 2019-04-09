package com.godwin.adb;


import com.godwin.common.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.android.sdk.AndroidSdkUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Godwin on 4/26/2018 3:04 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class AdbCommandExecutor {

    private final String TAG = getClass().getSimpleName();

    public String adbPortForward(Project project, int machinePort, int devicePort) {
        StringBuilder builder = new StringBuilder();
        builder.append("forward")
                .append(" ")
                .append("tcp:")
                .append(devicePort)
                .append("tcp:")
                .append(machinePort);

        return executeCommandUsingRuntime(project,builder.toString());
    }

    public String adbPortReverse(Project project, int machinePort, int devicePort) {
        StringBuilder builder = new StringBuilder();
        builder.append("reverse")
                .append(" ")
                .append("tcp:")
                .append(devicePort)
                .append(" ")
                .append("tcp:")
                .append(machinePort);

        return executeCommandUsingRuntime(project,builder.toString());
    }

    public String adbDevices(Project project) {
        StringBuilder builder = new StringBuilder();
        builder.append("devices");

        return executeCommandUsingRuntime(project,builder.toString());
    }

    public String adbDevicesWithAllValue(Project project) {
        StringBuilder builder = new StringBuilder();
        builder.append("devices -l");

        return executeCommandUsingRuntime(project,builder.toString());
    }

    private String executeCommandUsingRuntime(Project project,String subCommand) {
        Logger.d("SubCommand : " + subCommand);

        StringBuilder builder = new StringBuilder();
        builder.append(getAdbPath(project) +" ");
//        builder.append("adb");
        builder.append(" ");
        builder.append(subCommand);

        Logger.d("Exec Command : " + builder.toString());

        StringBuilder opBuilder = new StringBuilder();
        Runtime run = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = run.exec(builder.toString());
            pr.waitFor();

            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "null";
            while ((line = buf.readLine()) != null) {
                opBuilder.append(line);
                opBuilder.append("\n");
                Logger.d(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.d("Output : " + opBuilder.toString());
        return opBuilder.toString();
    }

    private String executeCommandUsingProcessBuilder(Project project, String subCommand) {
        Logger.d("SubCommand : " + subCommand);
        StringBuilder builder = new StringBuilder();
        builder.append(getAdbPath(project)+" ");
//        builder.append("adb");
        builder.append(" ");
        builder.append(subCommand);

        Logger.d("Exec Command : " + builder.toString());

        StringBuilder opBuilder = new StringBuilder();
        String[] splitCommands = builder.toString().split(" ");
        ProcessBuilder pb = new ProcessBuilder(splitCommands);
        Process pr = null;
        try {
            pr = pb.start();
            pr.waitFor();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "null";
            while ((line = buf.readLine()) != null) {
                opBuilder.append(line);
                Logger.d(line);
            }
            Logger.d("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Logger.d("Output : " + opBuilder.toString());
        return opBuilder.toString();
    }

    private String getAdbPath(Project project) {
        File adbpath = AndroidSdkUtils.getAdb(project);
        if (adbpath != null)
            return adbpath.getAbsolutePath();
        return "";
    }
}
