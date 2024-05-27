package com.internetms52.object_pool.scanner;

import com.internetms52.object_pool.util.PluginLogger;
import com.internetms52.object_pool.util.PluginLoggerProvider;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.List;

public class JavaFileScanner {
    PluginLogger logger = PluginLoggerProvider.getLogger(JavaFileScanner.class);

    public void execute(List<String> compileSourceRoots) throws MojoExecutionException {
        for (String sourceRoot : compileSourceRoots) {
            File sourceRootFile = new File(sourceRoot);
            if (sourceRootFile.exists() && sourceRootFile.isDirectory()) {
                listFilesRecursively(sourceRootFile);
            }
        }
    }

    private void listFilesRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    listFilesRecursively(f);
                }
            }
        } else if (file.isFile() && file.getName().endsWith(".java")) {
            logger.info("Source file: " + file.getAbsolutePath());
        }
    }
}
