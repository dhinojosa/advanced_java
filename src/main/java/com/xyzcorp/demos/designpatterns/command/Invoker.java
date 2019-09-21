package com.xyzcorp.demos.designpatterns.command;

import java.util.HashMap;
import java.util.Map;

public class Invoker {

    private Map<String, Action> commandMap = new HashMap<>();

    public void send(String command) {
        commandMap.get(command).execute();
    }
}
