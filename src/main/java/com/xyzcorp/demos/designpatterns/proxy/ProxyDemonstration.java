package com.xyzcorp.demos.designpatterns.proxy;

/**
 * @author John Ericksen
 */
public class ProxyDemonstration {

    public static void main(String[] args) {

        Target target = new TargetImpl();

        target.callMe();
        target.takeSomeTime();

        System.out.println("Added Proxy");
        Target targetProxy = new LoggingProxy(target);

        targetProxy.callMe();
        targetProxy.takeSomeTime();
    }
}
