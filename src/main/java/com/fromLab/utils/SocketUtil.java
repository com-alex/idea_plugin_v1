package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketUtil {
    public Task getTask(){
        Task task=null;
        Socket socket= null;
        try {
            socket = new Socket("127.0.0.1",11222);
            InputStream inputStream=socket.getInputStream();
            ObjectInputStream in=new ObjectInputStream(inputStream);
            task=(Task)in.readObject();
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return task;
    }
}
