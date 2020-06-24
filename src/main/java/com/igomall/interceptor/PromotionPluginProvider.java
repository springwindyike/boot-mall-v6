/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: pvho6yIoEuE4QWVqXeTJ08tmveQwGfw8
 */
package com.igomall.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.igomall.plugin.PromotionPlugin;

/**
 * PromotionPlugin - 促销插件Provider
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface PromotionPluginProvider {

	/**
	 * 促销插件
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param handler
	 *            处理器
	 * @return 促销插件
	 */
	PromotionPlugin promotionPlugin(HttpServletRequest request, HttpServletResponse response, Object handler);

}