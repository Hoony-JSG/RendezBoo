package com.ssafy.a107.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SessionKeyProvider {
    private static final String sessionKeyHeader = "SKEY_";
    private static final String oneToOne = "OTO_";
    private static final String manyToMany = "MULTY_";
    public static String getSessionKey(boolean oneToOneFlag){
        return new StringBuffer()
                .append(sessionKeyHeader)
                .append(oneToOneFlag? oneToOne: manyToMany)
                .append(new SimpleDateFormat("yy-MM-dd-hh:mm:ss").format(new Timestamp(System.currentTimeMillis())))
                .toString();
    }
}
