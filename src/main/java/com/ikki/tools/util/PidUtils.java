package com.ikki.tools.util;

import java.lang.management.ManagementFactory;

public class PidUtils {

    private static int pid;

    static {
        pid = retrivalPidFromJVM();
    }

    public static int retrivalPidFromJVM(){
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(name.split("@")[0]);
    }

    public static int getPid() {
        return pid;
    }

}
