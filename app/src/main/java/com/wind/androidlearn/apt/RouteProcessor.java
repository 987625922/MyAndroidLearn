package com.wind.androidlearn.apt;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Author: LL
 * @Description: APT的实现，需要导入java lib的module，否则找不到
 * @Date:Create：in 2020/12/31 16:59
 */
public class RouteProcessor
//        extends AbstractProcessor
{

//    /** 文件相关的辅助类 用于生成新的源文件、class等 */
//    private Filer mFiler;
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnv) {
//        super.init(processingEnv);
//        mFiler = processingEnv.getFiler();
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//
//        // 构建方法 此处使用到了square公司的javapoet库，用来辅助生成 类的代码
//        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("show")
//                .addModifiers(Modifier.PUBLIC);
//        methodBuilder.addStatement("String test = \"$N\" ","hello annotation world!");
//
//        /** 构建类 */
//        TypeSpec finderClass = TypeSpec.classBuilder("Hello$$Inject")
//                .addModifiers(Modifier.PUBLIC)
//                .addMethod(methodBuilder.build())
//                .build();
//        try {
//            JavaFile.builder("com.win.test",finderClass).build().writeTo(mFiler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    // 支持的注解类型
//    @Override
//    public Set<String> getSupportedAnnotationTypes() {
//        Set<String> types = new LinkedHashSet<>();
//        types.add(Hello.class.getCanonicalName());
//        return types;
//    }
//
//    @Override
//    public SourceVersion getSupportedSourceVersion() {
//        return super.getSupportedSourceVersion();
//    }
}
