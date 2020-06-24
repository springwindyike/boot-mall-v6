
package com.igomall.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.igomall.Order;
import com.igomall.dao.SnDao;
import com.igomall.dao.SvcDao;
import com.igomall.entity.Sn;
import com.igomall.entity.Store;
import com.igomall.entity.StoreRank;
import com.igomall.entity.Svc;
import com.igomall.service.SvcService;

/**
 * Service - 服务
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class SvcServiceImpl extends BaseServiceImpl<Svc, Long> implements SvcService {

	@Inject
	private SvcDao svcDao;
	@Inject
	private SnDao snDao;

	@Override
	@Transactional(readOnly = true)
	public Svc findBySn(String sn) {
		return svcDao.find("sn", StringUtils.lowerCase(sn));
	}

	@Override
	@Transactional(readOnly = true)
	public Svc findTheLatest(Store store, String promotionPluginId, StoreRank storeRank) {

		List<Order> orderList = new ArrayList<>();
		orderList.add(new Order("createdDate", Order.Direction.DESC));
		List<Svc> serviceOrders = svcDao.find(store, promotionPluginId, storeRank, orderList);

		return CollectionUtils.isNotEmpty(serviceOrders) ? serviceOrders.get(0) : null;
	}

	@Override
	@Transactional
	public Svc save(Svc svc) {
		Assert.notNull(svc, "[Assertion failed] - svc is required; it must not be null");

		svc.setSn(snDao.generate(Sn.Type.PLATFORM_SERVICE));

		return super.save(svc);
	}

}