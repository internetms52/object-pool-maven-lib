package com.internetms52.object_pool.filter;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class AnnotationFilter implements PluginFileFilter<CompilationUnit> {
    String annotationName;

    public AnnotationFilter(String annotationName) {
        this.annotationName = annotationName;
    }

    @Override
    public boolean filter(CompilationUnit file) {
        return file.findAll(ClassOrInterfaceDeclaration.class).stream().anyMatch(classDeclaration -> {
            return classDeclaration.isAnnotationPresent(annotationName);
        });
    }
}
