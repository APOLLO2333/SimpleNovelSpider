package Process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.BlockingQueue;

public class HomeSpiderProcess implements PageProcessor {

    private Site site = Site.me().setSleepTime(3000).setRetrySleepTime(3);

    private BlockingQueue<String> blockingQueue;

    public HomeSpiderProcess() {
    }

    public HomeSpiderProcess(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void process(Page page) {
        String firstPage = page.getHtml().xpath("/html/body/div[2]/ul[2]/li[1]/a/@href").toString();
        String name = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/h2/a/text()").toString();
        try {
            blockingQueue.put(firstPage + "~" + name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
