package com.internetms52.object_pool.scanner;

import com.internetms52.object_pool.filter.PluginFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaFileScanner implements PluginFileScanner<File> {

    List<PluginFileFilter<File>> fileFilters = new ArrayList<>();

    public JavaFileScanner(List<PluginFileFilter<File>> fileFilters) {
        this.fileFilters = fileFilters;
    }

    public void execute(List<String> compileSourceRoots) {
        for (String sourceRoot : compileSourceRoots) {
            File sourceRootFile = new File(sourceRoot);
            if (sourceRootFile.exists() && sourceRootFile.isDirectory()) {
                listFilesRecursively(sourceRootFile);
            }
        }
    }

    private List<File> listFilesRecursively(File file) {
        List<File> resultList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    resultList.addAll(listFilesRecursively(f));
                }
            }
        } else {
            Optional<PluginFileFilter<File>> failedFilterOpt = fileFilters.stream()
                    .filter(filter -> !filter.filter(file))
                    .findFirst();
            if (failedFilterOpt.isEmpty()) {
                resultList.add(file);
            }
        }
        return resultList;
    }

    @Override
    public List<File> execute(String rootPath) {
        File sourceRootFile = new File(rootPath);
        if (sourceRootFile.exists() && sourceRootFile.isDirectory()) {
            return listFilesRecursively(sourceRootFile);
        }
        return new ArrayList<>();
    }
}
