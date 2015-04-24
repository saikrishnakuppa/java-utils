package org.javautils.web;

import java.util.List;

import org.javautils.web.WebAppInfo.WebAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AbstractWebController {

	@Autowired
	private WebAppInfo webAppInfo;
	@ModelAttribute("webAppTitle")
	public String webAppTitle() {
		return webAppInfo.getTitle();
	}
	@ModelAttribute("webAppActions")
	public List<WebAction> webAppActions() {
		return webAppInfo.getWebActions();
	}
	@ModelAttribute("appInfo")
	public AppInfo getAppInfo() {
		return AppInfo.getInstance();
	}
}
