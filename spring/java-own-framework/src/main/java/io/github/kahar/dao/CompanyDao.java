package io.github.kahar.dao;

import io.github.kahar.model.Company;

public interface CompanyDao {

    void createCompany(Company company);

    void updateCompany(Company company);
}