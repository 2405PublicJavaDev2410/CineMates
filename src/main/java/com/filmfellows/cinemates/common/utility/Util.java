package com.filmfellows.cinemates.common.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String fileRename(String originalFileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date  = sdf.format(new Date(System.currentTimeMillis()));
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileRename = date + ext;
        return fileRename;
    }
}
