package com.example.demo.core.server;

import com.example.demo.core.codec.RpcRequestBody;
import com.example.demo.core.codec.RpcResponseBody;
import com.example.demo.core.rpc_protocol.RpcRequest;
import com.example.demo.core.rpc_protocol.RpcResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

public class RpcServerWorker implements Runnable{

    private Socket socket;
    private HashMap<String,Object> registeredService;

    public RpcServerWorker(Socket socket, HashMap<String, Object> registeredService) {
        this.socket = socket;
        this.registeredService = registeredService;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //1 Transfer层获取到RpcRequest消息【transfer层】
            RpcRequest request = (RpcRequest) ois.readObject();

            //2 解析版本号 并判断【protocol层】
            if (request.getHeader().equals("version=1")) {
                //3 将rpcRequest中的body部分解码出来变成RpcRequestBody【codec层】
                byte[] body = request.getBody();
                ByteArrayInputStream bis = new ByteArrayInputStream(body);
                ObjectInputStream objectIS = new ObjectInputStream(bis);
                RpcRequestBody rpcRequestBody = (RpcRequestBody) objectIS.readObject();

                //调用服务
                Object service = registeredService.get(rpcRequestBody.getInterfaceName());
                Method method = service.getClass().getMethod(rpcRequestBody.getMethodName(), rpcRequestBody.getParamTypes());
                Object returnObject = method.invoke(service, rpcRequestBody.getParameters());

                //1 将returnObject编码成byte[]即变成了返回编码【codec】层
                RpcResponseBody rpcResponseBody = RpcResponseBody.builder()
                        .retObject(returnObject)
                        .build();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream objectOS = new ObjectOutputStream(bos);
                objectOS.writeObject(rpcResponseBody);
                byte[] bytes = bos.toByteArray();

                //2 将返回编码作为body，加上header，生成RpcResponse协议【protocol层】
                RpcResponse rpcResponse = RpcResponse.builder()
                        .header("version=1")
                        .body(bytes)
                        .build();
                //3 发送
                oos.writeObject(rpcResponse);
                oos.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
