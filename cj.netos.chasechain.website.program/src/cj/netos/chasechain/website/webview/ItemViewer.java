package cj.netos.chasechain.website.webview;

import cj.lns.chip.sos.cube.framework.IDocument;
import cj.lns.chip.sos.cube.framework.IQuery;
import cj.netos.chasechain.website.*;
import cj.netos.chasechain.website.model.AppAccount;
import cj.studio.ecm.CJSystem;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.ecm.net.Circuit;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.gateway.socket.app.IGatewayAppSiteResource;
import cj.studio.gateway.socket.app.IGatewayAppSiteWayWebView;
import cj.studio.openport.util.Encript;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CjService(name = "/pages/viewer.html")
public class ItemViewer implements IGatewayAppSiteWayWebView {
    @CjServiceRef
    IPoolService poolService;
    @CjServiceRef
    IPersonService personService;
    @CjServiceSite
    IServiceSite site;

    @Override
    public void flow(Frame frame, Circuit circuit, IGatewayAppSiteResource resource) throws CircuitException {
        String item = frame.parameter("item");
        String pool = frame.parameter("pool");
        if (StringUtil.isEmpty(pool) || StringUtil.isEmpty(item)) {
            throw new CircuitException("404", "参数为空");
        }
        ContentItem contentItem = poolService.getContentItem(pool, item);
//        System.out.println(new Gson().toJson(contentItem));
        ContentBox box = poolService.getContentBox(pool, contentItem.getBox());
//        System.out.println(new Gson().toJson(box));
        TrafficPool trafficPool = poolService.getPool(pool);
//        System.out.println(new Gson().toJson(trafficPool));
        String appKey = site.getProperty("appKey");
        String appSecret = site.getProperty("appSecret");
        String nonce = System.currentTimeMillis() + "";
        String accessToken = String.format("%s%s%s", appKey, appSecret, nonce);
        Document document = resource.html(frame.relativePath());
        printTitle(document, trafficPool, box, contentItem, accessToken, nonce);
        printBody(document, trafficPool, box, contentItem, accessToken, nonce);
        printSubject(document, trafficPool, box, contentItem, accessToken, nonce);
        printExtra(document, trafficPool, box, contentItem, accessToken, nonce);
        printBottom(document);
        circuit.content().writeBytes(document.html().getBytes());
    }

    private void printTitle(Document doc, TrafficPool pool, ContentBox box, ContentItem item, String accessToken, String nonce) {
        doc.select(".title span").html(box.getPointer().getTitle());
        String leading=box.getPointer().getLeading();
        if (StringUtil.isEmpty(leading)) {
            doc.select(".header img").attr("src", "../images/netflow.png");
        }else{
            doc.select(".header img").attr("src", String.format("%s?accessToken=%s&nonce=%s", leading, accessToken, nonce));
        }

    }

    private void printBody(Document doc, TrafficPool pool, ContentBox box, ContentItem item, String accessToken, String nonce) throws CircuitException {
        BoxPointer boxPointer = box.getPointer();
        String docid = item.getPointer().getId();
        List<Media> medias;
        String title = "";
        if ("geo.receptor".equals(boxPointer.getType())) {
            GeosphereDocument document = poolService.getGeoDocument(docid);
            String text=document.getText().replace("\n","<br/>");
            doc.select(".fx_content").html(text);
            medias = poolService.listGeoMedia(docid);
//            System.out.println(new Gson().toJson(medias));
            title = text;
        } else {
            String creator = item.getPointer().getCreator();
            ChannelDocument document = poolService.getChannelDocument(creator, docid);
            String text=document.getContent().replace("\n","<br/>");
            doc.select(".fx_content").html(text);
            medias = poolService.listChannelMedia(creator, box.getPointer().getId(), docid);
//            System.out.println(new Gson().toJson(medias));
            title = text;
        }
        if (!StringUtil.isEmpty(title) && title.length() > 40) {
            title = title.substring(0, 40);
        }
        doc.select("title").html(title);
        Element ul = doc.select(".my-gallery").first();
        printMedias(ul, medias, accessToken, nonce);
    }

    private void printMedias(Element ul, List<Media> medias, String accessToken, String nonce) {
        if (medias.isEmpty()) {
            ul.remove();
            return;
        }
        if (medias.size() == 1) {
            Media media = medias.get(0);
            if ("share".equals(media.getType())) {
                Element share = ul.select("share").first().clone();
                ul.empty();
                String leading=media.getLeading();
                int pos=leading.indexOf("?");
                if (pos < 0) {
                    leading=String.format("%s?accessToken=%s&nonce=%s", leading, accessToken, nonce);
                }else{
                    leading=String.format("%s&accessToken=%s&nonce=%s", leading, accessToken, nonce);
                }
                share.select("a").attr("href", media.getSrc());
                share.select("img").attr("src", leading);
                share.select("span").html(media.getText());
                ul.appendChild(share);
                return;
            }
            if ("video".equals(media.getType())) {
                Element video = ul.select("video").first().clone();
                ul.empty();
                String src=media.getSrc();
                int pos=src.indexOf("?");
                if (pos < 0) {
                    src=String.format("%s?accessToken=%s&nonce=%s", src, accessToken, nonce);
                }else{
                    src=String.format("%s&accessToken=%s&nonce=%s", src, accessToken, nonce);
                }
                Element source=  video.select("source").first().clone();
                video.empty();
                source.attr("src",src);
                video.appendChild(source);
                video.appendText("不支持的视频格式");
                ul.appendChild(video);
                return;
            }
        }
        Element cfigure = ul.select("figure").first().clone();
        ul.empty();
        for (Media media : medias) {
            Element figure = cfigure.clone();
            String src=String.format("%s?accessToken=%s&nonce=%s", media.getSrc(), accessToken, nonce);
            figure.select("a").attr("href", src);
            figure.select("img").attr("src", src);
            ul.appendChild(figure);
        }
    }


    private void printSubject(Document doc, TrafficPool pool, ContentBox box, ContentItem item, String accessToken, String nonce) throws CircuitException {
        doc.select(".subject .pool span").html(pool.getTitle());
        String ctime = DateUtils.convertTimeToFormat(item.getCtime());
        doc.select(".subject .ctime").html(ctime);
        Element creator = doc.select(".creator").first();
        String creatorPerson = item.getPointer().getCreator();
        AppAccount appAccount = personService.getPerson(creatorPerson);
        if (appAccount == null) {
            creator.remove();
        } else {
            creator.select("span").html(appAccount.getNickName());
            creator.select("img").attr("src", String.format("%s?accessToken=%s&nonce=%s", appAccount.getAvatar(), accessToken, nonce));
        }

    }

    private void printExtra(Document doc, TrafficPool pool, ContentBox box, ContentItem item, String accessToken, String nonce) throws CircuitException {
        ItemBehavior behavior = poolService.getItemBehavior(pool.getId(), item.getId());
        if (behavior == null) {
            behavior = new ItemBehavior();
            behavior.setComments(0L);
            behavior.setItem(item.getId());
            behavior.setComments(0);
            behavior.setLikes(0);
            behavior.setPool(pool.getId());
        }
        if (behavior.getRecommends() == 0) {
            doc.select(".recommenders").remove();
        }else{
            doc.select(".value").html(behavior.getRecommends() + "");
        }

        Element likesE = doc.select(".likes").first();

        List<BehaviorDetails> likes = poolService.pageBehave(pool.getId(), item.getId(), "like", 20, 0);
        if (likes.isEmpty()) {
            likesE.remove();
        } else {
            likesE.select("span").remove();
            List<String> list = new ArrayList<>();
            for (BehaviorDetails details : likes) {
                list.add(details.getPerson());
            }
            Map<String, AppAccount> map = new HashMap<>();
            List<AppAccount> appAccounts = personService.listPerson(list);
            for (AppAccount appAccount : appAccounts) {
                map.put(appAccount.getAccountId(), appAccount);
            }
            for (BehaviorDetails details : likes) {
                AppAccount appAccount = map.get(details.getPerson());
                if (appAccount == null) {
                    continue;
                }
                likesE.append(String.format("<span>%s</span>", appAccount.getNickName()));
            }
            if (likes.size() > 20) {
                likesE.append(String.format("<span>等共%s人</span>", behavior.getLikes()));
            }
        }

        Element commendsE = doc.select(".commends").first();
        List<BehaviorDetails> commends = poolService.pageBehave(pool.getId(), item.getId(), "comment", 30, 0);
        if (commends.isEmpty()) {
            commendsE.remove();
        } else {
            Element li = commendsE.select("li").first().clone();
            commendsE.empty();
            List<String> list = new ArrayList<>();
            for (BehaviorDetails details : commends) {
                list.add(details.getPerson());
            }
            Map<String, AppAccount> map = new HashMap<>();
            List<AppAccount> appAccounts = personService.listPerson(list);
            for (AppAccount appAccount : appAccounts) {
                map.put(appAccount.getAccountId(), appAccount);
            }

            for (BehaviorDetails details : commends) {
                AppAccount appAccount = map.get(details.getPerson());
                if (appAccount == null) {
                    continue;
                }
                Element cli = li.clone();
                cli.select(".nick").html(appAccount.getNickName());
                cli.select(".commend").html(details.getAttachment() == null ? "" : details.getAttachment());
                cli.select(".time").html(DateUtils.convertTimeToFormat(details.getCtime()));
                commendsE.appendChild(cli);
            }
            if (commends.size() > 30) {
                commendsE.append(String.format("<li style='padding-top:10px;text-align:right;font-size:12px;'><span>等共%s人评论</span></li>", behavior.getLikes()));
            }
        }
    }

    private void printBottom(Document doc) {

    }
}
