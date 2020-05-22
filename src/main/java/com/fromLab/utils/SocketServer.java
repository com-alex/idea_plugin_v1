package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * use this socket server to send task to GUI
 */
public class SocketServer implements Runnable {
    private ServerSocket server;
    public volatile Task task;
    volatile String message;
    volatile boolean flag = true;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SocketServer() {

    }

    public boolean start() {
        boolean flag = false;
        try {
            server = new ServerSocket(11222);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(task);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

