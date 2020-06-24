
package com.igomall.audit;

/**
 * Audit - 审计者Provider
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AuditorProvider<T> {

	/**
	 * 获取当前审计者
	 * 
	 * @return 当前审计者
	 */
	T getCurrentAuditor();

}