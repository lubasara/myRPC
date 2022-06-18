package com.example.demo.core.client;

import com.example.demo.core.rpc_protocol.RpcRequest;
import com.example.demo.core.rpc_protocol.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RpcClientTransfer {

    public RpcResponse sendRequest(RpcRequest request) {
        try {
            Socket socket = new Socket("localhost", 9000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(request);
            oos.flush();

            RpcResponse rpcResponse = (RpcResponse) ois.readObject();
            return rpcResponse;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
