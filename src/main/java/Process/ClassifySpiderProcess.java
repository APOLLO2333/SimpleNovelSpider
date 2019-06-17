package Process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class ClassifySpiderProcess implements PageProcessor {
    private Site site = Site.me().setSleepTime(3000).setRetrySleepTime(3);

    private BlockingQueue<String> blockingQueue;

    public ClassifySpiderProcess() {
    }

    public ClassifySpiderProcess(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void process(Page page) {
//        String firstLink = page.getHtml().xpath("/html/body/div[4]/a[1]/@href").toString();
//        Selectable selectable = page.getHtml().$("body > div.pages").;
        List<String> links = page.getHtml().$("body > div.pages").links().all();
//        String firstLink = page.getHtml().$("body > div.pages > a:nth-child(1)").links().toString();
        String firstLink = links.get(0);
        String currentLink = page.getHtml().$("body > div.pages > b").toString().substring(26, 27);
//        String lastLink = page.getHtml().xpath("/html/body/div[4]/a[text()='尾页']/@href").toString();
        String lastLink = links.get(links.size() - 1);
        String url = firstLink.split("_")[0] + "_";
        int firstPage = Integer.parseInt(firstLink.split("_")[1].split("\\.")[0]);
        int currentPage = Integer.parseInt(currentLink);
        int lastPage = Integer.parseInt(lastLink.split("_")[1].split("\\.")[0]);
        Set<String> TargetLinks = new HashSet<>(page.getHtml().links().regex("/wapbook/\\d+.html").all());
        for (String link : TargetLinks) {
            try {
                blockingQueue.put(link);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (currentPage <= lastPage && currentPage >= firstPage) {
            page.addTargetRequest(url + (currentPage + 1) + ".html");
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
