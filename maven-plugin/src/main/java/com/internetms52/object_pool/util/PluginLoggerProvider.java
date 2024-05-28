package com.internetms52.object_pool.util;

import org.apache.maven.plugin.logging.Log;

public class PluginLoggerProvider {
    private static Log log;

    public static PluginLogger loggerInitializer(Log log, Class<?> clazz) {
        if (PluginLoggerProvider.log == null) {
            PluginLoggerProvider.log = log;
        }
        return PluginLoggerProvider.getLogger(clazz);
    }

    public static PluginLogger getLogger(Class<?> clazz) {
        return new PluginLogger(
                log, clazz
        );
    }
}
