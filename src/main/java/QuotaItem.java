import java.util.Objects;

/**
 * 指标数据单元
 */
public class QuotaItem implements Comparable<QuotaItem> {
    private String id;
    private String groupId;
    private Float quota;
    public static QuotaItem POISON = new QuotaItem("0000", "0000", 0f);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QuotaItem(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + groupId.hashCode() + quota.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getGroupId() {

        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    public boolean isStopItem() {
        return Objects.equals(POISON, this);
    }

    @Override
    public String toString() {
        return "QuotaItem{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", quota=" + quota +
                '}';
    }


    @Override
    public int compareTo(QuotaItem quotaItem) {
        if (this.quota > quotaItem.getQuota()) {
            return 1;
        } else if (this.quota == quotaItem.getQuota()) {
            return this.id.compareTo(quotaItem.getId());
        } else {
            return -1;
        }
    }
}
