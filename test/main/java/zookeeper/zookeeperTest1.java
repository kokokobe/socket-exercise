package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.SimpleTimeZone;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/9/5.
 * Description:(zookeeper client test
 * client port :2181,2182,2183)
 */
public class zookeeperTest1 {
    private static Integer server1ClientPort = 2181;
    private static Integer server2ClientPort = 2182;
    private static Integer server3ClientPort = 2183;
    ZooKeeper zooKeeper;
    @Test
    public void test1() {
        // 创建一个与服务器的连接
        try {

            ZooKeeper zk = new ZooKeeper("localhost:" + server1ClientPort, 3000, event -> System.out.println("已经触发了" + event.getType() + "事件！"));
            /*创建一个目录节点*/
            String path=zk.create("/testRootPath","testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("path:"+path);
            System.out.println(new String(zk.getData("/testRootPath",false,null)));
            // 创建一个子目录节点
            zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            // 取出子目录节点列表
            System.out.println("childOne:"+zk.getChildren("/testRootPath",true));
            // 修改子目录节点数据
            zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
            System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]");
            // 创建另外一个子目录节点
            zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            System.out.println("childTwo:"+new String(zk.getData("/testRootPath/testChildPathTwo",true,null)));
            /*删除子目录节点*/
            zk.delete("/testRootPath/testChildPathOne",-1);
            zk.delete("/testRootPath/testChildPathTwo",-1);
            /*删除父节点*/
            zk.delete("/testRootPath",-1);
            zk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void zookeeperAuth(){
        try {
            zooKeeper=new ZooKeeper("localhost:" + server2ClientPort,1000000,null);
            String authType="digest";
            String auth="joey:some";
            String p="/acl_digest";
            zooKeeper.addAuthInfo(authType,auth.getBytes());
            zooKeeper.create(p, "hello".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            Stat stat=new Stat();
            System.out.println(new String(zooKeeper.getData(p,false,stat)));
            zooKeeper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
