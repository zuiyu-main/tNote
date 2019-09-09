package com.tz.mynote.config;

import com.tz.mynote.interceptor.AuthenticationInterceptor;
import com.tz.mynote.interceptor.StringToDateConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author tz
 * @Classname InterceptorConfig
 * @Description 拦截器配置
 * @Date 2019-09-07 18:17
 */
@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    /**
     * 添加时间入参转换
     */
    /**
     * Add {@link Converter Converters} and {@link Formatter Formatters} in addition to the ones
     * registered by default.
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }
}
