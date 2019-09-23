package com.demo.io.service.thrift;

import com.demo.io.service.thrift.gen.DataException;
import com.demo.io.service.thrift.gen.Person;
import com.demo.io.service.thrift.gen.PersonService;
import org.apache.thrift.TException;

/**
 * Created by w景洋
 * on 2019/9/23
 */
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param : " + username);
        Person person = new Person();
        person.setName(username);
        person.setAge(20);
        person.setMarried(false);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Client Save new Person");
        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
