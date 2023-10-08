package com.begcode.demo.hibernate.config;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    value = {
        "com.begcode.demo.hibernate.**.repository",
        "com.begcode.demo.hibernate.oss.**.repository",
        "com.begcode.demo.hibernate.settings.**.repository",
        "com.begcode.demo.hibernate.system.**.repository",
        "com.begcode.demo.hibernate.log.**.repository",
        "com.begcode.demo.hibernate.taskjob.**.repository",
    },
    repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {}
