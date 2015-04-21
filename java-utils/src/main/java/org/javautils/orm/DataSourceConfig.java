package org.javautils.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Bean
	public DataSource sampleDataSource(
			@Value("${sample.jdbc.driverClass:}") String sampleJdbcDriverClass,
			@Value("${sample.jdbc.url:}") String sampleJdbcUrl,
			@Value("${sample.jdbc.connCheckingQuery:}") String connCheckingQuery,
			@Value("${sample.jdbc.connections.initialSize:0}") int initialSize,
			@Value("${sample.jdbc.connections.maxActive:-1}") int maxActive) throws SQLException {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(sampleJdbcDriverClass);
		dataSource.setUrl(sampleJdbcUrl);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setConnectionInitSqls(Collections.singletonList(connCheckingQuery));
		dataSource.getConnection();
		return dataSource;
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
