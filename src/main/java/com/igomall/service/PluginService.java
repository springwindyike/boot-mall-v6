
package com.igomall.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.igomall.plugin.LoginPlugin;
import com.igomall.plugin.PaymentPlugin;
import com.igomall.plugin.PromotionPlugin;
import com.igomall.plugin.StoragePlugin;

/**
 * Service - 插件
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PluginService {

	/**
	 * 获取支付插件
	 * 
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins();

	/**
	 * 获取存储插件
	 * 
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins();

	/**
	 * 获取登录插件
	 * 
	 * @return 登录插件
	 */
	List<LoginPlugin> getLoginPlugins();

	/**
	 * 获取促销插件
	 * 
	 * @return 促销插件
	 */
	List<PromotionPlugin> getPromotionPlugins();

	/**
	 * 获取支付插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 支付插件
	 */
	List<PaymentPlugin> getPaymentPlugins(boolean isEnabled);

	/**
	 * 获取有效支付插件
	 * 
	 * @param request
	 *            request
	 * @return 有效支付插件
	 */
	List<PaymentPlugin> getActivePaymentPlugins(HttpServletRequest request);

	/**
	 * 获取存储插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 存储插件
	 */
	List<StoragePlugin> getStoragePlugins(boolean isEnabled);

	/**
	 * 获取登录插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 登录插件
	 */
	List<LoginPlugin> getLoginPlugins(boolean isEnabled);

	/**
	 * 获取有效登录插件
	 * 
	 * @param request
	 *            request
	 * @return 有效登录插件
	 */
	List<LoginPlugin> getActiveLoginPlugins(HttpServletRequest request);

	/**
	 * 获取促销插件
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @return 促销插件
	 */
	List<PromotionPlugin> getPromotionPlugins(boolean isEnabled);

	/**
	 * 获取支付插件
	 * 
	 * @param id
	 *            ID
	 * @return 支付插件
	 */
	PaymentPlugin getPaymentPlugin(String id);

	/**
	 * 获取存储插件
	 * 
	 * @param id
	 *            ID
	 * @return 存储插件
	 */
	StoragePlugin getStoragePlugin(String id);

	/**
	 * 获取登录插件
	 * 
	 * @param id
	 *            ID
	 * @return 登录插件
	 */
	LoginPlugin getLoginPlugin(String id);

	/**
	 * 获取促销插件
	 * 
	 * @param id
	 *            ID
	 * @return 促销插件
	 */
	PromotionPlugin getPromotionPlugin(String id);

}