package com.internetms52.object_pool.util;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NativeLogger {
    Logger logger;


    public NativeLogger(Class<?> clazz) {
        logger = Logger.getLogger(clazz.getName());
    }

    private String strBinder(String... msg) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(msg).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public void info(String... msg) {
        logger.log(Level.INFO, strBinder(msg));
    }

    public void error(Exception ex) {
        error(ex.getClass().getName(), "|", ex.getMessage());
    }

    public void error(String... msg) {
        logger.log(Level.INFO, strBinder(msg));
    }
}
