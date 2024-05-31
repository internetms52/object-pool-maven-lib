package com.internetms52.object_pool.scanner;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.internetms52.object_pool.util.PluginLogger;
import com.internetms52.object_pool.util.PluginLoggerProvider;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;


public class ClassAnnotationScanner {
    private static PluginLogger pluginLogger = PluginLoggerProvider.getLogger(ClassAnnotationScanner.class);

    String annotationName;

    public ClassAnnotationScanner(String annotationName) {
        this.annotationName = annotationName;
    }

    public Optional<ClassAnnotationScanResponse> execute(File javaFile) {
        Optional<ClassAnnotationScanResponse> resultOpt = Optional.empty();
        try (FileInputStream fileInputStream = new FileInputStream(javaFile)) {
            CompilationUnit cu = StaticJavaParser.parse(fileInputStream);
            // 遍歷AST，查找特定annotation
            boolean haveAnnotation =
                    cu.findAll(ClassOrInterfaceDeclaration.class).stream().anyMatch(classDeclaration -> {
                        return classDeclaration.isAnnotationPresent(annotationName);
                    });
            if (haveAnnotation) {
                resultOpt = Optional.of(
                        new ClassAnnotationScanResponse(
                                javaFile.getPath()
                        )
                );
            }
        } catch (Exception ex) {
            pluginLogger.error(this.getClass().getName(), ";", ex.getMessage());
        }
        return resultOpt;
    }
}
