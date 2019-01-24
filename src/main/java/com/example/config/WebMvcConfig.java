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
     * @describe: 视图解析器
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

    //配置静态资源的处理  使DispatcherServlet对静态资源的请求转发到Servlet容器默认的Servlet上，而不是使用DispatcherServlet本身来处理此类请求
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
     * @describe: HTTP 请求和响应是基于文本的，意味着浏览器和服务器通过交换原始文本进行通信，而这里其实就是HttpMessageConverter发挥着作用。
     * 消息转换器，用于将请求的报文转换成java对象
     * 简单的流程：SpringMvc > HttpMessageConverter > HttpOUtputMessage > 响应报文
     * 请求报文 > HttpInputMessage > HttpMessageConverter > springMvc
     * 参考网址：https://www.jianshu.com/p/333ed5ee958d
     * 这里使用了FastJsonConfig来解析
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
