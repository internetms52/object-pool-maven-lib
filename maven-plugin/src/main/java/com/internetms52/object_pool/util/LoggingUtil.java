package com.internetms52.object_pool.util;

import java.util.Arrays;

public class LoggingUtil {
    public static String buildMsg(String... msgs) {
        StringBuffer stringBuffer = new StringBuffer();
        Arrays.stream(msgs).forEach(msg -> {
            stringBuffer.append(msg);
        });
        return stringBuffer.toString();
    }
}
