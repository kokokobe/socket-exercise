package com.liang.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

/**
 * @author Briliang
 * @date 2014/9/12
 * Description()
 */
public class DataMonitor implements Watcher,AsyncCallback.StatCallback {
    private ZooKeeper zk;
    private String znode;
    private Watcher chainedWatcher;
    boolean dead;
    DataMonitorListener listener;
    private byte[] prevData;

    public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;
        // Get things started by checking if the node exists. We are going
        // to be completely event driven
        zk.exists(znode,true,this,null);
    }

    public void process(WatchedEvent event) {
        String path=event.getPath();
        if(event.getType()==Event.EventType.None){
            // We are are being told that the state of the
            // connection has changed
            switch (event.getState()){
                case SyncConnected:
                    // In this particular example we don't need to do anything
                    // here - watches are automatically re-registered with
                    // server and any watches triggered while the client was
                    // disconnected will be delivered (in order of course)
                    break;
                case Expired:
                    /*It's all over*/
                    dead=true;
                    listener.closing(KeeperException.Code.SessionExpired);
                    break;
            }
        }else {
            if(path!=null&&path.equals(znode)){
                /*Something has changed on the node, let's find out*/
                zk.exists(znode,true,this,null);
            }
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc){
            case KeeperException.Code.Ok:exists=true;break;
            case KeeperException.Code.NoNode:exists=false;break;
            case KeeperException.Code.SessionExpired:
            case KeeperException.Code.NoAuth:dead=true;listener.closing(rc);
            default:
                /*retry error*/
                zk.exists(znode,true,this,null);
                return;
        }
        byte[] b=null;
        if(exists){
            try {
                b=zk.getData(znode,false,null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        if((b==null&&b!=prevData)||(b!=null&& Arrays.equals(prevData,b))){
            listener.equals(b);
            prevData=b;
        }
    }
    /**
     * Other classes use the DataMonitor by implementing this method
     */
    public interface DataMonitorListener {
        /**
         * The existence status of the node has changed.
         */
        public void exists(byte[] data);

        /**
         * The ZooKeeper session is no longer valid.
         * @param rc
         * the ZooKeeper reason code
         */
        public void closing(int rc);
    }
}
