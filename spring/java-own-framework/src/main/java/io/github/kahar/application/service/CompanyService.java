package io.github.kahar.application.service;

import io.github.kahar.application.model.Company;

public interface CompanyService {

    void createCompany(Company company);

    void updateCompany(Company company);
}