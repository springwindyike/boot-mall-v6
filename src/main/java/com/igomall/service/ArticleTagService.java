
package com.igomall.service;

import java.util.List;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.entity.ArticleTag;

/**
 * Service - 文章标签
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface ArticleTagService extends BaseService<ArticleTag, Long> {

	/**
	 * 查找文章标签
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 文章标签
	 */
	List<ArticleTag> findList(Integer count, List<Filter> filters, List<Order> orders, boolean useCache);

}