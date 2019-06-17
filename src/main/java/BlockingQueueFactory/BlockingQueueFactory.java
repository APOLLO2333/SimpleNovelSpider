package BlockingQueueFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueFactory {
    private static volatile BlockingQueue<String> classifyQueue;
    private static volatile BlockingQueue<String> homeQueue;

    public static BlockingQueue<String> getClassifyQueue() {
        if (classifyQueue == null) {
            synchronized (BlockingQueueFactory.class) {
                if (classifyQueue == null) {
                    classifyQueue = new ArrayBlockingQueue<>(10);
                }
            }
        }
        return classifyQueue;
    }

    public static BlockingQueue<String> getHomeQueue() {
        if (homeQueue == null) {
            synchronized (BlockingQueueFactory.class) {
                if (homeQueue == null) {
                    homeQueue = new ArrayBlockingQueue<>(10);
                }
            }
        }
        return homeQueue;
    }
}
