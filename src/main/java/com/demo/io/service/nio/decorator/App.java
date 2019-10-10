package com.demo.io.service.nio.decorator;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class App {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConCreteComponent()));
        component.doSomething();
    }
}
