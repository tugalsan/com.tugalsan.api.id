package com.tugalsan.api.id.server;

import java.nio.file.*;
import java.util.*;
import com.tugalsan.api.stream.client.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.unsafe.client.*;

public class TS_IdHarddisk {

    public static List<TGS_UnionExcuse<String>> get() {
        //TODO get: add LINUX implementaion: hdparm -i /dev/hda 
        return TGS_StreamUtils.toLst(
                TGS_StreamUtils.of(FileSystems.getDefault().getFileStores())
                        .map(item -> TGS_UnSafe.call(() -> {
                    return TGS_UnionExcuse.of(String.valueOf(item.getAttribute("volume:vsn")));
                }, e -> {
                    return TGS_UnionExcuse.ofExcuse(e);
                }))
        );
    }
}
