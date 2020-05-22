package com.fromLab.utils;

import com.fromLab.entity.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
/**
 * use this client to get task from socket server
 */

public class SocketUtil {
    HashMap<String,String> taskMap=new HashMap<>();
    public HashMap getTaskMap(){
        Task task;
        Socket socket;
        try {
            socket = new Socket("127.0.0.1",11222);
            InputStream inputStream=socket.getInputStream();
            ObjectInputStream in=new ObjectInputStream(inputStream);
            task=(Task)in.readObject();
            if(task!=null) {
                taskMap.put("taskId", String.valueOf(task.getTaskId()));
                taskMap.put("taskName", task.getTaskName());
                taskMap.put("status", task.getStatus());
                taskMap.put("projectName", task.getProjectName());
                taskMap.put("progress", task.getProgress());
                taskMap.put("taskPriority", PriorityRefelection(task.getTaskPriority()));
                taskMap.put("dueTime", task.getDueTime());
                taskMap.put("starTime", task.getStartTime());
                taskMap.put("taskType", task.getTaskType());
                taskMap.put("taskDetail", task.getTaskDetail());
            }
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return taskMap;
    }

    public String PriorityRefelection(Integer priorityNum){
          switch (priorityNum){
              case 7:
                  return "Low";
              case 8:
                  return "Normal";
              case 9:
                  return "High";
              case 10:
                  return "Immediate";

          }
          return null;
    }

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
    public static boolean isPortUsing(String host,int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress,port);
            flag = true;
            socket.close();
        } catch (IOException e) {

        }
        return flag;
    }
}
