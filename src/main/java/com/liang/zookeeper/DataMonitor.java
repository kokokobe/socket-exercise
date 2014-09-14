package com.liang.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author Briliang
 * @date 2014/9/12
 * Description()
 */
public class DataMonitor implements Watcher,AsyncCallback.StatCallback {
    private ZooKeeper zk;
    private boolean dead;
    private String znode;
    private Watcher chainedWatcher;
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
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {

    }

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
