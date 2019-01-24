package com.example.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPlugins(new Interceptor[]{pageHelper()});//���÷�ҳ���
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:mapper/*.xml"));
        return sessionFactory;
    }

    /**
     * mybatis ��ҳ������ã��ο���ַ��https://blog.csdn.net/u014082617/article/details/71215539
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //��startPage�е�pageNumЧ��һ��,����Ϊtrueʱ���ὫRowBounds��һ������offset����pageNumҳ��ʹ��
        p.setProperty("offsetAsPageNum", "true");
        //�ò���Ĭ��Ϊfalse ����Ϊtrueʱ��ʹ��RowBounds��ҳ�����count��ѯ
        p.setProperty("rowBoundsWithCount", "true");
        //���ú���ʱ�����pageNum<1���ѯ��һҳ�����pageNum>pages���ѯ���һҳ
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
