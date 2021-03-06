
package com.igomall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.igomall.entity.ProductCategory;
import com.igomall.service.ProductCategoryService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级商品分类列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class ProductCategoryRootListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "productCategories";

	@Resource
	private ProductCategoryService productCategoryService;

	public static ProductCategoryRootListDirective productCategoryRootListDirective;

	@PostConstruct
	public void init() {
		productCategoryRootListDirective = this;
		productCategoryRootListDirective.productCategoryService = this.productCategoryService;
	}

	/**
	 * 执行
	 * 
	 * @param env
	 *            环境变量
	 * @param params
	 *            参数
	 * @param loopVars
	 *            循环变量
	 * @param body
	 *            模板内容
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<ProductCategory> productCategories = productCategoryService.findRoots(count, useCache);
		setLocalVariable(VARIABLE_NAME, productCategories, env, body);
	}

}