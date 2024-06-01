package com.internetms52.object_pool.annotation.processor;

import com.google.auto.service.AutoService;
import com.internetms52.object_pool.annotation.ObjectPool;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.internetms52.object_pool.annotation.ObjectPool")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ObjectPoolProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ObjectPool.class)) {
            if (element instanceof TypeElement) {
                TypeElement classElement = (TypeElement) element;
                String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
                String className = classElement.getSimpleName().toString();

                TypeSpec generatedClass = TypeSpec.classBuilder(className + "Generated")
                        .addModifiers(Modifier.PUBLIC)
                        .build();

                JavaFile javaFile = JavaFile.builder(packageName, generatedClass)
                        .build();
                try {
                    Path generatedSources = Paths.get("target/generated-sources");
                    javaFile.writeTo(generatedSources);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
