
package com.igomall.service;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.StockLog;
import com.igomall.entity.Store;

/**
 * Service - 库存记录
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface StockLogService extends BaseService<StockLog, Long> {

	/**
	 * 查找库存记录分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 库存记录分页
	 */
	Page<StockLog> findPage(Store store, Pageable pageable);

}