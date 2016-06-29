package com.mcmanus.fs.persistence.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.mcmanus.fs.persistence.repositories")
@EnableTransactionManagement(order = 1000)
public class PersistenceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceConfig.class);

    @Value("${db.driver}")
    private String driver;

    @Value("${db.url}")
    protected String url;

    @Value("${db.login}")
    protected String login;

    @Value("${db.password}")
    protected String password;

    @Value("${hibernate.showsql}")
    protected Boolean showSql;

    @Bean
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(200);
        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQueryTimeout(30000);
        LOG.info("Datasource has been initialized with {}", dataSource);
        return dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway dbInitialization() {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource());
        flyway.setSqlMigrationPrefix("DB-");
        flyway.setLocations("sql/automated/common", "sql/automated/mainOnly");
        return flyway;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.mcmanus.fs.model.jpa");
        final HibernateJpaVendorAdapter hibernateAdapter = new HibernateJpaVendorAdapter();
        hibernateAdapter.setShowSql(showSql);
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        emf.setJpaProperties(jpaProperties);
        emf.setJpaVendorAdapter(hibernateAdapter);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
