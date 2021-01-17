package cj.netos.chasechain.website;

import cj.lns.chip.sos.cube.framework.ICube;
import cj.lns.chip.sos.disk.INetDisk;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;

public class AbstractService {
    private static transient final String _COL_NAME_dashboard = "traffic.dashboard.pointers";
    @CjServiceRef(refByName = "mongodb.netos")
    INetDisk disk;

    protected ICube cubePool(String pool) throws CircuitException {
        String col = String.format("%s.%s", Constants._COL_NAME, pool);

        return disk.cube(col);
    }

    protected ICube cubePerson(String person) throws CircuitException {
        String cubeName = person;

        return disk.cube(cubeName);
    }

    protected ICube home() throws CircuitException {
        return disk.home();
    }
}
