package io.github.kahar._volatile;

public class TaskRunner {

    private static int number;
    private volatile static boolean ready;

    public static void main(String[] args) {
        new Reader().start();
        number = 42;
        ready = true;
    }

    private static class Reader extends Thread {

        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }

            System.out.println(number);
        }
    }
}