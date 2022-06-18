package com.example.demo.server;

import com.example.demo.core.server.RpcServer;

public class TestServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        HelloServiceImpl helloService = new HelloServiceImpl();
        rpcServer.register(helloService);
        rpcServer.serve(9000);
    }
}
