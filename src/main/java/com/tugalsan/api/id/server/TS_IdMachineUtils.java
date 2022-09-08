package com.tugalsan.api.id.server;

import com.tugalsan.api.os.server.TS_OSUtils;
import com.tugalsan.api.os.server.TS_Process;
import com.tugalsan.api.string.server.TS_StringUtils;

public class TS_IdMachineUtils {

    public static String get() {
        if (TS_OSUtils.isWindows()) {
            var str = TS_Process.of("wmic csproduct get UUID").output;
            var lst = TS_StringUtils.toList_spc(str);
            for (var item : lst) {
                item = item.trim();
                if (item.isEmpty() || item.equals("UUID")) {
                    continue;
                }
                return item;
            }
            return null;
        } else if (TS_OSUtils.isMac()) {
            return TS_Process.of("system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }").output;
        } else if (TS_OSUtils.isLinux()) {
            return TS_Process.of("# cat /sys/class/dmi/id/product_uuid").output;
        } else if (TS_OSUtils.isUnix()) {
            return TS_Process.of(new String[]{
                "/bin/sh",
                "-c",
                "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"
            }).output;
        } else {
            return null;
        }
    }
}
