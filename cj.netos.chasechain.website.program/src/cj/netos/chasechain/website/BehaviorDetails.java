package cj.netos.chasechain.website;

/**
 * 内容物行为明细，反映内容行为情况的详细变化原因
 * <br>
 *  *     存入TrafficPoolCube
 */
public class BehaviorDetails {
    public final static transient String _COL_NAME = "behavior.details";
    String person;
    String item;
    String behave;//如：like,comment,recommended
    String attachment;//行为附件
    long ctime;
    String pool;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }
    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBehave() {
        return behave;
    }

    public void setBehave(String behave) {
        this.behave = behave;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }
}
