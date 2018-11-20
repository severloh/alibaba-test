import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 指标数据生产者
 */
public class Producer {
    private String name;
    private String filePath;
    private QuotaItemQueue quotaItemQueue;


    public Producer(String name,String filePath, QuotaItemQueue quotaItemQueue) {
        this.name=name;
        this.filePath = filePath;
        this.quotaItemQueue = quotaItemQueue;
    }


    public void produce() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String contentLine = br.readLine();
            while (contentLine != null) {
                String[] quotas = contentLine.split(",");
                QuotaItem quotaItem = new QuotaItem(quotas[0], quotas[1], Float.valueOf(quotas[2] == null ? "0" : quotas[2]));
                quotaItemQueue.put(quotaItem);
                System.out.printf(this.name + ":放入队列" + quotaItem.toString() + "\n");
                contentLine = br.readLine();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




