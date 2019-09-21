package com.xyzcorp;

public class MySingleton {
    private static MySingleton instance = new MySingleton();
    private MySingleton() { }
    public static MySingleton getInstance() {
        return instance;
    }
}