/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: X8TIK9FKq5Bw5TfPsWSbSR+e4pGZl/vx
 */
package com.igomall.controller.admin;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.AndPredicate;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igomall.Pageable;
import com.igomall.Results;
import com.igomall.entity.Attribute;
import com.igomall.entity.BaseEntity;
import com.igomall.entity.Product;
import com.igomall.service.AttributeService;
import com.igomall.service.ProductCategoryService;

/**
 * Controller - 属性
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("adminAttributeController")
@RequestMapping("/admin/attribute")
public class AttributeController extends BaseController {

	@Inject
	private AttributeService attributeService;
	@Inject
	private ProductCategoryService productCategoryService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(Long sampleId, ModelMap model) {
		model.addAttribute("maxOptionSize", Attribute.MAX_OPTION_SIZE);
		model.addAttribute("sample", attributeService.find(sampleId));
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "admin/attribute/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(Attribute attribute, Long productCategoryId) {
		CollectionUtils.filter(attribute.getOptions(), new AndPredicate(new UniquePredicate(), new Predicate() {
			public boolean evaluate(Object object) {
				String option = (String) object;
				return StringUtils.isNotEmpty(option);
			}
		}));
		attribute.setProductCategory(productCategoryService.find(productCategoryId));
		if (!isValid(attribute, BaseEntity.Save.class)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		Integer propertyIndex = attributeService.findUnusedPropertyIndex(attribute.getProductCategory());
		if (propertyIndex == null) {
			return Results.unprocessableEntity("admin.attribute.addCountNotAllowed", Product.ATTRIBUTE_VALUE_PROPERTY_COUNT);
		}
		attribute.setPropertyIndex(null);
		attributeService.save(attribute);
		return Results.OK;
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	public String edit(Long id, ModelMap model) {
		model.addAttribute("maxOptionSize", Attribute.MAX_OPTION_SIZE);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("attribute", attributeService.find(id));
		return "admin/attribute/edit";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(Attribute attribute) {
		CollectionUtils.filter(attribute.getOptions(), new AndPredicate(new UniquePredicate(), new Predicate() {
			public boolean evaluate(Object object) {
				String option = (String) object;
				return StringUtils.isNotEmpty(option);
			}
		}));
		if (!isValid(attribute)) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		attributeService.update(attribute, "propertyIndex", "productCategory");
		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", attributeService.findPage(pageable));
		return "admin/attribute/list";
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> delete(Long[] ids) {
		attributeService.delete(ids);
		return Results.OK;
	}

}