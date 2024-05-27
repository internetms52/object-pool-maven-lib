package com.internetms52.object_pool.filter;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationFilter implements PluginFileFilter<CompilationUnit> {
    @Override
    public boolean filter(CompilationUnit file) {
        // 取得所有類別宣告
        List<ClassOrInterfaceDeclaration> classDeclarations = new ArrayList<>();
        List<TypeDeclaration<?>> typeDeclarations = file.getTypes();
        for (TypeDeclaration<?> typeDeclaration : typeDeclarations) {
            if (typeDeclaration.isClassOrInterfaceDeclaration()) {
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
                if (classOrInterfaceDeclaration.isClassOrInterfaceDeclaration()
                        && !classOrInterfaceDeclaration.isInterface()) {
                    classDeclarations.add(classOrInterfaceDeclaration);
                }
            }
        }
        return !classDeclarations.isEmpty();
    }
}
