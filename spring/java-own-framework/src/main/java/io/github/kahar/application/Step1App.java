package io.github.kahar.application;

import io.github.kahar.application.dao.CompanyDaoImpl;
import io.github.kahar.application.model.Company;
import io.github.kahar.application.service.CompanyServiceImpl;

public class Step1App {

    public static void main(String[] args) {
        final CompanyDaoImpl companyDao = new CompanyDaoImpl();
        final CompanyServiceImpl companyService = new CompanyServiceImpl(companyDao);

        companyService.createCompany(new Company());
    }
}
