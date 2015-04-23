package org.javautils.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Value("${generateStatistics:false}")
	private String generateStatistics;
	
	@Value("${sqlDialect:org.hibernate.dialect.MySQLDialect}")
	private String sqlDialect;
	
	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(
			@Value("classpath*:/router-default.properties") Resource[] defaultProps) {
		List<Resource> resources = new ArrayList<Resource>();
		resources.addAll(Arrays.asList(defaultProps));
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
		props.setLocations(resources.toArray(new Resource[resources.size()]));
		props.setNullValue("<null>");
		return props;
	}
	
	@Bean
	public DataSource sampleDataSource(
			@Value("${sample.jdbc.driverClass:com.mysql.jdbc.Driver}") String sampleJdbcDriverClass,
			@Value("${sample.jdbc.url:jdbc:mysql://localhost:3306/test?user=root&password=action007}") String sampleJdbcUrl,
			@Value("${sample.jdbc.connCheckingQuery:select 1}") String connCheckingQuery
//			@Value("${sample.jdbc.connections.initialSize:0}") int initialSize,
//			@Value("${sample.jdbc.connections.maxActive:-1}") int maxActive
			) throws SQLException {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(sampleJdbcDriverClass);
		dataSource.setUrl(sampleJdbcUrl);
		dataSource.setInitialSize(0);
		dataSource.setMaxActive(-1);
		dataSource.setConnectionInitSqls(Collections.singletonList(connCheckingQuery));
		dataSource.getConnection();
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate sampleJdbcTemplate(@DataSourceConfigRef DataSource sampleDataSource) {
		return new JdbcTemplate(sampleDataSource);
	}
	
	@Bean
	@Lazy
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@DataSourceConfigRef DataSource sampleDataSource,
			@Value("${smdb.orm.extensionPackages:org.javautils.orm}") String... extensionPackages) {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(sampleDataSource);
		String[] allPackages = ArrayUtils.add(extensionPackages, MessageEvent.class.getPackage().getName());
		factoryBean.setPackagesToScan(allPackages);
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter() {
			
			@SuppressWarnings("deprecation")
			@Override
			public PersistenceProvider getPersistenceProvider() {
				return new HibernatePersistence();
			}
			
			@Override
			public Map<String, Object> getJpaPropertyMap() {
				Map<String, Object> jpaProperties = super.getJpaPropertyMap();
				jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
				jpaProperties.put("hibernate.connection.isolation", "2"); //READ_COMMITTED
				jpaProperties.put("hibernate.generate_statistics", String.valueOf(generateStatistics));
				
				return jpaProperties;
			}
		};
		jpaVendorAdapter.setDatabasePlatform(sqlDialect);
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//		factoryBean.setPersistenceUnitName("sample");
		return factoryBean;
	}
	
	@Bean
	@Lazy
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
 	
	@Bean
	@Lazy
	public CoreMessageEventDao coreMessageEventDao() {
		return new CoreMessageEventDaoImpl();
	}
	
	
	public static void main(String[] args) throws Exception {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/test?user=root&password=action007");
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(1);
		dataSource.setConnectionInitSqls(Collections.singletonList("select 1"));
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from txm_message");
		while(rs.next())
			System.out.println(rs.getInt("txm_message_id"));
	}
}
