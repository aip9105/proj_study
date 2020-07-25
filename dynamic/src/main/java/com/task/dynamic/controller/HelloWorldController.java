package com.task.dynamic.controller;

import com.task.dynamic.service.master.HelloWorldSerivce;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HelloWorldController.NAME_SPACE)
public class HelloWorldController {

    static final String NAME_SPACE = "hello";

    private static final String PRINT_HELLO_WORLD = "openPrintHelloWorld";

    private final HelloWorldSerivce helloWorldSerivce;

    public HelloWorldController(HelloWorldSerivce helloWorldSerivce) {
        this.helloWorldSerivce = helloWorldSerivce;
    }


    /**
     * 开启打印
     * @param
     * @return
     */
    @PostMapping(PRINT_HELLO_WORLD)
    public String openPrintHelloWorld(){
        helloWorldSerivce.openPrintHelloWorld();
        return "开启打印 -- Hello World";
    }

}
