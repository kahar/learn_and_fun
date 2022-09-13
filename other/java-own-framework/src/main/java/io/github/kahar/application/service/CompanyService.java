package io.github.kahar.application.service;

import io.github.kahar.application.model.Company;
import io.github.kahar.framework.annotation.Transactional;

public interface CompanyService {

    void createCompany(Company company);

    String generateToken(Company company);

    @Transactional
    void createWithTransaction(Company company);

    void updateCompany(Company company);
}