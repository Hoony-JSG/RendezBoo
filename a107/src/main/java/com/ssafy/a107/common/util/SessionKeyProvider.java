package com.ssafy.a107.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SessionKeyProvider {
    //나중에 enum으로 바꾸기
    public static String getSessionKey(String sessionType, String subType){
        return new StringBuffer()
                .append(sessionType).append('_')
                .append(subType).append('_')
                .append(new SimpleDateFormat("yyMMddhhmmss").format(new Timestamp(System.currentTimeMillis())))
                .toString();
    }
    public static void main(String[] args){
        System.out.println(getSessionKey("OPENVIDU", "MULTI"));
        System.out.println(getSessionKey("GAME", "BR31"));
    }
}
