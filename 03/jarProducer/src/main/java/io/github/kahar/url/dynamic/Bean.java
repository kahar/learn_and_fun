package io.github.kahar.url.dynamic;

import java.util.function.Supplier;

import static io.github.kahar.url.dynamic.Constant.getConstantString;

public class Bean implements Supplier<String> {

    public void sayHello() {
        System.out.println("Dynamic hello !!!");
    }

    public void sayConstantHello() {
        System.out.println(getConstantString());
    }

    public void sayHelloToUser(String user) {
        System.out.println("Hello " + user + "!");
    }

    @Override
    public String get() {
        return "Supplier get";
    }
}