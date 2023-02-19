package io.my.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigurationImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "io.my.config.autoconfig.DispatherServletConfig",
                "io.my.config.autoconfig.TomcatWebServerConfiguration"
        };
    }
}
