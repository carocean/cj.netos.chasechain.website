package cj.netos.chasechain.website;

/**
 * 内容物在流量池中的当前行为情况表，类似于余额一样的作用，由behaviorDetails跟踪具体的变化<br>
 * 它就是内容物排行榜<br>
 *     存入TrafficPoolCube
 */
public class ItemBehavior {
    public static transient final String _COL_NAME_INNATE = "behavior.innate";
    public static transient final String _COL_NAME_INNER = "behavior.inner";
    String item;//唯一
    long likes;
    long comments;
    long recommends;
    long utime;//更新时间
    String pool;

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getRecommends() {
        return recommends;
    }

    public void setRecommends(long recommends) {
        this.recommends = recommends;
    }
}
