// Copyright 2012 Citrix Systems, Inc. Licensed under the
// Apache License, Version 2.0 (the "License"); you may not use this
// file except in compliance with the License.  Citrix Systems, Inc.
// reserves all rights not expressly granted by the License.
// You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// 
// Automatically generated by addcopyright.py at 04/03/2012
package com.cloud.network.lb.dao;

import java.util.List;

import javax.ejb.Local;

import com.cloud.network.ElasticLbVmMapVO;
import com.cloud.network.LoadBalancerVO;
import com.cloud.network.dao.LoadBalancerDao;
import com.cloud.network.dao.LoadBalancerDaoImpl;
import com.cloud.network.router.VirtualRouter.Role;
import com.cloud.network.router.VirtualRouter.Role;
import com.cloud.utils.component.ComponentLocator;
import com.cloud.utils.db.GenericDaoBase;
import com.cloud.utils.db.JoinBuilder.JoinType;
import com.cloud.utils.db.SearchBuilder;
import com.cloud.utils.db.SearchCriteria;
import com.cloud.vm.DomainRouterVO;
import com.cloud.vm.dao.DomainRouterDao;
import com.cloud.vm.dao.DomainRouterDaoImpl;

@Local(value={ElasticLbVmMapDao.class})
public class ElasticLbVmMapDaoImpl extends GenericDaoBase<ElasticLbVmMapVO, Long> implements ElasticLbVmMapDao {
    protected final DomainRouterDao _routerDao = ComponentLocator.inject(DomainRouterDaoImpl.class);
    protected final LoadBalancerDao _loadbalancerDao = ComponentLocator.inject(LoadBalancerDaoImpl.class);

    
    protected final SearchBuilder<ElasticLbVmMapVO> AllFieldsSearch;
    protected final SearchBuilder<ElasticLbVmMapVO> UnusedVmSearch;
    protected final SearchBuilder<ElasticLbVmMapVO> LoadBalancersForElbVmSearch;


    protected final SearchBuilder<DomainRouterVO> ElbVmSearch;
    
    protected final SearchBuilder<LoadBalancerVO> LoadBalancerSearch;
   
    protected ElasticLbVmMapDaoImpl() {
        AllFieldsSearch  = createSearchBuilder();
        AllFieldsSearch.and("ipId", AllFieldsSearch.entity().getIpAddressId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("lbId", AllFieldsSearch.entity().getLbId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("elbVmId", AllFieldsSearch.entity().getElbVmId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.done();
   
        ElbVmSearch = _routerDao.createSearchBuilder();
        ElbVmSearch.and("role", ElbVmSearch.entity().getRole(), SearchCriteria.Op.EQ);
        UnusedVmSearch  = createSearchBuilder();
        UnusedVmSearch.and("elbVmId", UnusedVmSearch.entity().getElbVmId(), SearchCriteria.Op.NULL);
        ElbVmSearch.join("UnusedVmSearch", UnusedVmSearch, ElbVmSearch.entity().getId(), UnusedVmSearch.entity().getElbVmId(), JoinType.LEFTOUTER);
        ElbVmSearch.done();
        UnusedVmSearch.done();    
        
        LoadBalancerSearch = _loadbalancerDao.createSearchBuilder();
        LoadBalancersForElbVmSearch = createSearchBuilder();
        LoadBalancersForElbVmSearch.and("elbVmId", LoadBalancersForElbVmSearch.entity().getElbVmId(), SearchCriteria.Op.EQ);
        LoadBalancerSearch.join("LoadBalancersForElbVm", LoadBalancersForElbVmSearch, LoadBalancerSearch.entity().getId(), LoadBalancersForElbVmSearch.entity().getLbId(), JoinType.INNER);
        LoadBalancersForElbVmSearch.done();
        LoadBalancerSearch.done();

    }

    @Override
    public ElasticLbVmMapVO findOneByLbIdAndElbVmId(long lbId, long elbVmId) {
        SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("lbId", lbId);
        sc.setParameters("elbVmId", elbVmId);
        return findOneBy(sc);
    }

    @Override
    public List<ElasticLbVmMapVO> listByLbId(long lbId) {
        SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("lbId", lbId);
        return listBy(sc);
    }

    @Override
    public List<ElasticLbVmMapVO> listByElbVmId(long elbVmId) {
        SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("elbVmId", elbVmId);
        return listBy(sc);
    }

    @Override
    public int deleteLB(long lbId) {
    	SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("lbId", lbId);
        return super.expunge(sc);
    }

    @Override
    public ElasticLbVmMapVO findOneByIpIdAndElbVmId(long ipId, long elbVmId) {
        SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("ipId", ipId);
        sc.setParameters("elbVmId", elbVmId);
        return findOneBy(sc);
    }

    @Override
    public ElasticLbVmMapVO findOneByIp(long ipId) {
        SearchCriteria<ElasticLbVmMapVO> sc = AllFieldsSearch.create();
        sc.setParameters("ipId", ipId);
        return findOneBy(sc);
    }

    public List<DomainRouterVO> listUnusedElbVms() {
        SearchCriteria<DomainRouterVO> sc = ElbVmSearch.create();
        sc.setParameters("role", Role.LB);
        return _routerDao.search(sc, null);
    }
    
    @Override
    public List<LoadBalancerVO> listLbsForElbVm(long elbVmId) {
        SearchCriteria<LoadBalancerVO> sc = LoadBalancerSearch.create();
        sc.setJoinParameters("LoadBalancersForElbVm", "elbVmId", elbVmId);
        return _loadbalancerDao.search(sc, null);
    }
	
}
