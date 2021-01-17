package cj.netos.chasechain.website;

public class GeosphereDocument {
    String id;
    String upstreamPerson;
    //如果是从网流来的消息
    String upstreamChannel;
    String sourceSite;
    String sourceApp;
    String receptor;
    String category;
    String channel;
    String brand;
    String creator;
    long ctime;
    long atime;
    long rtime;
    long dtime;
    String state;
    String text;
    String purchaseSn;
    ///location是GEOPoi对象
    LatLng location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpstreamPerson() {
        return upstreamPerson;
    }

    public void setUpstreamPerson(String upstreamPerson) {
        this.upstreamPerson = upstreamPerson;
    }

    public String getUpstreamChannel() {
        return upstreamChannel;
    }

    public void setUpstreamChannel(String upstreamChannel) {
        this.upstreamChannel = upstreamChannel;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    public String getSourceApp() {
        return sourceApp;
    }

    public void setSourceApp(String sourceApp) {
        this.sourceApp = sourceApp;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getAtime() {
        return atime;
    }

    public void setAtime(long atime) {
        this.atime = atime;
    }

    public long getRtime() {
        return rtime;
    }

    public void setRtime(long rtime) {
        this.rtime = rtime;
    }

    public long getDtime() {
        return dtime;
    }

    public void setDtime(long dtime) {
        this.dtime = dtime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPurchaseSn() {
        return purchaseSn;
    }

    public void setPurchaseSn(String purchaseSn) {
        this.purchaseSn = purchaseSn;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
