package io.github.kahar.creational.builder.director;

import io.github.kahar.creational.builder.car.Car;

public class Director {
    /*Here UUID can be used - but then adding this value as suffix will extend Car name*/
    private static Long uniqueId = 0L;

    public static Car getUniqueCar() {
        Car result = Car.builder().id(uniqueId).name("Unique name " + uniqueId).build();
        uniqueId++;
        return result;
    }
}
