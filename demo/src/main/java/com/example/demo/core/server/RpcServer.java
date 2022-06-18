package com.example.demo.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

public class RpcServer {

    private final ExecutorService threadPool;

    private final HashMap<String,Object> registeredService;

    public RpcServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        ArrayBlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,TimeUnit.SECONDS,workingQueue,threadFactory);
        this.registeredService = new HashMap<>();
    }

    public void register(Object service) {
        registeredService.put(service.getClass().getInterfaces()[0].getName(), service);
    }

    public void serve(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("server starting...");
            Socket handleSocket;
            while ((handleSocket = serverSocket.accept()) != null) {
                System.out.println("client connected, ip=" + handleSocket.getInetAddress());
                threadPool.execute(new RpcServerWorker(handleSocket,registeredService));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
