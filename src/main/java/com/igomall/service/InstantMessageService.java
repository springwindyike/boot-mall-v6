
package com.igomall.service;

import java.util.List;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.InstantMessage;
import com.igomall.entity.Store;

/**
 * Service - 即时通讯
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface InstantMessageService extends BaseService<InstantMessage, Long> {

	/**
	 * 查找即时通讯
	 *
	 * @param type
	 *            类型
	 * @param storeId
	 *            店铺ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 即时通讯
	 */
	List<InstantMessage> findList(InstantMessage.Type type, Long storeId, Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

	/**
	 * 查找即时通讯分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 即时通讯分页
	 */
	Page<InstantMessage> findPage(Store store, Pageable pageable);

}