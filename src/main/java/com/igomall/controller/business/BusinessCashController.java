/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: D+82S/Wk1AI1Zm+FAzK751auN8sffafu
 */
package com.igomall.controller.business;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igomall.Pageable;
import com.igomall.Results;
import com.igomall.Setting;
import com.igomall.entity.Business;
import com.igomall.entity.BusinessCash;
import com.igomall.security.CurrentUser;
import com.igomall.service.BusinessCashService;
import com.igomall.util.SystemUtils;

/**
 * Controller - 商家提现
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("businessBusinessCashController")
@RequestMapping("/business/business_cash")
public class BusinessCashController extends BaseController {

	@Inject
	private BusinessCashService businessCashService;

	/**
	 * 检查余额
	 */
	@GetMapping("/check_balance")
	public @ResponseBody boolean checkBalance(BigDecimal amount, @CurrentUser Business currentUser) {
		return amount.compareTo(BigDecimal.ZERO) > 0 && currentUser.getAvailableBalance().compareTo(amount) >= 0;
	}

	/**
	 * 申请提现
	 */
	@GetMapping("/application")
	public String cash() {
		return "business/business_cash/application";
	}

	/**
	 * 申请提现
	 */
	@PostMapping("/save")
	public ResponseEntity<?> applyCash(BusinessCash businessCash, @CurrentUser Business currentUser) {
		if (!isValid(businessCash)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Setting setting = SystemUtils.getSetting();
		if (currentUser.getBalance().compareTo(businessCash.getAmount()) < 0 || businessCash.getAmount().compareTo(setting.getBusinessMinimumCashAmount()) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		businessCashService.applyCash(businessCash, currentUser);

		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @CurrentUser Business currentUser, ModelMap model) {
		model.addAttribute("page", businessCashService.findPage(null, null, null, currentUser, pageable));
		return "business/business_cash/list";
	}

}