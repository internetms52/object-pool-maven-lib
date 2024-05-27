package com.internetms52.object_pool.filter;

import java.io.File;

public class JavaFilePropertyFilter implements PluginFileFilter<File> {

    @Override
    public boolean filter(File file) {
        return file.isFile() && file.getName().endsWith(".java");
    }
}
