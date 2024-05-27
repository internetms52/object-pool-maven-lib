package com.internetms52.object_pool.util;

import org.apache.maven.plugin.logging.Log;

import java.util.Arrays;

public class PluginLogger {
    private Log logger;
    private Class<?> clazz;

    public PluginLogger(Log logger, Class<?> clazz) {
        this.logger = logger;
        this.clazz = clazz;
    }

    private String buildMsg(String... msgs) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(clazz.getName());
        stringBuffer.append("|");
        Arrays.stream(msgs).forEach(msg -> {
            stringBuffer.append(msg);
        });
        return stringBuffer.toString();
    }

    public void error(String... msgs) {
        logger.error(buildMsg(msgs));
    }

    public void info(String... msgs) {
        logger.info(buildMsg(msgs));
    }

    public void debug(String... msgs) {
        logger.debug(buildMsg(msgs));
    }
}
