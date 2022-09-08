package io.github.kahar;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class URLClassLoaderTest {

    public static void main(String[] args) throws Exception {
        String path = Paths.get("jarProducer\\build\\libs\\jarProducer-1.0-SNAPSHOT.jar").toAbsolutePath().toString();
        File f = new File(path);

        URL[] classLoaderUrls = new URL[]{f.toURI().toURL()};
        URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
        Class<?> beanClass = urlClassLoader.loadClass("io.github.kahar.url.dynamic.Bean");
        Constructor<?> constructor = beanClass.getConstructor();
        Object beanObj = constructor.newInstance();

        Method method = beanClass.getMethod("sayHello");
        method.invoke(beanObj);

        method = beanClass.getMethod("sayConstantHello");
        method.invoke(beanObj);

        method = beanClass.getMethod("sayHelloToUser", String.class);
        method.invoke(beanObj, "MagicUser");

        Supplier<String> supplierObj = (Supplier<String>) beanObj;
        System.out.println("Get supplier value from test:" + supplierObj.get());
    }

}