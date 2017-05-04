package com.github.darains.sustech.student.server.config;

//
//@Configuration
//@EnableSwagger
//public class SwaggerConfig {
//
//    private SpringSwaggerConfig springSwaggerConfig;
//
//    @Autowired
//    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
//        this.springSwaggerConfig = springSwaggerConfig;
//    }
//
//    @Bean
//    public SwaggerSpringMvcPlugin customImplementation() {
//        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//            .apiInfo(apiInfo()).includePatterns("/api/.*")
//            .apiVersion("1.0.0");
//    }
//
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo(
//            "Demo API",
//            "/api/××× 直接使用，不需要登录；<br>"
//                + "/api/i/××× 需要使用HTTP Basic登录，或提供x-auth-token<br>"
//                + "包含参数Parameters Data Type=Pageable时，有三个通用参数可以使用,比如：size=20&page=0&sort=popular,msg，"
//                + "默认：size=20表明每页20行，page=0第几页，sort=popular,msg(asc默认正序，desc倒序)",
//            null, null, null, null);
//        return apiInfo;
//    }
//}