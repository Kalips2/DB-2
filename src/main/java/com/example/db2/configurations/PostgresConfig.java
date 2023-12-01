package com.example.db2.configurations;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class PostgresConfig {
  @Primary
  @Bean(name = "postgresDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "jdbcTemplate")
  public JdbcTemplate jdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
