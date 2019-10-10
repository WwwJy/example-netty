package com.demo.io.service.nio.decorator;

/**
 * Created by w景洋
 * on 2019/10/9
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component c) {
        super(c);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能C");
    }
}
