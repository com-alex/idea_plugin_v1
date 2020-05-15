package com.fromLab.test;

import com.fromLab.entity.Task;
import com.fromLab.utils.SocketServer;
import com.fromLab.utils.SocketUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wsh
 * @date 2020-05-15
 */
public class SocketTest {

    private SocketServer socketServer;
    private Thread thread;

    @Before
    public void init(){
        this.socketServer = new SocketServer();
        this.socketServer.start();
        this.thread = new Thread(socketServer);
        this.thread.start();
    }

    @Test
    public void socketTest(){
        SocketUtil socketUtil = new SocketUtil();
        Task task = new Task();
        task.setTaskId(13);
        this.socketServer.task = task;
        System.out.println(socketUtil.getTask());
    }
}
