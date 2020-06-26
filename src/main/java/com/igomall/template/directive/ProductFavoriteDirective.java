
package com.igomall.template.directive;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.entity.ProductFavorite;
import com.igomall.service.ProductFavoriteService;
import com.igomall.util.FreeMarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 模板指令 - 商品收藏
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class ProductFavoriteDirective extends BaseDirective {

	/**
	 * "会员ID"参数名称
	 */
	private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "productFavorites";

	@Resource
	private ProductFavoriteService productFavoriteService;

	public static ProductFavoriteDirective productFavoriteDirective;

	@PostConstruct
	public void init() {
		productFavoriteDirective = this;
		productFavoriteDirective.productFavoriteService = this.productFavoriteService;
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
		Long memberId = FreeMarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, ProductFavorite.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<ProductFavorite> productFavorites = productFavoriteService.findList(memberId, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, productFavorites, env, body);
	}

}