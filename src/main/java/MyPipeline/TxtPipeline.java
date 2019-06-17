package MyPipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class TxtPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            String filePath = "G:\\novel\\" + resultItems.get("name") + ".txt";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file, true);
            Map<String, Object> map = resultItems.getAll();
            String title = map.get("title").toString();
            outputStream.write(title.getBytes());
            outputStream.write("\r\n".getBytes());
            String text = map.get("text").toString().substring(16).replace("</div>", "").
                    replace("&nbsp;", "").replaceAll("  ", "").
                    replaceAll("\r|\n", "").trim();
            String[] test = text.split("<br>");
            for (String s : test) {
                byte[] txt = s.getBytes();
                outputStream.write(txt);
                outputStream.write("\r\n".getBytes());
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
