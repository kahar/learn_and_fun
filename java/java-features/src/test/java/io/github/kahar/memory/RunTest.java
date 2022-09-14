package io.github.kahar.memory;

import java.util.ArrayList;

public class RunTest {

    public static void main(String[] args) {
        RunTest runTest = new RunTest();
        runTest.simpleTest();
    }

    private void simpleTest() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10000000; i++) {
            arrayList.add(i);
        }
    }
}
