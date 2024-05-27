package com.internetms52.object_pool.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "lombok-auto-testing", defaultPhase = LifecyclePhase.TEST, requiresDependencyResolution = ResolutionScope.TEST)
public class ObjectPoolMojo extends AbstractMojo {
    public final Log log = getLog();
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            String sourceDirectory = project.getBuild().getSourceDirectory();
            log.info("Source Directory:" + sourceDirectory);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }

    private String sourceDirectoryToPackage() {
        String groupId = project.getGroupId();
        getLog().info("Root Package Path: " + groupId);
        return groupId;
    }
}
