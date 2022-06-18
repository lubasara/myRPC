package com.example.demo.server;

import com.example.demo.IDL.Hello.HelloRequest;
import com.example.demo.IDL.Hello.HelloResponse;
import com.example.demo.IDL.Hello.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public HelloResponse hello(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hello: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }

    @Override
    public HelloResponse hi(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hi: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }
}
