package com.internetms52.object_pool.util;

import org.apache.maven.plugin.logging.Log;

public class PluginLoggerProvider {
    private static Log log;

    public PluginLoggerProvider(Log log) {
        this.log = log;
    }

    public static PluginLogger getLogger(Class<?> clazz) {
        return new PluginLogger(
                log, clazz
        );
    }
}
