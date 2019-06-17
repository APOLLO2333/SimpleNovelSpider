package Start;

import BlockingQueueFactory.BlockingQueueFactory;
import MyPipeline.TxtPipeline;
import us.codecraft.webmagic.Spider;
import Process.IndexSpiderProcess;

import java.util.concurrent.BlockingQueue;

public class HomeStart implements Runnable {
    private BlockingQueue<String> blockingQueue;
    private static final String url = "https://m.23us.so/";
    private boolean flag = true;

    public HomeStart(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void startHomeSpider() {
        while (!blockingQueue.isEmpty() || this.flag) {
            if (!blockingQueue.isEmpty()) {
                new Thread(() -> {
                    try {
                        String target = blockingQueue.take();
                        System.out.println(url + target.split("!")[0]);
                        Spider.create(new IndexSpiderProcess(target.split("~")[1])).
                                addPipeline(new TxtPipeline()).addUrl(url + target.split("~")[0]).run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

    @Override
    public void run() {
        this.startHomeSpider();
    }
}
