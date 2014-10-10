package com.liang.zookeeper.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/10/10.
 * Description:()
 */
public class WatcherClient implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherClient.class);
    public static final int CLIENT_PORT = 2181;
    /*所要监控的结点*/
    public static final String PATH = "/app1";
    /*所要监控的结点的子结点列表*/
    protected static List<String> nodeList;
    protected static ZooKeeper zk;
    protected WatcherForNode watcherForNode = new WatcherForNode();

    @Override
    public void run() {
        while (true) {
            try {
                zk.exists(PATH, watcherForNode);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                nodeList = zk.getChildren(PATH, watcherForNode);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
            /*对PATH下的每个结点都设置一个watcher*/
            for (String nodeName : nodeList) {
                try {
                    zk.exists(PATH + "/" + nodeName, watcherForNode);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public WatcherClient() throws IOException {
        zk = new ZooKeeper("localhost:" + CLIENT_PORT, 21810, watcherForNode);
    }

    public static void main(String[] args) throws IOException {
        WatcherClient client = new WatcherClient();
        Thread th = new Thread(client);
        th.start();
    }
}
