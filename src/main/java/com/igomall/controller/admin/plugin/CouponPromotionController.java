
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
import com.igomall.plugin.CouponPromotionPlugin;
import com.igomall.plugin.PaymentPlugin;
import com.igomall.plugin.PromotionPlugin;
import com.igomall.service.PluginConfigService;
import com.igomall.service.PromotionService;

/**
 * Controller - 优惠券
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("adminPluginCouponPromotionController")
@RequestMapping("/admin/plugin/coupon_promotion")
public class CouponPromotionController extends BaseController {

	@Inject
	private CouponPromotionPlugin couponPromotionPlugin;
	@Inject
	private PluginConfigService pluginConfigService;
	@Inject
	private PromotionService promotionService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!couponPromotionPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(couponPromotionPlugin.getId());
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
		if (couponPromotionPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(couponPromotionPlugin.getId());
			promotionService.shutDownPromotion(couponPromotionPlugin);
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = couponPromotionPlugin.getPluginConfig();
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/coupon_promotion/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, BigDecimal serviceCharge, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) < 0) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		PluginConfig pluginConfig = couponPromotionPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put(PromotionPlugin.SERVICE_CHARGE, String.valueOf(serviceCharge));
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		if (!pluginConfig.getIsEnabled()) {
			promotionService.shutDownPromotion(couponPromotionPlugin);
		}
		return Results.OK;
	}

}