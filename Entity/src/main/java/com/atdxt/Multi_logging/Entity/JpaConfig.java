package com.atdxt.Multi_logging.Entity;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.atdxt.Multi_logging.Repository")
@EntityScan(basePackages = "com.atdxt.Multi_logging.Entity")
public class JpaConfig {

    private final DataSource dataSource;

    @Autowired
    public JpaConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        // Set the data source
        entityManagerFactoryBean.setDataSource(dataSource);

        // Set packages to scan for entity classes
        entityManagerFactoryBean.setPackagesToScan("com.atdxt.Multi_logging.Entity");

        // Configure the JPA vendor adapter
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Set additional Hibernate properties
        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");
        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        return entityManagerFactoryBean;
    }
}
