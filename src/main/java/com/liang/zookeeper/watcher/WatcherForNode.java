package com.liang.zookeeper.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/10/10.
 * Description:(watcher for node type change)
 */
public class WatcherForNode implements Watcher {
    private static final Logger log= LoggerFactory.getLogger(WatcherForNode.class);
    @Override
    public void process(WatchedEvent event) {
        /*结点变化之前的结点列表*/
        List<String> nodeListBefore=WatcherClient.nodeList;
        /*主结点的数据变化时*/
        if(event.getType()== Event.EventType.NodeDataChanged){
            log.info("Node data changed:" + event.getPath());
            System.out.println("Node data changed:" + event.getPath());
        }
        if (event.getType() == Event.EventType.NodeDeleted){
            log.info("Node deleted:" + event.getPath());
            System.out.println("Node deleted:" + event.getPath());
        }
        if(event.getType()== Event.EventType.NodeCreated){
            log.info("Node created:"+event.getPath());
            System.out.println("Node created:"+event.getPath());
        }
        /*获取更新后的NodeList*/
        List<String> nodeListNow = null;
        try {
            nodeListNow=WatcherClient.zk.getChildren(event.getPath(),false);
        } catch (KeeperException e) {
            log.error("zookeeper has an error for",e);
        } catch (InterruptedException e) {
            log.error("zookeeper interrupted",e);
        }
        /*增加新节点输出新节点的日志*/
        if(nodeListBefore.size()<nodeListNow.size()){
            nodeListNow.stream().filter(str -> !nodeListBefore.contains(str)).forEach(str -> {
                log.info("Node created:" + event.getPath() + "/" + str);
            });
        }
    }
}
