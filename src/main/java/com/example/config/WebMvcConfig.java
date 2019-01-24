package com.example.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * @describe: ��ͼ������
     * @return: org.springframework.web.servlet.ViewResolver
     * @auther: xiongdingkun
     * @date: 2018/11/24 20:54
     **/
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    //���þ�̬��Դ�Ĵ���  ʹDispatcherServlet�Ծ�̬��Դ������ת����Servlet����Ĭ�ϵ�Servlet�ϣ�������ʹ��DispatcherServlet�����������������
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

  /*  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css");
    	registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js");
    	registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img");
    }*/

    /**
     * @describe: HTTP �������Ӧ�ǻ����ı��ģ���ζ��������ͷ�����ͨ������ԭʼ�ı�����ͨ�ţ���������ʵ����HttpMessageConverter���������á�
     * ��Ϣת���������ڽ�����ı���ת����java����
     * �򵥵����̣�SpringMvc > HttpMessageConverter > HttpOUtputMessage > ��Ӧ����
     * ������ > HttpInputMessage > HttpMessageConverter > springMvc
     * �ο���ַ��https://www.jianshu.com/p/333ed5ee958d
     * ����ʹ����FastJsonConfig������
     * @param converters
     * @return: void
     * @auther: xiongdingkun
     * @date: 2018/11/26 8:55
     **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
    }
}
