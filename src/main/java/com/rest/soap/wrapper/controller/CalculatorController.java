package com.rest.soap.wrapper.controller;

import com.rest.soap.wrapper.calc.Add;
import com.rest.soap.wrapper.calc.Divide;
import com.rest.soap.wrapper.calc.Multiply;
import com.rest.soap.wrapper.calc.Subtract;
import com.rest.soap.wrapper.calcimpl.CalculatorServiceInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CalculatorController {

    final
    CalculatorServiceInterface calculatorService;



    public CalculatorController(@Qualifier("calc2") CalculatorServiceInterface calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/add")
    public int add(@RequestBody Add add){
        return calculatorService.add(add);
    }

    @PostMapping("/subtract")
    public int subtract(@RequestBody Subtract subtract){
        return calculatorService.subtract(subtract);
    }

    @PostMapping("/multiply")
    public int multiply(@RequestBody Multiply multiply){
        return calculatorService.multiply(multiply);
    }

    @PostMapping("/divide")
    public int divide(@RequestBody Divide divide){
        return calculatorService.divide(divide);
    }
}
