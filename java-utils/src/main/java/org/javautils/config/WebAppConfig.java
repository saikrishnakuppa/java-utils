package org.javautils.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.javautils.orm.DataSourceConfigRef;
import org.javautils.web.AppInfo;
import org.javautils.web.WebAppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.endpoint.GenericMessageEndpointFactory.InternalResourceException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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
	@Bean
	public AppInfo appInfo(@Value("${buildJDK}") String buildJDK, @Value("${buildTimestamp}") String buildTimestamp,
			@Value("${builtBy}") String builtBy, @Value("${version}") String version) {
		AppInfo appInfo = AppInfo.getInstance();
		appInfo.setBuildJDK(buildJDK);
		appInfo.setBuildTimestamp(buildTimestamp);
		appInfo.setBuiltBy(builtBy);
		appInfo.setVersion(version);
		return appInfo;
	}
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
