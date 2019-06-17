package Process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class IndexSpiderProcess implements PageProcessor {
    private static final String url = "https://m.23us.so";
    private  String name;
    private Site site = Site.me().setSleepTime(3000).setRetrySleepTime(3);

    public IndexSpiderProcess(String name) {
        this.name = name;
    }

    @Override
    public void process(Page page) {
        page.putField("name",name);
        page.putField("title", page.getHtml().xpath("//*[@id=\"_52mb_h1\"]//text()").toString());
        page.putField("text", page.getHtml().xpath("//*[@id=\"nr1\"]").toString());
        page.putField("nextUrl", page.getHtml().xpath("//*[@id=\"pb_next\"]//@href").toString());
        page.addTargetRequest(url + page.getHtml().xpath("//*[@id=\"pb_next\"]//@href").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
