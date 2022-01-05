package com.ldg.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("校园失物招领")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .apis(RequestHandlerSelectors.basePackage("com.ldg.controller"))
                .build();
    }
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("API接口文档")
                .description("开发者梁登光")
                .version("1.0")
                .contact(new Contact("梁登光", "https://baidu.com",
                        "1126458841@qq.com"))
                .build();
    }
}
