package org.javautils.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.javautils.orm.DataSourceConfigRef;
import org.javautils.web.WebAppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebAppConfig {

	@Autowired
	@DataSourceConfigRef DataSource sampldDataSource;
	
	@Bean
	WebAppInfo webAppInfo() {
		WebAppInfo appInfo = new WebAppInfo();
		appInfo.setTitle("Sample Web Application");
		appInfo.setWebActions(Arrays.asList(WebAppInfo.WEB_ACTIONS));
		return appInfo;
	}
}
