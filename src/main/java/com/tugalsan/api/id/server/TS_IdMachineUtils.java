package com.tugalsan.api.id.server;

import com.tugalsan.api.os.server.TS_OsPlatformUtils;
import com.tugalsan.api.os.server.TS_OsProcess;
import com.tugalsan.api.string.client.TGS_StringUtils;

public class TS_IdMachineUtils {

    public static String get() {
        if (TS_OsPlatformUtils.isWindows()) {
            var str = TS_OsProcess.of("wmic csproduct get UUID").output;
            var lst = TGS_StringUtils.jre().toList_spc(str);
            for (var item : lst) {
                item = item.trim();
                if (item.isEmpty() || item.equals("UUID")) {
                    continue;
                }
                return item;
            }
            return null;
        } else if (TS_OsPlatformUtils.isMac()) {
            return TS_OsProcess.of("system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }").output;
        } else if (TS_OsPlatformUtils.isLinux()) {
            return TS_OsProcess.of("# cat /sys/class/dmi/id/product_uuid").output;
        } else if (TS_OsPlatformUtils.isUnix()) {
            return TS_OsProcess.of(new String[]{
                "/bin/sh",
                "-c",
                "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"
            }).output;
        } else {
            return null;
        }
    }
}
