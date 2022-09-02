package io.github.kahar.SOLID.D;

public class Windows98Machine {
    /*This is wrong because requires specific implementation, and object is instantiated in constructor*/
//    private final StandardKeyboard keyboard;
//    private final Monitor monitor;
//
//    public Windows98Machine() {
//        monitor = new Monitor();
//        keyboard = new StandardKeyboard();
//    }

    private final Keyboard keyboard;
    private final Monitor monitor;

    public Windows98Machine(Keyboard keyboard, Monitor monitor) {
        this.keyboard = keyboard;
        this.monitor = monitor;
    }

}
