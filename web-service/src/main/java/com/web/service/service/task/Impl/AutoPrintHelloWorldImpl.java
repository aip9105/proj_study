package com.web.service.service.task.Impl;

import com.web.service.service.task.ITaskExecuteService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class AutoPrintHelloWorldImpl implements ITaskExecuteService {


    @Override
    public void execute(String param) {
        if (Integer.parseInt(param)%2 == 0){
            System.out.println(LocalDateTime.now() +"-- HelloWorld");
        }
    }
}
