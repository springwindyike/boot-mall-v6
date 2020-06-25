
package com.igomall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.entity.ProductTag;
import com.igomall.service.ProductTagService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 商品标签列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class ProductTagListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "productTags";

	@Inject
	private ProductTagService productTagService;

	public static ProductTagListDirective productTagListDirective;

	@PostConstruct
	public void init() {
		productTagListDirective = this;
		productTagListDirective.productTagService = this.productTagService;
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
		List<Filter> filters = getFilters(params, ProductTag.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<ProductTag> productTags = productTagService.findList(count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, productTags, env, body);
	}

}