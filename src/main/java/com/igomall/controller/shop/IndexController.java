
package com.igomall.controller.shop;

import com.igomall.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Controller - 首页
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("shopIndexController")
@RequestMapping("/")
public class IndexController extends BaseController {

	@Autowired
	private SeoService seoService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		System.out.println("1==="+seoService);
		return "shop/index";
	}

}