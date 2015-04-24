package org.javautils.web;

import java.util.List;

import org.apache.commons.lang3.Validate;

public class WebAppInfo {

	public static final WebAction[] WEB_ACTIONS = new WebAction[] {
		new WebAction("/actions/action1.do", "Action_1"),
		new WebAction("/actions/action2.do", "Action_2"),
		new WebAction("/actions/action3.do", "Action_3"),
		new WebAction("/actions/action4.do", "Action_4"),
		new WebAction("/actions/action5.do", "Action_5"),
	};
	
	private String title;
	private List<WebAction> webActions;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<WebAction> getWebActions() {
		return webActions;
	}
	public void setWebActions(List<WebAction> webActions) {
		this.webActions = webActions;
	}

	public static class WebAction {
		private String url, title;

		public WebAction(String url, String title) {
			Validate.notEmpty(url, "Req'd parameter 'url' is null/empty");
			Validate.notEmpty(title, "Req'd parameter 'title' is null/empty");
			this.url = url;
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}
