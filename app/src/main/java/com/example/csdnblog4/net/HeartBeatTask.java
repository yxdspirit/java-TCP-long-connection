package com.example.csdnblog4.net;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * 实现5秒的定时发送一个心跳
 * Created by orange on 16/6/8.
 */
public class HeartBeatTask implements Runnable {
    private static final int REPEATTIME = 4000;
    private volatile boolean isKeepAlive = true;
    private OutputStream outputStream;
    private String uuid;
    public HeartBeatTask(OutputStream outputStream,String uuid) {
        this.outputStream = outputStream;
        this.uuid=uuid;
    }


    @Override
    public void run() {
        try {
            while (isKeepAlive) {
                SocketUtil.write2Stream("heartBeat", uuid,outputStream);
                try {
                    Thread.sleep(REPEATTIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                SocketUtil.closeStream(outputStream);
            }
        } catch (IOException e) {
            Log.d(new Date().toString(), " : Time is out, request" + " has been closed.");
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                SocketUtil.closeStream(outputStream);
            }
        }
    }

    public void setKeepAlive(boolean isKeepAlive) {
        this.isKeepAlive = isKeepAlive;
    }
}