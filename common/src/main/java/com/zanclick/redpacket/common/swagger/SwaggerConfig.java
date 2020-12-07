package com.zanclick.redpacket.common.swagger;

import com.zanclick.redpacket.common.swagger.annotation.OpenV1Api;
import com.zanclick.redpacket.common.swagger.annotation.WebOpenV1Api;
import com.zanclick.redpacket.common.swagger.annotation.WebV1Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lvlu
 * @date 2019-03-05 14:03
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html", "doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket v1WebApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("WEB接口1.0")
                .apiInfo(apiInfo("WEB接口1.0","1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(WebV1Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket v1WebOpenApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("WEB开放接口1.0")
                .apiInfo(apiInfo("WEB开放接口1.0","1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(WebOpenV1Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket v1OpenApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("开放接口1.0")
                .apiInfo(apiInfo("开放接口1.0","1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(OpenV1Api.class))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("©2020 Copyright. Powered By Personal.Tech")
                .termsOfServiceUrl("http://127.0.0.1:8087/")
                .version(version)
                .build();
    }
}
