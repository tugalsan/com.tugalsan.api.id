package com.tugalsan.api.id.server;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.tugalsan.api.stream.client.*;

public class TS_IdHarddisl {

    public static List<String> get() {
        //TODO LINUX hdparm -i /dev/hda 
        return TGS_StreamUtils.toList(
                TGS_StreamUtils.of(FileSystems.getDefault().getFileStores())
                        .map(item -> {
                            try {
                                return String.valueOf(item.getAttribute("volume:vsn"));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                return "N/A";
                            }
                        })
        );
    }
}
