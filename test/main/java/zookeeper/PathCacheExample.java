package zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/9/9.
 * Description:(An example of the PathChildrenCache. The example "harness" is a command processor
 * that allows adding/updating/removed nodes in a path. A PathChildrenCache keeps a
 * cache of these changes and outputs when updates occurs.)
 */
public class PathCacheExample {
    private static final String PATH="/example/cache";

    public static void main(String[] args) throws Exception {
        TestingServer server=new TestingServer();
        CuratorFramework client=null;
        PathChildrenCache cache=null;
        CuratorFrameworkFactory.newClient(server.getConnectString(),new BoundedExponentialBackoffRetry(1000,1000,3));
        client.start();
        // in this example we will cache data. Notice that this is optional.
        cache=new PathChildrenCache(client,PATH,true);
        cache.start();
        processCommands(client,cache);
        CloseableUtils.closeQuietly(cache);
        CloseableUtils.closeQuietly(client);
        CloseableUtils.closeQuietly(server);
    }

    private static void processCommands(CuratorFramework client, PathChildrenCache cache) {

    }

}
