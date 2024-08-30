/*
 * Copyright 2024 xyz Co., Ltd. All Rights Reserved
 */
package xyz.muses.config;

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
 * @author jervis
 * @date 2024/8/30
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(this.apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("xyz.muses.web.controller"))
            .paths(PathSelectors.any())
            .build()
            .host("www.muses.xyz")
            .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("API Documentation")
            .description("This is a detailed description of the MUSES-API, including information on how to use it.")
            .version("1.0.0")
            .termsOfServiceUrl("http://www.muses.xyz/terms")
            .contact(new Contact(
                "Jervis",
                "http://www.muses.xyz",
                "jervis@muses.xyz"))
            .license("Apache License 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .build();
    }
}
