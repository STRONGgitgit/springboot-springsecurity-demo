package com.example.config;

import org.apache.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration  //标记为配置文件，这里配置缓存的配置
@EnableCaching//启用缓存注解
public class CachingConfig {

    private static final Logger logger = Logger.getLogger(CachingConfig.class);

    /**
     * @describe: 实例化一个ehcache工厂bean并指定xml
     * @return: org.springframework.cache.ehcache.EhCacheManagerFactoryBean
     * @auther: xiongdingkun
     * @date: 2018/11/24 19:59
     **/
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(
                "ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }


    /**
     * @describe: 获得一个ehcache bean并将它加入到ehcache管理器中
     * @return: org.springframework.cache.CacheManager
     * @auther: xiongdingkun
     * @date: 2018/11/24 20:01
     **/
    @Bean
    public CacheManager cacheManager() {
        logger.info("EhCacheCacheManager");
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return cacheManager;
    }
}
