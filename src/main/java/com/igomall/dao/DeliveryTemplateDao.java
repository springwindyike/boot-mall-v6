
package com.igomall.dao;

import java.util.List;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.DeliveryTemplate;
import com.igomall.entity.Store;

/**
 * Dao - 快递单模板
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface DeliveryTemplateDao extends BaseDao<DeliveryTemplate, Long> {

	/**
	 * 查找默认快递单模板
	 * 
	 * @param store
	 *            店铺
	 * @return 默认快递单模板，若不存在则返回null
	 */
	DeliveryTemplate findDefault(Store store);

	/**
	 * 查找快递单模板
	 * 
	 * @param store
	 *            店铺
	 * @return 快递单模板
	 */
	List<DeliveryTemplate> findList(Store store);

	/**
	 * 查找快递单模板分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 快递单模板分页
	 */
	Page<DeliveryTemplate> findPage(Store store, Pageable pageable);

	/**
	 * 清除默认
	 * 
	 * @param store
	 *            店铺
	 */
	void clearDefault(Store store);

	/**
	 * 清除默认
	 * 
	 * @param exclude
	 *            排除快递单模板
	 */
	void clearDefault(DeliveryTemplate exclude);

}