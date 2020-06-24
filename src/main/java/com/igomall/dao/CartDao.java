
package com.igomall.dao;

import com.igomall.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 删除过期购物车
	 */
	void deleteExpired();

}