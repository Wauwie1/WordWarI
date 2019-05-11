package com.wordwargroup.wordwarserver.WS;

import com.wordwargroup.wordwarserver.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Greeting message) throws Exception {
        System.out.println("Greeting WS called");
        Thread.sleep(1000); // simulated delay
        return message;
    }
}