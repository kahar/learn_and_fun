package io.github.kahar.task;

import liquibase.integration.spring.SpringLiquibase;
import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class H2Config {

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new JakartaWebServlet());

        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/liquibase-changeLog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}