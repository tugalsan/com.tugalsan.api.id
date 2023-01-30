package com.tugalsan.api.id.server;

import java.nio.file.*;
import java.util.*;
import com.tugalsan.api.stream.client.*;
import com.tugalsan.api.unsafe.client.*;

public class TS_IdHarddisl {

    public static List<String> get() {
        //TODO get: add LINUX implementaion: hdparm -i /dev/hda 
        return TGS_StreamUtils.toLst(
                TGS_StreamUtils.of(FileSystems.getDefault().getFileStores())
                        .map(item -> TGS_UnSafe.compile(() -> {
                    return String.valueOf(item.getAttribute("volume:vsn"));
                }, e -> {
                    e.printStackTrace();
                    return "N/A";
                }))
        );
    }
}
