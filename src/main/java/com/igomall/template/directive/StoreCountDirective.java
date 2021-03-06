
package com.igomall.template.directive;

import com.igomall.entity.Store;
import com.igomall.service.StoreService;
import com.igomall.util.FreeMarkerUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * 模板指令 - 店铺数量
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class StoreCountDirective extends BaseDirective {

	/**
	 * "店铺类型"参数名称
	 */
	private static final String TYPE_PARAMETER_NAME = "type";

	/**
	 * "店铺状态"参数名称
	 */
	private static final String STATUS_PARAMETER_NAME = "status";

	/**
	 * "店铺是否启用"参数名称
	 */
	private static final String IS_ENABLED_PARAMETER_NAME = "isEnabled";

	/**
	 * "店铺是否过期"参数名称
	 */
	private static final String HAS_EXPIRED_PARAMETER_NAME = "hasExpired";

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "count";

	@Resource
	private StoreService storeService;

	public static StoreCountDirective storeCountDirective;

	@PostConstruct
	public void init() {
		storeCountDirective = this;
		storeCountDirective.storeService = this.storeService;
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
		Store.Type type = FreeMarkerUtils.getParameter(TYPE_PARAMETER_NAME, Store.Type.class, params);
		Store.Status status = FreeMarkerUtils.getParameter(STATUS_PARAMETER_NAME, Store.Status.class, params);
		Boolean isEnabled = FreeMarkerUtils.getParameter(IS_ENABLED_PARAMETER_NAME, Boolean.class, params);
		Boolean hasExpired = FreeMarkerUtils.getParameter(HAS_EXPIRED_PARAMETER_NAME, Boolean.class, params);

		long count = storeService.count(type, status, isEnabled, hasExpired);
		setLocalVariable(VARIABLE_NAME, count, env, body);
	}

}