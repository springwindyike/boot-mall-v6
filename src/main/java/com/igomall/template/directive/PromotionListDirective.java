
package com.igomall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.entity.Promotion;
import com.igomall.plugin.PromotionPlugin;
import com.igomall.service.PluginService;
import com.igomall.service.PromotionService;
import com.igomall.util.FreeMarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 促销列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class PromotionListDirective extends BaseDirective {

	/**
	 * "促销插件ID"参数名称
	 */
	private static final String PROMOTION_PLUGIN_ID_PARAMETER_NAME = "promotionPluginId";

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	/**
	 * "会员等级ID"参数名称
	 */
	private static final String MEMBER_RANK_ID_PARAMETER_NAME = "memberRankId";

	/**
	 * "商品分类ID"参数名称
	 */
	private static final String PRODUCT_CATEGORY_ID_PARAMETER_NAME = "productCategoryId";

	/**
	 * "是否已开始"参数名称
	 */
	private static final String HAS_BEGUN_PARAMETER_NAME = "hasBegun";

	/**
	 * "是否已结束"参数名称
	 */
	private static final String HAS_ENDED_PARAMETER_NAME = "hasEnded";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "promotions";

	@Inject
	private PromotionService promotionService;
	@Inject
	private PluginService pluginService;

	public static PromotionListDirective promotionListDirective;

	@PostConstruct
	public void init() {
		promotionListDirective = this;
		promotionListDirective.promotionService = this.promotionService;
		promotionListDirective.pluginService = this.pluginService;
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
		String promotionPluginId = FreeMarkerUtils.getParameter(PROMOTION_PLUGIN_ID_PARAMETER_NAME, String.class, params);
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID_PARAMETER_NAME, Long.class, params);
		Long memberRankId = FreeMarkerUtils.getParameter(MEMBER_RANK_ID_PARAMETER_NAME, Long.class, params);
		Long productCategoryId = FreeMarkerUtils.getParameter(PRODUCT_CATEGORY_ID_PARAMETER_NAME, Long.class, params);
		Boolean hasBegun = FreeMarkerUtils.getParameter(HAS_BEGUN_PARAMETER_NAME, Boolean.class, params);
		Boolean hasEnded = FreeMarkerUtils.getParameter(HAS_ENDED_PARAMETER_NAME, Boolean.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Promotion.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		PromotionPlugin promotionPlugin = pluginService.getPromotionPlugin(promotionPluginId);
		List<Promotion> promotions = promotionService.findList(promotionPlugin, storeId, memberRankId, productCategoryId, hasBegun, hasEnded, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, promotions, env, body);
	}

}