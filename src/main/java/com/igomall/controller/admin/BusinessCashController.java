/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: HtwfEzxf8JaHXgwpOmuqAz3v/J7xMsfB
 */
package com.igomall.controller.admin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igomall.Pageable;
import com.igomall.Results;
import com.igomall.entity.BusinessCash;
import com.igomall.service.BusinessCashService;

/**
 * Controller - 商家提现
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("adminBusinessCashController")
@RequestMapping("/admin/business_cash")
public class BusinessCashController extends BaseController {

	@Inject
	private BusinessCashService businessCashService;

	/**
	 * 审核
	 */
	@PostMapping("/review")
	public ResponseEntity<?> review(Long id, Boolean isPassed) {
		BusinessCash businessCash = businessCashService.find(id);
		if (isPassed == null || businessCash == null || !BusinessCash.Status.PENDING.equals(businessCash.getStatus())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		businessCashService.review(businessCash, isPassed);
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(BusinessCash.Status status, Pageable pageable, ModelMap model) {
		model.addAttribute("status", status);
		model.addAttribute("page", businessCashService.findPage(status, null, null, null, pageable));
		return "admin/business_cash/list";
	}

}