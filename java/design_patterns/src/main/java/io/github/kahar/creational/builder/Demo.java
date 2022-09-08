package io.github.kahar.creational.builder;

import io.github.kahar.creational.builder.director.Director;

public class Demo {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("New car created:" + Director.getUniqueCar());
        }
    }
}
