import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Solution {


    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        QuotaItemQueue quotaItemQueue = new QuotaItemQueue();
        QuotaItemStore quotaItemStore = new QuotaItemStore();
        URL classPath = Thread.currentThread().getContextClassLoader().getResource("test");
        if (classPath == null) {
            throw new RuntimeException("路径不存在");
        }
        String[] filePaths = new File(classPath.getFile()).list();
        if (filePaths == null) {
            throw new RuntimeException("空文件夹");
        }
        for (int i = 0; i < filePaths.length; i++) {
            String finalFilePath = classPath.getPath() + File.separator + filePaths[i];
            Producer producer = new Producer("producer_" + i, finalFilePath, quotaItemQueue);
            executorService.execute(() -> producer.produce());
        }
        //保证有点数据
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //消费者可以多个
        Consumer consumer = new Consumer("consumer_0",quotaItemQueue, quotaItemStore);
        executorService.execute(() -> consumer.consume());

        //保证存储结构里面有点数据
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        List<QuotaItem> list = quotaItemStore.findMinQuotas();
        System.out.printf("=====最终结果========\n");
        list.forEach(v -> {
            System.out.printf("result:" + v.toString() + "\n");
        });
        System.out.printf("=====完成========\n");

        try {
            consumer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
