/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: NBcohlMuQa0ncQlHrrCNxV/Q1DkO0t4V
 */
package com.igomall.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 首页
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("shopIndexController")
@RequestMapping("/")
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "shop/index";
	}

}