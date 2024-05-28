package com.internetms52.object_pool.mojo;

import com.internetms52.object_pool.annotation.ObjectPool;
import com.internetms52.object_pool.util.PluginLogger;
import com.internetms52.object_pool.util.PluginLoggerProvider;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

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

        } catch (Exception ex) {
            ex.printStackTrace();
            pluginLogger.error(ex.getMessage());
        }
    }
}
