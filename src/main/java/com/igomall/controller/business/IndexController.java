/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: FevzNSfMJ7zqPQ/RUBvANBLASmjWLvsU
 */
package com.igomall.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 商家中心
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("businessIndexController")
@RequestMapping("/business/index")
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@GetMapping
	public String index() {
		return "/business/index";
	}

}