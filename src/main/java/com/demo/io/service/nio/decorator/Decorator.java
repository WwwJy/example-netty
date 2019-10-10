package com.demo.io.service.nio.decorator;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component c){
        this.component = c;
    }
    @Override
    public void doSomething() {
        component.doSomething();
    }
}
