package com.fintech.erp.config;

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

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
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
            createCache(cm, com.fintech.erp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.fintech.erp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.fintech.erp.domain.User.class.getName());
            createCache(cm, com.fintech.erp.domain.Authority.class.getName());
            createCache(cm, com.fintech.erp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.fintech.erp.domain.Timesheetek.class.getName());
            createCache(cm, com.fintech.erp.domain.Berek.class.getName());
            createCache(cm, com.fintech.erp.domain.EfoFoglalkoztatasok.class.getName());
            createCache(cm, com.fintech.erp.domain.OsztalekfizetesiKozgyulesek.class.getName());
            createCache(cm, com.fintech.erp.domain.SajatCegAlapadatok.class.getName());
            createCache(cm, com.fintech.erp.domain.CegAlapadatok.class.getName());
            createCache(cm, com.fintech.erp.domain.Bankszamlaszamok.class.getName());
            createCache(cm, com.fintech.erp.domain.Munkavallalok.class.getName());
            createCache(cm, com.fintech.erp.domain.SajatCegTulajdonosok.class.getName());
            createCache(cm, com.fintech.erp.domain.SajatCegKepviselok.class.getName());
            createCache(cm, com.fintech.erp.domain.Maganszemelyek.class.getName());
            createCache(cm, com.fintech.erp.domain.PartnerCegAdatok.class.getName());
            createCache(cm, com.fintech.erp.domain.PartnerCegMunkavallalok.class.getName());
            createCache(cm, com.fintech.erp.domain.PartnerCegKapcsolattartok.class.getName());
            createCache(cm, com.fintech.erp.domain.SzerzodesesJogviszonyok.class.getName());
            createCache(cm, com.fintech.erp.domain.MegrendelesDokumentumok.class.getName());
            createCache(cm, com.fintech.erp.domain.Megrendelesek.class.getName());
            createCache(cm, com.fintech.erp.domain.TeljesitesIgazolasDokumentumok.class.getName());
            createCache(cm, com.fintech.erp.domain.UgyfelElszamolasok.class.getName());
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
