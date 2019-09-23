package com.demo.io.service.thrift;

import com.demo.io.service.thrift.gen.Person;
import com.demo.io.service.thrift.gen.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by w景洋
 * on 2019/9/23
 */
public class ThriftClient {

    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost",8090),600);
        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);
        try {
            tTransport.open();
            Person p = client.getPersonByUsername("张三");
            System.out.println(p.getName());
            System.out.println(p.getAge());
            System.out.println(p.isMarried());
            System.out.println("===========================");
            Person p2 = new Person();
            p2.setName("李四");
            p2.setAge(19);
            p2.setMarried(false);
            client.savePerson(p2);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }finally {
            tTransport.close();
        }
    }
}
