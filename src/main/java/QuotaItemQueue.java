import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 指标数据队列
 */
public class QuotaItemQueue {
    private BlockingQueue<QuotaItem> queue;


    public QuotaItemQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void put(QuotaItem quotaItem) throws InterruptedException {
        queue.put(quotaItem);
    }

    public QuotaItem take() throws InterruptedException {
        return queue.take();

    }
}
