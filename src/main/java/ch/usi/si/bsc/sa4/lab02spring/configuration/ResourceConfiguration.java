//package ch.usi.si.bsc.sa4.lab02spring.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.WebJarsResourceResolver;
//
//import java.nio.file.DirectoryStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Configuration
//@EnableWebMvc
//public class ResourceConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        System.out.println("I'm alive!!!");
//
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + s);
//
//        String classpath = System.getProperty("java.class.path");
//        System.out.println(classpath);
//
//        try {
//            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("./build/resources/main/static/"))) {
//                for (Path path : stream) {
////                    System.out.println(path.getFileName());
//                }
//            }
//        } catch (Exception ex) {
////            System.out.println(ex.getMessage());
//        }
//
//        registry.addResourceHandler("/webjars/**", "/resources/**")
//                .addResourceLocations("/webjars/", "classpath:/static/", "/resources/static/") // Try with classpath here
//                .resourceChain(false).addResolver(new WebJarsResourceResolver());
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//
//        registry
//                .addResourceHandler("/**")
//                .addResourceLocations("/resources/static/","/static/");
//
//
//    }
//}