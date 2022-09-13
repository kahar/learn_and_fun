package io.github.kahar.application;

import io.github.kahar.application.model.Company;
import io.github.kahar.application.service.CompanyService;
import io.github.kahar.framework.ApplicationContext;

public class Step3App {

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new ApplicationContext(Step3App.class.getPackage());
        final CompanyService companyServiceProxy = applicationContext.getBean(CompanyService.class);

        companyServiceProxy.createCompany(new Company());
    }
}