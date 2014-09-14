package com.liang.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.omg.SendingContext.RunTime;

import java.io.*;

/**
 * @author Briliang
 * @date 2014/9/12
 * Description(zookeeper client in response to keep client connection)
 */
public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
    private final String[] exec;
    private final String fileName;
    private final ZooKeeper zk;
    private final DataMonitor dm;
    private Process child;
    public static void main(String[] args) {

    }

    public Executor(String hostAndPort, String znode, String fileName, String[] exec) throws IOException {
        this.exec = exec;
        this.fileName = fileName;
        zk = new ZooKeeper(hostAndPort, 3000, this);
        dm = new DataMonitor(zk, znode, null, this);
    }

    @Override
    public void process(WatchedEvent event) {
        dm.process(event);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exists(byte[] data) {
        if(data==null){
            if(child!=null){
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else {
            if(child!=null){
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream outputStream=new FileOutputStream(fileName);
                outputStream.write(data);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child= Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(),System.out);
                new StreamWriter(child.getErrorStream(),System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closing(int rc) {
        synchronized (this){
            notifyAll();
        }
    }
    class StreamWriter extends Thread{
        OutputStream os;
        InputStream in;
        public StreamWriter(InputStream in,OutputStream os){
            this.in=in;
            this.os=os;
            start();
        }
        @Override
        public void run() {
            byte[] b=new byte[80];
            int rc;
            try {
                while((rc=in.read(b))>0){
                    os.write(b,0,rc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
