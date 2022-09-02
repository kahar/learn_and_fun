package io.github.kahar.SOLID.O.calculator;

import java.security.InvalidParameterException;

/*This is wrong, because when a new requirement of adding multiplication or divide functionality comes in,
we've no way besides changing the calculate method of the Calculator class.*/
public class Calculator {

    public void calculate(CalculatorOperation operation) {
        if (operation == null) {
            throw new InvalidParameterException("Can not perform operation");
        }

        if (operation instanceof Addition) {
            Addition addition = (Addition) operation;
            addition.setResult(addition.getLeft() + addition.getRight());
        } else if (operation instanceof Subtraction) {
            Subtraction subtraction = (Subtraction) operation;
            subtraction.setResult(subtraction.getLeft() - subtraction.getRight());
        }
    }
}