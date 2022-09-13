package io.github.kahar.application;

import io.github.kahar.application.dao.CompanyDao;
import io.github.kahar.application.dao.CompanyDaoImpl;
import io.github.kahar.application.model.Company;
import io.github.kahar.application.service.CompanyService;
import io.github.kahar.application.service.CompanyServiceImpl;
import io.github.kahar.framework.ProxyHandler;

import java.lang.reflect.Proxy;

public class Step2aApp {

    public static void main(String[] args) {
        final CompanyDao companyDao = new CompanyDaoImpl();

        final CompanyService companyServiceProxy = (CompanyService) Proxy.newProxyInstance(
                Step2aApp.class.getClassLoader(),
                new Class[]{CompanyService.class},
                new ProxyHandler(new CompanyServiceImpl(companyDao))
        );

        companyServiceProxy.createCompany(new Company());
    }
}