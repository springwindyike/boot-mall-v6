
package com.igomall.dao;

import com.igomall.Page;
import com.igomall.Pageable;
import com.igomall.entity.Coupon;
import com.igomall.entity.CouponCode;
import com.igomall.entity.Member;

/**
 * Dao - 优惠码
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface CouponCodeDao extends BaseDao<CouponCode, Long> {

	/**
	 * 查找优惠码分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 优惠码分页
	 */
	Page<CouponCode> findPage(Member member, Pageable pageable);

	/**
	 * 查找优惠码数量
	 * 
	 * @param coupon
	 *            优惠券
	 * @param member
	 *            会员
	 * @param hasBegun
	 *            是否已开始
	 * @param hasExpired
	 *            是否已过期
	 * @param isUsed
	 *            是否已使用
	 * @return 优惠码数量
	 */
	Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed);

}