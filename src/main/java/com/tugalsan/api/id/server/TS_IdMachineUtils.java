package com.tugalsan.api.id.server;

import com.tugalsan.api.os.server.TS_OSUtils;
import com.tugalsan.api.os.server.TS_RuntimeUtils;
import com.tugalsan.api.string.server.TS_StringUtils;

public class TS_IdMachineUtils {

    public static String get() {
        if (TS_OSUtils.isWindows()) {
            var str = TS_RuntimeUtils.runConsole_readResult("wmic csproduct get UUID");
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
            return TS_RuntimeUtils.runConsole_readResult("system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }");
        } else if (TS_OSUtils.isLinux()) {
            return TS_RuntimeUtils.runConsole_readResult("# cat /sys/class/dmi/id/product_uuid");
        } else if (TS_OSUtils.isUnix()) {
            return TS_RuntimeUtils.runConsole_readResult(new String[]{
                "/bin/sh",
                "-c",
                "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"
            });
        } else {
            return null;
        }
    }
}
