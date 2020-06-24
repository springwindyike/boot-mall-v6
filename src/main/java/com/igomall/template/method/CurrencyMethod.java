/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: rn8+apmarptCPN4x5q+c+X/szNqwbfin
 */
package com.igomall.template.method;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.igomall.Setting;
import com.igomall.util.FreeMarkerUtils;
import com.igomall.util.SystemUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 模板方法 - 货币格式化
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class CurrencyMethod implements TemplateMethodModelEx {

	/**
	 * 执行
	 * 
	 * @param arguments
	 *            参数
	 * @return 结果
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		BigDecimal amount = FreeMarkerUtils.getArgument(0, BigDecimal.class, arguments);
		Boolean showSign = FreeMarkerUtils.getArgument(1, Boolean.class, arguments);
		Boolean showUnit = FreeMarkerUtils.getArgument(2, Boolean.class, arguments);
		if (amount != null) {
			Setting setting = SystemUtils.getSetting();
			String price = String.valueOf(setting.setScale(amount));
			if (showSign != null && showSign) {
				price = setting.getCurrencySign() + price;
			}
			if (showUnit != null && showUnit) {
				price += setting.getCurrencyUnit();
			}
			return new SimpleScalar(price);
		}
		return null;
	}

}