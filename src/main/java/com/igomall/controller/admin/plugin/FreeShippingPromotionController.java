
package com.igomall.controller.admin.plugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.igomall.Results;
import com.igomall.controller.admin.BaseController;
import com.igomall.entity.PluginConfig;
import com.igomall.plugin.FreeShippingPromotionPlugin;
import com.igomall.plugin.PaymentPlugin;
import com.igomall.plugin.PromotionPlugin;
import com.igomall.service.PluginConfigService;
import com.igomall.service.PromotionService;

/**
 * Controller - 免运费促销
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminPluginFreeShippingPromotionController")
@RequestMapping("/admin/plugin/free_shipping_promotion")
public class FreeShippingPromotionController extends BaseController {

	@Inject
	private FreeShippingPromotionPlugin freeShippingPromotionPlugin;
	@Inject
	private PluginConfigService pluginConfigService;
	@Inject
	private PromotionService promotionService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!freeShippingPromotionPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(freeShippingPromotionPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return Results.OK;
	}

	/**
	 * 卸载
	 */
	@PostMapping("/uninstall")
	public ResponseEntity<?> uninstall() {
		if (freeShippingPromotionPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(freeShippingPromotionPlugin.getId());
			promotionService.shutDownPromotion(freeShippingPromotionPlugin);
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = freeShippingPromotionPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/free_shipping_promotion/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, BigDecimal serviceCharge, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		PluginConfig pluginConfig = freeShippingPromotionPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put(PromotionPlugin.SERVICE_CHARGE, String.valueOf(serviceCharge));
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		if (!pluginConfig.getIsEnabled()) {
			promotionService.shutDownPromotion(freeShippingPromotionPlugin);
		}
		return Results.OK;
	}

}