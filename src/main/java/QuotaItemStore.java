import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * 指标数据存储结构
 */
public class QuotaItemStore {
    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    private Map<String, TreeSet<QuotaItem>> quotaTreeSetMap;

    public QuotaItemStore() {
        this.quotaTreeSetMap = new HashMap<>();
    }

    public void addQuotaItem(QuotaItem quotaItem) {
        readWriteLock.writeLock().lock();
        try {
            quotaTreeSetMap.putIfAbsent(quotaItem.getGroupId(), new TreeSet<>());
            quotaTreeSetMap.get(quotaItem.getGroupId()).add(quotaItem);
        } finally {
            readWriteLock.writeLock().unlock();
        }


    }

    public List<QuotaItem> findMinQuotas() {
        List<QuotaItem> quotaItemList;
        try {
            readWriteLock.readLock().lock();
            quotaItemList = quotaTreeSetMap.values().stream().map(TreeSet::first).collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }

        return quotaItemList;

    }
}
