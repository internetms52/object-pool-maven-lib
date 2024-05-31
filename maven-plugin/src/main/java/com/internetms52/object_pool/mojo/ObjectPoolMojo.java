package com.internetms52.object_pool.mojo;

import com.internetms52.object_pool.filter.JavaFilePropertyFilter;
import com.internetms52.object_pool.filter.PluginFileFilter;
import com.internetms52.object_pool.scanner.JavaFileScanner;
import com.internetms52.object_pool.util.PluginLogger;
import com.internetms52.object_pool.util.PluginLoggerProvider;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "object-pool", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class ObjectPoolMojo extends AbstractMojo {

    PluginLogger pluginLogger = PluginLoggerProvider.loggerInitializer(getLog(), this.getClass());

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            String sourceDirectory = project.getBuild().getSourceDirectory();
            pluginLogger.info("Source Directory:" + sourceDirectory);
            List<PluginFileFilter<File>> fileFilters = new ArrayList<>();
            fileFilters.add(new JavaFilePropertyFilter());
            JavaFileScanner javaFileScanner = new JavaFileScanner(fileFilters);
            List<File> resultList = javaFileScanner.execute(sourceDirectory);
            resultList.forEach(file -> {
               pluginLogger.info(file.getAbsolutePath());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            pluginLogger.error(ex.getMessage());
        }
    }
}
