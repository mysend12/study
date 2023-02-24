package io.my.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {

    @Test
    void configuration() {
        Common common = new Common();
        Assertions.assertEquals(common, common);

        MyConfig myConfig = new MyConfig();
        Bean1 bean1 = myConfig.bean1();
        Bean2 bean2 = myConfig.bean2();
        Assertions.assertNotEquals(bean1.common, bean2.common);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();

        Bean1 bean11 = context.getBean(Bean1.class);
        Bean2 bean12 = context.getBean(Bean2.class);
        Assertions.assertEquals(bean11.common, bean12.common);
    }

    @Test
    void proxyCommonMethod() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();
        Assertions.assertEquals(bean1.common, bean2.common);
    }

    /**
     * Configuration 어노테이션의 옵션 proxyCommonMethod가 true인 경우,
     * 대충 아래같은 방식으로 proxy 클래스가 생성되어 동작하기 때문에 같은 객체가 나온다.
     */
    static class MyConfigProxy extends MyConfig {
        private Common common;
        @Override
        Common common() {
            if (this.common == null) {
                this.common = super.common();
            }
            return this.common;
        }
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }
        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }
        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }
    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }

}
