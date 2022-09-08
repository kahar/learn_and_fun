package io.github.kahar.SOLID.L;

/*By throwing a car without an engine into the mix, we are inherently changing the behavior of our program.
This is a blatant violation of Liskov substitution and is a bit harder to fix than our previous two principles.*/
public class ElectricCar implements Car {

    public void turnOnEngine() {
        throw new AssertionError("I don't have an engine!");
    }

    public void accelerate() {
        //this acceleration is crazy!
    }
}
