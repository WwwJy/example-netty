package com.demo.io.service.thrift;

import com.demo.io.service.thrift.gen.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.net.InetSocketAddress;

/**
 * Created by w景洋
 * on 2019/9/23
 */
public class ThriftService {

    /**
     * 数据压缩格式
     * TBinaryProtocol -- 二进制格式
     * TCompactProtocol -- 压缩格式
     * TJSONProtocol -- JSON格式
     * TSimpleJSONProtocol -- JSON只写协议
     * TDebugProtocol -- 可读的文本格式
     *
     * 数据传输方式
     * TSocket -- 阻塞式socket
     * TFramedTransport -- 以frame为单位进行传输，非阻塞式服务中使用
     * TFileTransport -- 以文件形式进行传输
     * TMemoryTransport -- 将内存用于I/O. java内部使用了简单的ByteArrayOutputStream
     * TZlibTransport -- 使用zlib进行压缩，与其他传输方式联合使用。当前无java实现
     *
     * 支持的服务模型
     * TSimpleServer -- 简单的单线程服务模型，常用于测试
     * TThreadPoolServer -- 多线程服务模型，使用标准的阻塞式IO
     * TNonblockingServer -- 多线程服务模型，使用非阻塞式IO(需要配合TFramedTransport数据传输方式)
     * THsHaServer -- THsHa 引入了线程池处理，其模型把读写任务放到线程池去处理。Half-aysnc是在处理IO事件上,Half-sync用于handler对于rpc同步处理
     * @param args
     * @throws TTransportException
     */
    public static void main(String[] args) throws TTransportException {
        TNonblockingServerSocket socket = new TNonblockingServerSocket(new InetSocketAddress("localhost",8090),600);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());

        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));
        // 半同步半异步的服务
        TServer server = new THsHaServer(arg);
        System.out.println("Thrift Server Started");
        server.serve();
    }
}
