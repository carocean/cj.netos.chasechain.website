package cj.netos.chasechain.website;

import cj.studio.ecm.net.CircuitException;

import java.util.List;

public interface IPoolService {
    TrafficPool getPool(String pool) throws CircuitException;
    ContentBox getContentBox(String pool, String box) throws CircuitException;
    ContentItem getContentItem(String pool, String item) throws CircuitException;



    ItemBehavior getItemBehavior(String trafficPool, String item) throws CircuitException;

    List<BehaviorDetails> pageBehave(String trafficPool, String item, String behave, int limit, long offset) throws CircuitException;

    GeosphereDocument getGeoDocument(String docid) throws CircuitException;
    List<Media> listGeoMedia(String docid) throws CircuitException;
    ChannelDocument getChannelDocument(String creator, String id) throws CircuitException;
    List<Media> listChannelMedia(String creator, String channel, String docid) throws CircuitException;
}
