/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: A/d7Td7OHeAObQ/r8aPQV7BYJrrG+MiT
 */
package com.igomall.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igomall.entity.Member;
import com.igomall.entity.SocialUser;
import com.igomall.security.CurrentUser;
import com.igomall.service.PluginService;
import com.igomall.service.SocialUserService;

/**
 * Controller - 会员登录
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("memberLoginController")
@RequestMapping("/member/login")
public class LoginController extends BaseController {

	@Value("${security.member_login_success_url}")
	private String memberLoginSuccessUrl;

	@Inject
	private PluginService pluginService;
	@Inject
	private SocialUserService socialUserService;

	/**
	 * 登录页面
	 */
	@GetMapping
	public String index(Long socialUserId, String uniqueId, @CurrentUser Member currentUser, HttpServletRequest request, ModelMap model) {
		if (socialUserId != null && StringUtils.isNotEmpty(uniqueId)) {
			SocialUser socialUser = socialUserService.find(socialUserId);
			if (socialUser == null || socialUser.getUser() != null || !StringUtils.equals(socialUser.getUniqueId(), uniqueId)) {
				return UNPROCESSABLE_ENTITY_VIEW;
			}
			model.addAttribute("socialUserId", socialUserId);
			model.addAttribute("uniqueId", uniqueId);
		}
		model.addAttribute("memberLoginSuccessUrl", memberLoginSuccessUrl);
		model.addAttribute("loginPlugins", pluginService.getActiveLoginPlugins(request));
		return "/member/login/index";
	}

}