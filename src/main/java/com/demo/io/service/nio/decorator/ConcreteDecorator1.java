package com.demo.io.service.nio.decorator;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component c) {
        super(c);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能B");
    }
}
