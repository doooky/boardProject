package kdh.boardproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    /*
    * Docket: Swagger 설정의 핵심이 되는 Bean
    *   useDefaultResponseMessages : Swagger에서 제공해주는 기본 응답 코드 (200, 401, 403, 404)
    *   false : 기본 응답 코드 노출하지 않음
    *   apis : api 스펙이 작성되어 있는 패키지(Controller)를 지정
    *   paths : apis에 있는 API 중 특정 path를 선택
    *   apiInfo : Swagger UI로 노출할 정보
    * */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kdh.boardproject.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Test")
                .description("SwaggerConfig")
                .version("3.0")
                .build();
    }


}