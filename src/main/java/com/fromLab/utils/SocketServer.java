package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable  {
    private ServerSocket server;
    private Task task;
    public void setTask(Task task){
        this.task=task;
    }
    public SocketServer(){
        try {
            server = new ServerSocket(11222);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(task);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer=new SocketServer();
        Thread thread=new Thread(socketServer);
        socketServer.setTask(null);

    }
}

