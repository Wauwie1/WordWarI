package com.wordwargroup.wordwarserver.REST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {
    private String type;
    private Value value;

    public Greeting() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
