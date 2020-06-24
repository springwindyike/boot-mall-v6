/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: HO51Oj+NyX5vB14Mu7uRf/0vPZx/S9bW
 */
package com.igomall.controller.admin;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.igomall.Pageable;
import com.igomall.Results;
import com.igomall.entity.Consultation;
import com.igomall.service.ConsultationService;

/**
 * Controller - 咨询
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminConsultationController")
@RequestMapping("/admin/consultation")
public class ConsultationController extends BaseController {

	@Inject
	private ConsultationService consultationService;

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("consultation", consultationService.find(id));
		return "admin/consultation/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(Long id, @RequestParam(defaultValue = "false") Boolean isShow) {
		Consultation consultation = consultationService.find(id);
		if (consultation == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (isShow != consultation.getIsShow()) {
			consultation.setIsShow(isShow);
			consultationService.update(consultation);
		}
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", consultationService.findPage(null, null, null, null, pageable));
		return "admin/consultation/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		if (ids != null) {
			consultationService.delete(ids);
		}
		return Results.OK;
	}

}