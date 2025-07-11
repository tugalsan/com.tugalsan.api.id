package com.tugalsan.api.id.server;

import com.tugalsan.api.os.server.TS_OsPlatformUtils;
import com.tugalsan.api.os.server.TS_OsProcess;
import com.tugalsan.api.string.client.TGS_StringUtils;

public class TS_IdMachineUtils {

    @Deprecated //TODO can return null;
    public static String get() {
        if (SYNC != null) {
            return SYNC;
        }
        if (TS_OsPlatformUtils.isWindows()) {
            var str = TS_OsProcess.of("wmic csproduct get UUID").output;
            if (str == null) {
                return null;
            }
            var lst = TGS_StringUtils.jre().toList_spc(str);
            for (var item : lst) {
                item = item.trim();
                if (item.isEmpty() || item.equals("UUID")) {
                    continue;
                }
                return SYNC = item;
            }
            return null;
        }
        if (TS_OsPlatformUtils.isMac()) {
            return SYNC = TS_OsProcess.of("system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }").output;
        }
        if (TS_OsPlatformUtils.isLinux()) {
            return SYNC = TS_OsProcess.of("# cat /sys/class/dmi/id/product_uuid").output;
        }
        if (TS_OsPlatformUtils.isUnix()) {
            return SYNC = TS_OsProcess.of(new String[]{
                "/bin/sh",
                "-c",
                "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"
            }).output;
        }
        return null;
    }
    private static volatile String SYNC = null;
}
