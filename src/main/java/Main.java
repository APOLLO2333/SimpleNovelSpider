import BlockingQueueFactory.BlockingQueueFactory;
import Start.ClassifyStart;
import Start.HomeStart;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import Process.NovelSpiderProcess;

public class Main {
    private static final String url = "https://m.23us.so/";

    public static void main(String[] args) {
        ClassifyStart classifyStart = new ClassifyStart(BlockingQueueFactory.getClassifyQueue());
        HomeStart homeStart = new HomeStart(BlockingQueueFactory.getHomeQueue());
        new Thread(classifyStart).start();
        new Thread(homeStart).start();
        Spider.create(new NovelSpiderProcess()).addPipeline(new ConsolePipeline()).setDownloader(new HttpClientDownloader()).addUrl(url).run();
        classifyStart.setFlag(false);
        homeStart.setFlag(false);
    }
}
