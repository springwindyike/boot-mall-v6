/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: bv8beIzZfIHffA0YrIDHJPBwdFL4qkGV
 */
package com.igomall.controller.admin;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igomall.Pageable;
import com.igomall.service.DeliveryCenterService;

/**
 * Controller - 发货点
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminDeliveryCenterController")
@RequestMapping("/admin/delivery_center")
public class DeliveryCenterController extends BaseController {

	@Inject
	private DeliveryCenterService deliveryCenterService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Model model, Pageable pageable) {
		model.addAttribute("page", deliveryCenterService.findPage(pageable));
		return "admin/delivery_center/list";
	}
}