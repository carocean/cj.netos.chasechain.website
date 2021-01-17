package cj.netos.chasechain.website.webview;

import cj.netos.chasechain.website.IPoolService;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.Circuit;
import cj.studio.ecm.net.CircuitException;
import cj.studio.ecm.net.Frame;
import cj.studio.gateway.socket.app.IGatewayAppSiteResource;
import cj.studio.gateway.socket.app.IGatewayAppSiteWayWebView;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;

@CjService(name = "/behavior.service")
public class BehaviorWebview implements IGatewayAppSiteWayWebView {
    @CjServiceRef
    IPoolService poolService;

    @Override
    public void flow(Frame frame, Circuit circuit, IGatewayAppSiteResource resource) throws CircuitException {
        String limit = frame.parameter("limit");
        String offset = frame.parameter("offset");
        String item = frame.parameter("item");
        String pool = frame.parameter("pool");
        String behave = frame.parameter("behave");
        if (StringUtil.isEmpty(pool) || StringUtil.isEmpty(item) || StringUtil.isEmpty(behave) || StringUtil.isEmpty(limit) || StringUtil.isEmpty(offset)) {
            throw new CircuitException("404", "参数为空");
        }
        Object list = poolService.pageBehave(pool, item, behave, Integer.valueOf(limit), Long.valueOf(offset));
        circuit.content().writeBytes(new Gson().toJson(list).getBytes());
    }
}
