package zookeeper;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/9/9.
 * Description: (
 * This shows a very simplified method of registering an instance with the service discovery. Each individual
 * instance in your distributed set of applications would create an instance of something similar to ExampleServer, * start it when the application comes up and close it when the application shuts down.
 * )
 */
public class ExampleServer implements Closeable {
    @Override
    public void close() throws IOException {

    }
}
