package com.gree.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * swagger
 * 建议 EnableSwagger2 和 EnableKnife4j 二选一，但是连个都加上也不影响
 * 如果不需要使用 json 文件只要 @EnableSwagger2
 * 如果想要页面美观一点使用 @EnableKnife4j
 * @author yangLongFei 2020-12-10-19:34
 */
@Configuration
@EnableSwagger2 // http://localhost:8081/greeFIRST/swagger-ui.html
@EnableKnife4j // http://localhost:8081/greeFIRST/doc.html
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    /**
     * new Docket(DocumentationType.SWAGGER_2).host(host).apiInfo(apiInfo())
     */
    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.enable}")
    private boolean enable;

    /**
     * swagger2的配置文件
     * @return
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").description("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = Lists.newArrayList();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2).host("localhost:8081").apiInfo(apiInfo())
                .enable(enable)
                .globalOperationParameters(aParameters)
                .select()
                //需要扫描的controller的包
                .apis(RequestHandlerSelectors.basePackage("com.gree.user.controller")).paths(PathSelectors.any()).build();
    }

    /**
     * 构建 api文档页面的标题，内容，版本，描述，等信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("yang_zzu_first 接口文档")
                //创建人
                .contact(new Contact("firstGree", "https://blog.csdn.net/yang_zzu/", "yang_zzu@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description("api管理").build();
    }


}
