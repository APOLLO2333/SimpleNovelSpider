package Start;

import BlockingQueueFactory.BlockingQueueFactory;
import us.codecraft.webmagic.Spider;
import Process.HomeSpiderProcess;

import java.util.concurrent.BlockingQueue;

public class ClassifyStart implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private static final String url = "https://m.23us.so/";
    private boolean flag = true;

    public ClassifyStart(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void startClassifySpider() {
        while (!blockingQueue.isEmpty() || this.flag) {
            if (!blockingQueue.isEmpty()) {
                try {
                    Spider.create(new HomeSpiderProcess(BlockingQueueFactory.getHomeQueue())).addUrl(url + blockingQueue.take()).run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void run() {
        this.startClassifySpider();
    }
}
