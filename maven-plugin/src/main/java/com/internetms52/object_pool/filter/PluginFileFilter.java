package com.internetms52.object_pool.filter;

public interface PluginFileFilter<T> {
    boolean filter(T file);
}
