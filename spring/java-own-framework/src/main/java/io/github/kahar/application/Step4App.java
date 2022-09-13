package io.github.kahar.application;

import io.github.kahar.application.model.Company;
import io.github.kahar.application.service.CompanyService;
import io.github.kahar.framework.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Step4App {

    private static final Logger logger = LoggerFactory.getLogger(Step4App.class);

    public static void main(String[] args) {

        final ApplicationContext applicationContext = new ApplicationContext(Step4App.class.getPackage());
        final CompanyService companyServiceProxy = applicationContext.getBean(CompanyService.class);

        companyServiceProxy.createCompany(new Company());
    }
}