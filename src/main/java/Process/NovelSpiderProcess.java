package Process;

import BlockingQueueFactory.BlockingQueueFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class NovelSpiderProcess implements PageProcessor {
    private static final String url = "https://m.23us.so";
    private Site site = Site.me().setSleepTime(3000).setRetrySleepTime(3);

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("/wapsort/._.\\.html").all();
        for (String link : links) {
            System.out.println(url + link);
            Spider.create(new ClassifySpiderProcess(BlockingQueueFactory.getClassifyQueue())).
                    setDownloader(new HttpClientDownloader()).addUrl(url + link).run();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
