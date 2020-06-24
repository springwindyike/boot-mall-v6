
package com.igomall.service;

import java.util.List;

import com.igomall.entity.SpecificationItem;

/**
 * Service - 规格项
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface SpecificationItemService {

	/**
	 * 规格项过滤
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	void filter(List<SpecificationItem> specificationItems);

}