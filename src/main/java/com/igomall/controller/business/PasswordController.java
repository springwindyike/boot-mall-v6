/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: kmKLgZv9N+sGyd1IbegIWfGFqIWyWV1a
 */
package com.igomall.controller.business;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igomall.Results;
import com.igomall.entity.Business;
import com.igomall.security.CurrentUser;
import com.igomall.service.BusinessService;

/**
 * Controller - 密码
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("businessPasswordController")
@RequestMapping("/business/password")
public class PasswordController extends BaseController {

	@Inject
	private BusinessService businessService;

	/**
	 * 验证当前密码
	 */
	@GetMapping("/check_current_password")
	public @ResponseBody boolean checkCurrentPassword(String currentPassword, @CurrentUser Business currentUser) {
		return StringUtils.isNotEmpty(currentPassword) && currentUser.isValidCredentials(currentPassword);
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit() {
		return "business/password/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String currentPassword, String password, @CurrentUser Business currentUser) {
		if (StringUtils.isEmpty(currentPassword) || StringUtils.isEmpty(password)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!currentUser.isValidCredentials(currentPassword)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (!isValid(Business.class, "password", password)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		currentUser.setPassword(password);
		businessService.update(currentUser);

		return Results.OK;
	}

}