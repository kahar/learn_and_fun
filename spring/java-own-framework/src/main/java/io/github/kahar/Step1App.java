package io.github.kahar;

import io.github.kahar.dao.CompanyDaoImpl;
import io.github.kahar.model.Company;
import io.github.kahar.service.CompanyServiceImpl;

public class Step1App {

    public static void main(String[] args) {
        final CompanyDaoImpl companyDao = new CompanyDaoImpl();
        final CompanyServiceImpl companyService = new CompanyServiceImpl(companyDao);

        companyService.createCompany(new Company());
    }
}
