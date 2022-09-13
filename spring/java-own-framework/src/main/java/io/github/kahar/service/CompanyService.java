package io.github.kahar.service;

import io.github.kahar.model.Company;

public interface CompanyService {

    void createCompany(Company company);

    void updateCompany(Company company);
}