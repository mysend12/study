package io.my.config;

import io.my.config.autoconfig.DispatherServletConfig;
import io.my.config.autoconfig.TomcatWebServerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyAutoConfigurationImportSelector.class)
public @interface EnableMyAutoConfiguration {
}
