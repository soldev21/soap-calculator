package com.rest.soap.wrapper.configuration;

import com.rest.soap.wrapper.calcimpl.CalculatorService;
import com.rest.soap.wrapper.calcimpl.CalculatorService2;
//import com.rest.soap.wrapper.calcimpl.CalculatorService3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Configuration
public class CalculatorConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
//        marshaller.setMarshallerListener(new Marshaller.Listener() {
//            @Override
//            public void afterMarshal(Object source) {
//                super.afterMarshal(source);
//                System.out.println(source);
//            }
//        });
//        marshaller.setUnmarshallerListener(new Unmarshaller.Listener() {
//            @Override
//            public void afterUnmarshal(Object target, Object parent) {
//                super.afterUnmarshal(target, parent);
//                System.out.println(target);
//            }
//        });
        marshaller.setContextPath("com.rest.soap.wrapper.calc");
        return marshaller;
    }

    @Bean("calc")
    public CalculatorService countryClient(Jaxb2Marshaller marshaller) {
        CalculatorService client = new CalculatorService();
//        client.setDefaultUri("http://www.dneonline.com/calculator.asmx?op=Add");
        client.setDefaultUri("http://www.dneonline.com/calculator.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean("calc2")
    public CalculatorService2 calculatorService2(Jaxb2Marshaller marshaller) {
        CalculatorService2 client = new CalculatorService2();
//        client.setDefaultUri("http://www.dneonline.com/calculator.asmx?op=Add");
        client.setDefaultUri("http://www.dneonline.com/calculator.asmx");
        client.setMarshaller(marshaller);
        client.setUnMarshaller(marshaller);
        return client;
    }
//
//    @Bean("calc3")
//    public CalculatorService3 calculatorService3(){
//        return new CalculatorService3();
//    }

}