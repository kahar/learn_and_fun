package io.github.kahar.application;

import io.github.kahar.application.service.CompanyService;
import io.github.kahar.framework.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step6App {

    private static final Logger logger = LoggerFactory.getLogger(Step6App.class);

    public static void main(String[] args) {

        final ApplicationContext applicationContext = new ApplicationContext(Step6App.class.getPackage());
        final CompanyService companyServiceProxy1 = applicationContext.getBean(CompanyService.class);
        final CompanyService companyServiceProxy2 = applicationContext.getBean(CompanyService.class);

        logger.info(String.valueOf(companyServiceProxy1 == companyServiceProxy2));
    }
}
