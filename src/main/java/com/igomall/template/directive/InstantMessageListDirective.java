
package com.igomall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.igomall.Filter;
import com.igomall.Order;
import com.igomall.entity.ArticleTag;
import com.igomall.entity.InstantMessage;
import com.igomall.service.InstantMessageService;
import com.igomall.util.FreeMarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 即时通讯
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class InstantMessageListDirective extends BaseDirective {

	/**
	 * "类型"参数名称
	 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/**
	 * "店铺ID"参数名称
	 */
	private static final String STORE_ID_PARAMETER_NAME = "storeId";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "instantMessages";

	@Inject
	private InstantMessageService instantMessageService;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		InstantMessage.Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, InstantMessage.Type.class, params);
		Long storeId = FreeMarkerUtils.getParameter(STORE_ID_PARAMETER_NAME, Long.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, ArticleTag.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(params);

		List<InstantMessage> instantMessages = instantMessageService.findList(type, storeId, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, instantMessages, env, body);
	}

}