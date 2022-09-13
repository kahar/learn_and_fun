package io.github.kahar.application.dao;

import io.github.kahar.application.model.Company;

public interface CompanyDao {

    void createCompany(Company company);

    void updateCompany(Company company);
}