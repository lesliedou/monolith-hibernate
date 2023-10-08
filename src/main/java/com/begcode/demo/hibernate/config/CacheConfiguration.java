package com.begcode.demo.hibernate.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, Constants.CAPTCHA_KEY);
            createCache(cm, com.begcode.demo.hibernate.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.begcode.demo.hibernate.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.begcode.demo.hibernate.domain.ApiPermission.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".authorities");
            createCache(cm, com.begcode.demo.hibernate.oss.domain.OssConfig.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.UReportFile.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.FillRuleItem.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.ResourceCategory.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".files");
            createCache(cm, com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".images");
            createCache(cm, com.begcode.demo.hibernate.system.domain.AnnouncementRecord.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.Dictionary.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.Dictionary.class.getName() + ".items");
            createCache(cm, com.begcode.demo.hibernate.domain.Authority.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.Authority.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.domain.Authority.class.getName() + ".viewPermissions");
            createCache(cm, com.begcode.demo.hibernate.domain.Authority.class.getName() + ".apiPermissions");
            createCache(cm, com.begcode.demo.hibernate.domain.Authority.class.getName() + ".users");
            createCache(cm, com.begcode.demo.hibernate.domain.ViewPermission.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".authorities");
            createCache(cm, com.begcode.demo.hibernate.system.domain.SmsSupplier.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.UploadImage.class.getName());
            createCache(cm, com.begcode.demo.hibernate.log.domain.SysLog.class.getName());
            createCache(cm, com.begcode.demo.hibernate.system.domain.SmsConfig.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.BusinessType.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.Position.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.Position.class.getName() + ".users");
            createCache(cm, com.begcode.demo.hibernate.settings.domain.CommonFieldData.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.UploadFile.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.RegionCode.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.RegionCode.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.Department.class.getName());
            createCache(cm, com.begcode.demo.hibernate.domain.Department.class.getName() + ".children");
            createCache(cm, com.begcode.demo.hibernate.domain.Department.class.getName() + ".authorities");
            createCache(cm, com.begcode.demo.hibernate.domain.Department.class.getName() + ".users");
            createCache(cm, com.begcode.demo.hibernate.system.domain.DataPermissionRule.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.SiteConfig.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.SiteConfig.class.getName() + ".items");
            createCache(cm, com.begcode.demo.hibernate.system.domain.SmsMessage.class.getName());
            createCache(cm, com.begcode.demo.hibernate.system.domain.SmsTemplate.class.getName());
            createCache(cm, com.begcode.demo.hibernate.system.domain.Announcement.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.SysFillRule.class.getName());
            createCache(cm, com.begcode.demo.hibernate.settings.domain.SysFillRule.class.getName() + ".ruleItems");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
