/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: KkFuvtI3CJCKK39CONyI5NDzPJX78Ty9
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
import com.igomall.entity.CategoryApplication;
import com.igomall.service.CategoryApplicationService;

/**
 * Controller - 经营分类申请
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminCategoryApplicationController")
@RequestMapping("/admin/category_application")
public class CategoryApplicationController extends BaseController {

	@Inject
	private CategoryApplicationService categoryApplicationService;

	/**
	 * 审核
	 */
	@PostMapping("/review")
	public ResponseEntity<?> review(Long id, Boolean isPassed) {
		CategoryApplication categoryApplication = categoryApplicationService.find(id);
		if (categoryApplication == null || isPassed == null || !CategoryApplication.Status.PENDING.equals(categoryApplication.getStatus())) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (categoryApplicationService.exist(categoryApplication.getStore(), categoryApplication.getProductCategory(), CategoryApplication.Status.APPROVED)) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		categoryApplicationService.review(categoryApplication, isPassed);
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(CategoryApplication.Status status, Pageable pageable, ModelMap model) {
		model.addAttribute("status", status);
		model.addAttribute("page", categoryApplicationService.findPage(status, null, null, pageable));
		return "admin/category_application/list";
	}

}