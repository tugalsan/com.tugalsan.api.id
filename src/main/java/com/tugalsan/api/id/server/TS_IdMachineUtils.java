package com.tugalsan.api.id.server;

import com.tugalsan.api.os.server.TS_OsPlatformUtils;
import com.tugalsan.api.os.server.TS_OsProcess;
import com.tugalsan.api.string.server.TS_StringUtils;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_IdMachineUtils {

    public static TGS_UnionExcuse<String> get() {
        if (TS_OsPlatformUtils.isWindows()) {
            var u = TS_OsProcess.of("wmic csproduct get UUID").toUnion();
            if (u.isExcuse()) {
                return u.toExcuse();
            }
            var lst = TS_StringUtils.toList_spc(u.value().output);
            for (var item : lst) {
                item = item.trim();
                if (item.isEmpty() || item.equals("UUID")) {
                    continue;
                }
                return TGS_UnionExcuse.of(item);
            }
            return null;
        } else if (TS_OsPlatformUtils.isMac()) {
            var u = TS_OsProcess.of("system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }").toUnion();
            if (u.isExcuse()) {
                return u.toExcuse();
            }
            return TGS_UnionExcuse.of(u.value().output);
        } else if (TS_OsPlatformUtils.isLinux()) {
            var u = TS_OsProcess.of("# cat /sys/class/dmi/id/product_uuid").toUnion();
            if (u.isExcuse()) {
                return u.toExcuse();
            }
            return TGS_UnionExcuse.of(u.value().output);
        } else if (TS_OsPlatformUtils.isUnix()) {
            var u = TS_OsProcess.of(new String[]{
                "/bin/sh",
                "-c",
                "echo <password for superuser> | sudo -S cat /sys/class/dmi/id/product_uuid"
            }).toUnion();
            if (u.isExcuse()) {
                return u.toExcuse();
            }
            return TGS_UnionExcuse.of(u.value().output);
        } else {
            return TGS_UnionExcuse.ofExcuse("TS_IdMachineUtils", "get", "unknown os");
        }
    }
}
