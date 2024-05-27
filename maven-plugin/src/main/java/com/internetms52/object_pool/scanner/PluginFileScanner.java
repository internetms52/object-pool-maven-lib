package com.internetms52.object_pool.scanner;

import java.util.List;

public interface PluginFileScanner<T> {
    public List<T> execute(String rootPath);
}
