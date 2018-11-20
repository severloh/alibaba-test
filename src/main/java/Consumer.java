/**
 * 文件解析后的数据消费者
 */
public class Consumer {
    private String name;
    private QuotaItemQueue quotaItemQueue;
    private QuotaItemStore quotaItemStore;


    public Consumer(String name, QuotaItemQueue quotaItemQueue, QuotaItemStore quotaItemStore) {
        this.name = name;
        this.quotaItemQueue = quotaItemQueue;
        this.quotaItemStore = quotaItemStore;
    }

    public void consume() {
        while (true) {
            try {
                QuotaItem quotaItem = quotaItemQueue.take();
                if (quotaItem.isStopItem()) {
                    System.out.printf(this.name + ":停止消费标记==毒丸对象:" + quotaItem.toString() + "\n");
                    break;
                } else {
                    quotaItemStore.addQuotaItem(quotaItem);
                    System.out.printf(this.name + ":取出队列并放入集合" + quotaItem.toString() + "\n");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void stop() throws InterruptedException {
        quotaItemQueue.put(QuotaItem.POISON);
    }


}
