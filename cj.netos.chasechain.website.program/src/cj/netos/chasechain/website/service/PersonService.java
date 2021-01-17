package cj.netos.chasechain.website.service;

import cj.netos.chasechain.website.IPersonService;
import cj.netos.chasechain.website.mapper.AppAccountMapper;
import cj.netos.chasechain.website.model.AppAccount;
import cj.netos.chasechain.website.model.AppAccountExample;
import cj.studio.ecm.annotation.CjBridge;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.orm.mybatis.annotation.CjTransaction;

import java.util.List;

@CjBridge(aspects = "@transaction")
@CjService(name = "personService")
public class PersonService implements IPersonService {
    @CjServiceRef(refByName = "mybatis.cj.netos.chasechain.website.mapper.AppAccountMapper")
    AppAccountMapper accountMapper;

    @CjTransaction
    @Override
    public List<AppAccount> listPerson(List<String> persons) throws CircuitException {
        AppAccountExample example = new AppAccountExample();
        example.createCriteria().andAccountIdIn(persons);
        return accountMapper.selectByExample(example);
    }
    @CjTransaction
    @Override
    public AppAccount getPerson(String person) throws CircuitException {
        AppAccountExample example = new AppAccountExample();
        example.createCriteria().andAccountIdEqualTo(person);
        List<AppAccount> list= accountMapper.selectByExample(example);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
