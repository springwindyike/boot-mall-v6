/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: nlvj4ZW3Rh0S1Ae20q4HREFglt275+Z8
 */
package com.igomall.controller.business;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igomall.Results;
import com.igomall.Setting;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Business;
import com.igomall.entity.BusinessAttribute;
import com.igomall.security.UserAuthenticationToken;
import com.igomall.service.BusinessAttributeService;
import com.igomall.service.BusinessService;
import com.igomall.service.UserService;
import com.igomall.util.SystemUtils;

/**
 * Controller - 商家注册
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("businessRegisterController")
@RequestMapping("/business/register")
public class RegisterController extends BaseController {

	@Inject
	private UserService userService;
	@Inject
	private BusinessService businessService;
	@Inject
	private BusinessAttributeService businessAttributeService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !businessService.usernameExists(username);
	}

	/**
	 * 检查E-mail是否存在
	 */
	@GetMapping("/check_email")
	public @ResponseBody boolean checkEmail(String email) {
		return StringUtils.isNotEmpty(email) && !businessService.emailExists(email);
	}

	/**
	 * 检查手机是否存在
	 */
	@GetMapping("/check_mobile")
	public @ResponseBody boolean checkMobile(String mobile) {
		return StringUtils.isNotEmpty(mobile) && !businessService.mobileExists(mobile);
	}

	/**
	 * 注册页面
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "business/register/index";
	}

	/**
	 * 注册提交
	 */
	@PostMapping("/submit")
	public ResponseEntity<?> submit(String username, String password, String email, String mobile, HttpServletRequest request) {
		Setting setting = SystemUtils.getSetting();
		if (!ArrayUtils.contains(setting.getAllowedRegisterTypes(), Setting.RegisterType.BUSINESS)) {
			return Results.unprocessableEntity("business.register.disabled");
		}
		if (!isValid(Business.class, "username", username, BaseEntity.Save.class) || !isValid(Business.class, "password", password, BaseEntity.Save.class) || !isValid(Business.class, "email", email, BaseEntity.Save.class) || !isValid(Business.class, "mobile", mobile, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (businessService.usernameExists(username)) {
			return Results.unprocessableEntity("business.register.usernameExist");
		}
		if (businessService.emailExists(email)) {
			return Results.unprocessableEntity("business.register.emailExist");
		}
		if (businessService.mobileExists(mobile)) {
			return Results.unprocessableEntity("business.register.mobileExist");
		}

		Business business = new Business();
		business.removeAttributeValue();
		for (BusinessAttribute businessAttribute : businessAttributeService.findList(true, true)) {
			String[] values = request.getParameterValues("businessAttribute_" + businessAttribute.getId());
			if (!businessAttributeService.isValid(businessAttribute, values)) {
				return Results.UNPROCESSABLE_ENTITY;
			}
			Object memberAttributeValue = businessAttributeService.toBusinessAttributeValue(businessAttribute, values);
			business.setAttributeValue(businessAttribute, memberAttributeValue);
		}

		business.setUsername(username);
		business.setPassword(password);
		business.setEmail(email);
		business.setMobile(mobile);
		business.setBalance(BigDecimal.ZERO);
		business.setFrozenAmount(BigDecimal.ZERO);
		business.setStore(null);
		business.setBusinessCashs(null);
		business.setBusinessDepositLogs(null);
		business.setIsEnabled(true);
		business.setIsLocked(false);
		business.setLockDate(null);
		business.setLastLoginIp(request.getRemoteAddr());
		business.setLastLoginDate(new Date());

		userService.register(business);
		userService.login(new UserAuthenticationToken(Business.class, username, password, false, request.getRemoteAddr()));
		return Results.OK;
	}

}