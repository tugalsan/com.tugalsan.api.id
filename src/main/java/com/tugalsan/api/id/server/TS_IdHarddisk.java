package com.tugalsan.api.id.server;

import java.nio.file.*;
import java.util.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.io.IOException;

public class TS_IdHarddisk {

    public static List<TGS_UnionExcuse<String>> get() {
        //TODO get: add LINUX implementaion: hdparm -i /dev/hda 
        List<TGS_UnionExcuse<String>> all = new ArrayList();
        for (var fs : FileSystems.getDefault().getFileStores()) {
            try {
                all.add(TGS_UnionExcuse.of(String.valueOf(fs.getAttribute("volume:vsn"))));
            } catch (IOException ex) {
                all.add(TGS_UnionExcuse.ofExcuse(ex));
            }
        }
        return all;
    }
}
