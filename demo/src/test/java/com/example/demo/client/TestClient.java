package com.example.demo.client;

import com.example.demo.IDL.Hello.HelloRequest;
import com.example.demo.IDL.Hello.HelloResponse;
import com.example.demo.IDL.Hello.HelloService;
import com.example.demo.core.client.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        //获取rpcService
        RpcClientProxy proxy = new RpcClientProxy();
        HelloService helloService = proxy.getService(HelloService.class);
        //构造出请求对象HelloRequest
        HelloRequest helloRequest = new HelloRequest("tom");
        //rpc调用并返回结果对象HelloResponse
        HelloResponse helloResponse = helloService.hello(helloRequest);
        //从HelloResponse中获取msg
        String msg = helloResponse.getMsg();
        System.out.println(msg);

        //调用hi方法
        HelloResponse hiResponse = helloService.hi(helloRequest);
        String hiMsg = hiResponse.getMsg();
        System.out.println(hiMsg);
    }
}
