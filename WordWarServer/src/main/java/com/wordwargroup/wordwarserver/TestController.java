package com.wordwargroup.wordwarserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Value value = new Value();
        value.setId((long)1);
        value.setQuote("Testje");
        Greeting response = new Greeting();
        response.setType("ok");
        response.setValue(value);

        return response;
    }
}
