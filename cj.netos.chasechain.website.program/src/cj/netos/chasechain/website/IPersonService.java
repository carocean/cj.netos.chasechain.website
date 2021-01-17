package cj.netos.chasechain.website;

import cj.netos.chasechain.website.model.AppAccount;
import cj.studio.ecm.net.CircuitException;

import java.util.List;

public interface IPersonService {
    List<AppAccount> listPerson(List<String> persons)throws CircuitException;
    AppAccount getPerson(String person)throws CircuitException;
}
