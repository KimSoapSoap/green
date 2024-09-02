package shop.mtcoding.springv3.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.springv3.core.interceptor.LoginInterceptor;

@Configuration  //IoC에 저장됨
public class WebConfig implements WebMvcConfigurer {


    // 주소가 "/api/~~" 일 때만 적용
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**");
    }
}
