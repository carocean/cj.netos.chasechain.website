package cj.netos.chasechain.website.service;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.chasechain.website.*;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.net.CircuitException;

import java.util.ArrayList;
import java.util.List;

@CjService(name = "poolService")
public class PoolService extends AbstractService implements IPoolService {
    @Override
    public ContentItem getContentItem(String pool, String item) throws CircuitException {
        ICube cube = cubePool(pool);
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple %s %s where {'tuple.id':'%s'}",
                ContentItem._COL_NAME, ContentItem.class.getName(), item);
        IQuery<ContentItem> query = cube.createQuery(cjql);
        IDocument<ContentItem> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        document.tuple().setPool(pool);
        return document.tuple();
    }
    @Override
    public ItemBehavior getItemBehavior(String trafficPool, String item) throws CircuitException {
        ICube cube = cubePool(trafficPool);
        String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'tuple.item':'%s'}",
                ItemBehavior._COL_NAME_INNER, ItemBehavior.class.getName(), item);
        IQuery<ItemBehavior> query = cube.createQuery(cjql);
        IDocument<ItemBehavior> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        return document.tuple();
    }
    @Override
    public List<BehaviorDetails> pageBehave(String trafficPool, String item, String behave, int limit, long offset) throws CircuitException {
        ICube cube = cubePool(trafficPool);
        String cjql = String.format("select {'tuple':'*'}.limit(%s).skip(%s).sort({'tuple.ctime':-1}) from tuple %s %s where {'tuple.item':'%s','tuple.behave':'%s'}",
                limit, offset,
                BehaviorDetails._COL_NAME, BehaviorDetails.class.getName(), item, behave);
        IQuery<BehaviorDetails> query = cube.createQuery(cjql);
        List<IDocument<BehaviorDetails>> list = query.getResultList();
        List<BehaviorDetails> behaviors = new ArrayList<>();
        for (IDocument<BehaviorDetails> document : list) {
            behaviors.add(document.tuple());
        }
        return behaviors;
    }

    @Override
    public TrafficPool getPool(String pool) throws CircuitException {
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple %s %s where {'tuple.id':'%s'}",
                TrafficPool._COL_NAME, TrafficPool.class.getName(), pool);
        IQuery<TrafficPool> query = home().createQuery(cjql);
        IDocument<TrafficPool> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        return document.tuple();
    }

    @Override
    public ContentBox getContentBox(String pool, String box) throws CircuitException {
        ICube cube = cubePool(pool);
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple %s %s where {'tuple.id':'%s'}",
                ContentBox._COL_NAME, ContentBox.class.getName(), box);
        IQuery<ContentBox> query = cube.createQuery(cjql);
        IDocument<ContentBox> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        document.tuple().setPool(pool);
        return document.tuple();
    }

    @Override
    public GeosphereDocument getGeoDocument(String docid) throws CircuitException {
        String colname = "geo.receptor.docs";
        String cjql = String.format("select {'tuple':'*'}.limit(1) from tuple %s %s where {'tuple.id':'%s'}",
                colname,
                GeosphereDocument.class.getName(),
                docid
        );
        IQuery<GeosphereDocument> query = home().createQuery(cjql);
        IDocument<GeosphereDocument> documentIDocument = query.getSingleResult();
        if (documentIDocument == null) {
            return null;
        }
        return documentIDocument.tuple();
    }

    @Override
    public List<Media> listGeoMedia(String docid) throws CircuitException {
        String colname = "geo.receptor.medias";
        String cjql = String.format("select {'tuple':'*'} from tuple %s %s where {'tuple.docid':'%s'}",
                colname,
                GeosphereMedia.class.getName(),
                docid
        );
        IQuery<GeosphereMedia> query = home().createQuery(cjql);
        List<IDocument<GeosphereMedia>> docs = query.getResultList();
        List<Media> list = new ArrayList<>();
        for (IDocument<GeosphereMedia> doc : docs) {
            list.add(doc.tuple().toMedia());
        }
        return list;
    }

    @Override
    public ChannelDocument getChannelDocument(String creator, String docid) throws CircuitException {
        ICube cube = cubePerson(creator);
        String cjql = String.format("select {'tuple':'*'} from tuple network.channel.documents %s where {'tuple.id':'%s'}",
                ChannelDocument.class.getName(),
                docid
        );
        IQuery<ChannelDocument> query = cube.createQuery(cjql);
        IDocument<ChannelDocument> document = query.getSingleResult();
        if (document == null) {
            return null;
        }
        return document.tuple();
    }

    @Override
    public List<Media> listChannelMedia(String creator, String channel, String docid) throws CircuitException {
        ICube cube = cubePerson(creator);
        String cjql = String.format("select {'tuple':'*'} from tuple network.channel.documents.medias %s where {'tuple.docid':'%s'}", ChannelMedia.class.getName(), docid);
        IQuery<ChannelMedia> query = cube.createQuery(cjql);
        List<IDocument<ChannelMedia>> docs = query.getResultList();
        List<Media> medias = new ArrayList<>();
        for (IDocument<ChannelMedia> doc : docs) {
            medias.add(doc.tuple().toMedia());
        }
        return medias;
    }
}
