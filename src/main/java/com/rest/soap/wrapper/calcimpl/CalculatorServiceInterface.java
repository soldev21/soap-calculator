package com.rest.soap.wrapper.calcimpl;

import com.rest.soap.wrapper.calc.Add;
import com.rest.soap.wrapper.calc.Divide;
import com.rest.soap.wrapper.calc.Multiply;
import com.rest.soap.wrapper.calc.Subtract;

public interface CalculatorServiceInterface {
    int add(Add add);
    int subtract(Subtract subtract);
    int multiply(Multiply multiply);
    int divide(Divide divide);
}
