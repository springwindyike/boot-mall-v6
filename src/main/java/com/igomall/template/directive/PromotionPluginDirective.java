
package com.igomall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.igomall.plugin.PromotionPlugin;
import com.igomall.service.PluginService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 促销插件
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class PromotionPluginDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "promotionPlugin";

	@Inject
	private PluginService pluginService;

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
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<PromotionPlugin> promotionPlugins = pluginService.getPromotionPlugins(true);

		setLocalVariable(VARIABLE_NAME, CollectionUtils.isNotEmpty(promotionPlugins), env, body);
	}

}