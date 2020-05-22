package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * use this socket server to send task to GUI
 *
 */
public class SocketServer implements Runnable  {
    private ServerSocket server;
    public volatile Task task;
    volatile String message;
    volatile boolean flag=true;
    public void setMessage(String message){this.message=message;}
    public void setTask(Task task){
        this.task=task;
    }
    public SocketServer(){
//        try {
//            server = new ServerSocket(11222);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public boolean start(){
        boolean flag=false;
        try {
            server = new ServerSocket(11222);
            flag =true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    @Override
    public void run() {
        while (true) {
//            System.out.println(message);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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

    public static void main(String[] args) throws InterruptedException {
        SocketServer socketServer=new SocketServer();
        Thread thread=new Thread(socketServer);
     //   socketServer.setTask(null);
        socketServer.setMessage("b");
        thread.start();
        Thread.sleep(1000);
      //  socketServer.flag=false;
        socketServer.setMessage("a");
   //     socketServer.flag=true;

      //  thread.start();

    }
}

