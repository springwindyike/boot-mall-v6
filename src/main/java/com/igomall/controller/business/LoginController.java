
package com.igomall.controller.business;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.igomall.entity.Business;
import com.igomall.security.CurrentUser;

/**
 * Controller - 商家登录
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Controller("businessLoginController")
@RequestMapping("/business")
public class LoginController extends BaseController {

	@Value("${security.business_login_success_url}")
	private String businessLoginSuccessUrl;

	/**
	 * 登录跳转
	 */
	@GetMapping({ "", "/" })
	public String index() {
		return "redirect:/business/login";
	}

	/**
	 * 登录页面
	 */
	@GetMapping("/login")
	public String index(@CurrentUser Business currentUser, ModelMap model) {
		if (currentUser != null) {
			return "redirect:" + businessLoginSuccessUrl;
		}
		model.addAttribute("businessLoginSuccessUrl", businessLoginSuccessUrl);
		return "business/login/index";
	}

}