package io.my.tobyhelloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@SpringBootApplication

@Configuration
@ComponentScan
public class TobyhellobootApplication {

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/*");
                }).start();
            }
        };

        applicationContext.register(TobyhellobootApplication.class);
        applicationContext.refresh();
    }

}
